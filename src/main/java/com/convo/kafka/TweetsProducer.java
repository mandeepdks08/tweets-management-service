package com.convo.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.convo.datamodel.Tweet;
import com.convo.util.GsonUtils;

@Component
public class TweetsProducer {
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	private static final String TWEETS_TOPIC = "tweets";

	public void sendTweet(Tweet tweet) {
		kafkaTemplate.send(TWEETS_TOPIC, GsonUtils.getGson().toJson(tweet));
	}
}
