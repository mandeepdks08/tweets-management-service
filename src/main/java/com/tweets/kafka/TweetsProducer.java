package com.tweets.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.tweets.datamodel.Tweet;
import com.tweets.exceptions.ServiceUnreachableException;
import com.tweets.util.GsonUtils;

@Component
public class TweetsProducer {

	private static final String TWEETS_TOPIC = "tweets.new";

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void sendTweet(Tweet tweet) {
		ProducerRecord<String, String> record = new ProducerRecord<>(TWEETS_TOPIC, GsonUtils.getGson().toJson(tweet));
		kafkaTemplate.send(record);
	}

	public void fallback(Tweet tweet, Throwable t) {
		throw new ServiceUnreachableException("Kafka is unreachable at the moment", t);
	}
}
