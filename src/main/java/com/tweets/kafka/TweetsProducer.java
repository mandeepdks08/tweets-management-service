package com.tweets.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.tweets.datamodel.Tweet;
import com.tweets.util.AppConstants;
import com.tweets.util.GsonUtils;
import com.tweets.util.SystemContext;

@Component
public class TweetsProducer {

	private static final String TWEETS_TOPIC = "tweets.to-persist";

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void sendTweet(Tweet tweet) {
		ProducerRecord<String, String> record = new ProducerRecord<>(TWEETS_TOPIC, GsonUtils.getGson().toJson(tweet));
		record.headers().add(AppConstants.PARENT_CORRELATION_ID_HEADER.getValue(),
				SystemContext.getParentCorrelationId().getBytes());
		record.headers().add(AppConstants.CHILD_CORRELATION_ID_HEADER.getValue(),
				SystemContext.getChildCorrelationId().getBytes());
		kafkaTemplate.send(record);
	}
}
