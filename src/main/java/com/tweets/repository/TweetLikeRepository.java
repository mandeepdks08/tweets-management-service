package com.tweets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tweets.datamodel.TweetLike;

@Repository
public interface TweetLikeRepository extends JpaRepository<TweetLike, Long> {
	public TweetLike findByTweetIdAndUserId(Long tweetId, String userId);
}
