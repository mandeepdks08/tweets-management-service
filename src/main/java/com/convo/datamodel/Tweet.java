package com.convo.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tweets")
@Getter
@Setter
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
