package com.tweets.restmodel;

import java.util.List;

import com.tweets.datamodel.Tweet;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class ListTweetsResponse extends BaseResponse {
	private Integer offset;
	private List<Tweet> tweets;
}
