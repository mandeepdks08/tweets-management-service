package com.tweets.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweets.datamodel.Tweet;
import com.tweets.datamodel.User;
import com.tweets.kafka.TweetsProducer;
import com.tweets.repository.TweetsRepository;
import com.tweets.restmodel.TweetEditRequest;
import com.tweets.util.SystemContext;

@Service
public class TweetEditHandler {
	
	@Autowired
	private TweetsRepository tweetsRepo;
	
	@Autowired
	private TweetsProducer tweetsProducer;

	public void saveEditedTweet(TweetEditRequest request) {
		User loggedInUser = SystemContext.getLoggedInUser();
		Tweet tweet = tweetsRepo.findById(request.getTweetId()).orElse(null);
		if (tweet == null || !tweet.getUserId().equals(loggedInUser.getUserId())) {
			throw new RuntimeException("Unauthorized!");
		} else {		
			tweet.setTweet(request.getTweet());
			tweetsRepo.save(tweet);
			tweetsProducer.sendTweet(tweet);
		}
	}
}
