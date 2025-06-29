package com.tweets.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweets.datamodel.Tweet;
import com.tweets.datamodel.User;
import com.tweets.repository.TweetsRepository;
import com.tweets.util.SystemContext;

@Service
public class ShowTweetHandler {

	@Autowired
	private TweetsRepository tweetsRepo;

	public Tweet showTweet(Long tweetId) {
		if (tweetId == null) {
			throw new RuntimeException("Id to dede bhai");
		}
		return tweetsRepo.findById(tweetId).orElse(null);
	}
}
