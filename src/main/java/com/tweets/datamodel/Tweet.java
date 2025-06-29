package com.tweets.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tweets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Tweet extends DbBaseModel {
	@Column(name = "userid")
	private String userId;
	@Column(name = "tweet")
	private String tweet;
	@Column(name = "isdeleted")
	private Boolean isDeleted;
	// In future we can add photos, videos, and audios as well
}
