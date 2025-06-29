package com.tweets.handlers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweets.datamodel.Tweet;
import com.tweets.datamodel.User;
import com.tweets.kafka.TweetsProducer;
import com.tweets.repository.TweetsRepository;
import com.tweets.restmodel.TweetSaveRequest;
import com.tweets.util.SystemContext;

@Service
public class TweetSaveHandler {
	
	@Autowired
	private TweetsRepository tweetsRepo;
	
	@Autowired
	private TweetsProducer tweetsProducer;
	
	public void save(TweetSaveRequest request) {
		User loggedInUser = SystemContext.getLoggedInUser();
		Tweet tweet = Tweet.builder().userId(loggedInUser.getUserId()).tweet(request.getTweet())
				.createdOn(LocalDateTime.now()).processedOn(LocalDateTime.now()).build();
		tweetsRepo.save(tweet);
		tweetsProducer.sendTweet(tweet);
	}
}
