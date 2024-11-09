package com.convo.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.convo.datamodel.Tweet;
import com.convo.util.AppConstants;
import com.convo.util.GsonUtils;
import com.convo.util.SystemContext;

@Component
public class TweetsProducer {

	private static final String TWEETS_TOPIC = "tweets";

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
