package com.convo.util;

import lombok.extern.slf4j.Slf4j;

/*
 * Snowflake id pattern
 * Total bits = 64
 * <timestamp_bits><machineId_bits><sequence_bits>
 * With current config - 
 * sequence_bits = 10, i.e, max ids allowed per timestamp = 2^10 = 1024 (0-1023)
 * machindeId_bits = 5, i.e, max id allowed = 2^5 = 32 (0-31)
 * timetamp_bits = 64 - machineId_bits - sequence_bits = 49
 */
@Slf4j
public class SnowflakeIdGenerator {
	private static long epoch = 1672531200000L;
	private static long machineIdBits = 5L;
	private static long sequenceBits = 10L;

	// timestamp is to be left shifted to make space for machine id and sequence
	// bits
	private static long timestampShiftBits = machineIdBits + sequenceBits;
	// machine id is to left shifted to make space for sequence bits since sequence
	// bits
	// come in the end of the correlation id pattern
	private static long machineIdShiftBits = sequenceBits;

	private final Long machineId;
	private Long sequence;
	private Long lastTimestamp;

	public SnowflakeIdGenerator(long machineId) {
		long maxMachineId = 1 << machineIdBits;
		if (machineId >= maxMachineId) {
			throw new RuntimeException("Machine id must be below max machine id limit " + maxMachineId);
		}
		this.machineId = machineId;
		this.sequence = -1L;
		this.lastTimestamp = System.currentTimeMillis();
	}

	public synchronized long nextId() {
		long timestamp = System.currentTimeMillis();
		if (timestamp == lastTimestamp) {
			/*
			 * If request came within same timestamp generate the correlation id for same
			 * timestamp with higher sequence
			 */
			long maxSequence = 1 << sequenceBits;
			sequence++;
			if (sequence >= maxSequence) {
				log.warn("Sequence limit reached. Need to re-assess the limit. Current limit 0-{}, timestamp {}",
						maxSequence - 1, timestamp);
				sequence = 0L;
				while (timestamp == lastTimestamp) {
					timestamp = System.currentTimeMillis();
				}
			}
		} else {
			/*
			 * If timestamp changed, start the sequence from 0
			 */
			sequence = 0L;
			lastTimestamp = timestamp;
		}

		/*
		 * Out of 64-bits of the long Rightmost 10 bits are allotted to sequence Next 5
		 * right bits are allotted to machineId Remaining bits (49 bits) are allotted to
		 * timestamp
		 */
		return (((timestamp - epoch) << timestampShiftBits) | (machineId << machineIdShiftBits) | sequence);
	}
}
