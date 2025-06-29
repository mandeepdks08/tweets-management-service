package com.tweets.restmodel;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class BaseResponse {
	private String message;
	private Boolean success;
	private List<String> errors;
}
