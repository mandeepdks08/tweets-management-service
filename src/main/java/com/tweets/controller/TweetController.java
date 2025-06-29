package com.tweets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tweets.datamodel.Tweet;
import com.tweets.handlers.DeleteTweetHandler;
import com.tweets.handlers.LikeTweetHandler;
import com.tweets.handlers.ListTweetsHandler;
import com.tweets.handlers.ShowTweetHandler;
import com.tweets.handlers.TweetEditHandler;
import com.tweets.handlers.TweetSaveHandler;
import com.tweets.restmodel.LikeTweetRequest;
import com.tweets.restmodel.ListTweetsResponse;
import com.tweets.restmodel.TweetDeleteRequest;
import com.tweets.restmodel.TweetEditRequest;
import com.tweets.restmodel.TweetSaveRequest;
import com.tweets.util.GsonUtils;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/tweet/v1")
@Slf4j
public class TweetController {

	@Autowired
	private TweetSaveHandler tweetSaveHandler;

	@Autowired
	private TweetEditHandler tweetEditHandler;

	@Autowired
	private ListTweetsHandler listTweetsHandler;

	@Autowired
	private DeleteTweetHandler deleteTweetHandler;

	@Autowired
	private ShowTweetHandler showTweetHandler;

	@Autowired
	private LikeTweetHandler likeTweetHandler;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	protected void saveTweet(@RequestBody TweetSaveRequest tweetSaveRequest) {
		log.info("Tweet save request {}", GsonUtils.getGson().toJson(tweetSaveRequest));
		tweetSaveHandler.save(tweetSaveRequest);
	}

	@RequestMapping(value = "/edit", method = RequestMethod.PATCH)
	protected void editTweet(@RequestBody TweetEditRequest tweetEditRequest) {
		log.info("Tweet edit request {}", GsonUtils.getGson().toJson(tweetEditRequest));
		tweetEditHandler.saveEditedTweet(tweetEditRequest);
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	protected ResponseEntity<ListTweetsResponse> listTweets(@RequestParam("offset") Integer offset) {
		ListTweetsResponse listTweetsResponse = listTweetsHandler.listTweets(offset);
		return new ResponseEntity<>(listTweetsResponse, HttpStatus.OK);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	protected void deleteTweet(@RequestBody TweetDeleteRequest tweetDeleteRequest) {
		deleteTweetHandler.deleteTweet(tweetDeleteRequest);
	}

	@RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
	protected Tweet showTweet(@PathVariable Long id) {
		return showTweetHandler.showTweet(id);
	}

	@RequestMapping(value = "/like", method = RequestMethod.POST)
	protected void likeTweet(@RequestBody LikeTweetRequest request) {
		likeTweetHandler.likeTweet(request);
	}

}
