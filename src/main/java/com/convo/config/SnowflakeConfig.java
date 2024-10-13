package com.convo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.convo.util.SnowflakeIdGenerator;

@Configuration
public class SnowflakeConfig {

	@Bean
	public SnowflakeIdGenerator snowflakeIdGenerator() {
		// TODO: to be picked from environment variable
		long machineId = 1;
		return new SnowflakeIdGenerator(machineId);
	}
}
