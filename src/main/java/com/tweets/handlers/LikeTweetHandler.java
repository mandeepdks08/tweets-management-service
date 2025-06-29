package com.tweets.handlers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweets.datamodel.TweetLike;
import com.tweets.datamodel.User;
import com.tweets.repository.TweetLikeRepository;
import com.tweets.restmodel.LikeTweetRequest;
import com.tweets.util.SystemContext;

@Service
public class LikeTweetHandler {

	@Autowired
	private TweetLikeRepository tweetLikeRepo;

	public void likeTweet(LikeTweetRequest request) {
		Long tweetId = request.getTweetId();
		if (tweetId == null) {
			throw new RuntimeException("Tweet id is required");
		}
		User loggedInUser = SystemContext.getLoggedInUser();
		TweetLike tweetLike = tweetLikeRepo.findByTweetIdAndUserId(tweetId, loggedInUser.getUserId());
		if (tweetLike == null) {
			tweetLike = TweetLike.builder().tweetId(tweetId).userId(loggedInUser.getUserId())
					.createdOn(LocalDateTime.now()).build();
			tweetLikeRepo.save(tweetLike);
		} else {
			throw new RuntimeException("Tweet already liked");
		}
	}

}
