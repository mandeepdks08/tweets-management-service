package com.tweets.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweets.datamodel.Tweet;
import com.tweets.datamodel.User;
import com.tweets.repository.TweetsRepository;
import com.tweets.restmodel.TweetDeleteRequest;
import com.tweets.util.SystemContext;

@Service
public class DeleteTweetHandler {
	
	@Autowired
	private TweetsRepository tweetsRepo;

	public void deleteTweet(TweetDeleteRequest tweetDeleteRequest) {
		User loggedInUser = SystemContext.getLoggedInUser();
		Long tweetId = tweetDeleteRequest.getTweetId();
		Tweet tweet = tweetsRepo.findById(tweetId).orElse(null);
		if (tweet != null && tweet.getUserId().equals(loggedInUser.getUserId())) {
			tweet.setIsDeleted(true);
			tweetsRepo.save(tweet);
		} else {
			throw new RuntimeException("Tweet not found");
		}
	}
}
