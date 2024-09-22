package com.convo.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.convo.datamodel.Tweet;
import com.convo.datamodel.User;
import com.convo.repository.TweetsRepository;
import com.convo.restmodel.TweetSaveRequest;
import com.convo.util.GsonUtils;
import com.convo.util.SystemContextHolder;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/tweet/v1")
@Slf4j
public class TweetController {
	
	@Autowired
	private TweetsRepository tweetsRepo;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	protected void saveTweet(@RequestBody TweetSaveRequest tweetSaveRequest) {
		log.info("Tweet save request {}", GsonUtils.getGson().toJson(tweetSaveRequest));
		User loggedInUser = SystemContextHolder.getLoggedInUser();
		Tweet tweet = Tweet.builder().userId(loggedInUser.getUserId()).tweet(tweetSaveRequest.getTweet())
				.createdOn(LocalDateTime.now()).processedOn(LocalDateTime.now()).build();
		tweetsRepo.save(tweet);
	}
}
