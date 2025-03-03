package com.tweets.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tweetlikes")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TweetLike extends DbBaseModel {
	@Column(name = "tweetid")
	private Long tweetId;
	@Column(name = "userid")
	private String userId;
}
