package com.tweets.restmodel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TweetEditRequest {
	private Long tweetId;
	private String tweet;
}
