package com.convo.kafka;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.convo.datamodel.Tweet;
import com.convo.repository.TweetsRepository;
import com.convo.util.GsonUtils;

@Component
public class TweetsConsumer {

	private static List<Tweet> pendingTweetsToSave = new ArrayList<>();

	@Autowired
	private TweetsRepository tweetsRepo;

	@KafkaListener(topics = "tweets", groupId = "my-group-id")
	public void listen(String message) {
		synchronized (TweetsConsumer.pendingTweetsToSave) {
			pendingTweetsToSave.add(GsonUtils.getGson().fromJson(message, Tweet.class));
			if (pendingTweetsToSave.size() == 1000) {
				flushToDatabase();
			}
		}
	}

	@Scheduled(fixedDelay = 5, timeUnit = TimeUnit.SECONDS)
	private void flushToDatabase() {
		synchronized (TweetsConsumer.pendingTweetsToSave) {
			if (CollectionUtils.isNotEmpty(pendingTweetsToSave)) {
				tweetsRepo.saveAll(pendingTweetsToSave);
				pendingTweetsToSave.clear();
			}
		}
	}
}
