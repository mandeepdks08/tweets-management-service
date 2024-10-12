package com.convo.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.convo.datamodel.Tweet;
import com.convo.datamodel.User;
import com.convo.kafka.TweetsProducer;
import com.convo.restmodel.TweetEditRequest;
import com.convo.restmodel.TweetSaveRequest;
import com.convo.util.GsonUtils;
import com.convo.util.SystemContextHolder;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/tweet/v1")
@Slf4j
public class TweetController {

	@Autowired
	private TweetsProducer tweetsProducer;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	protected void saveTweet(@RequestBody TweetSaveRequest tweetSaveRequest) {
		log.info("Tweet save request {}", GsonUtils.getGson().toJson(tweetSaveRequest));
		User loggedInUser = SystemContextHolder.getLoggedInUser();
		Tweet tweet = Tweet.builder().userId(loggedInUser.getUserId()).tweet(tweetSaveRequest.getTweet())
				.createdOn(LocalDateTime.now()).processedOn(LocalDateTime.now()).build();
		tweetsProducer.sendTweet(tweet);
	}

	@RequestMapping(value = "/edit", method = RequestMethod.PATCH)
	protected void editTweet(@RequestBody TweetEditRequest tweetEditRequest) {
		log.info("Tweet edit request {}", GsonUtils.getGson().toJson(tweetEditRequest));
		User loggedInUser = SystemContextHolder.getLoggedInUser();
		Tweet tweet = Tweet.builder().id(tweetEditRequest.getTweetId()).tweet(tweetEditRequest.getTweet())
				.userId(loggedInUser.getUserId()).build();
		tweetsProducer.sendTweet(tweet);
	}

}
