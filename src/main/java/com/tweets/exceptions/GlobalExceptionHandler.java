package com.tweets.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tweets.restmodel.BaseResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ServiceUnreachableException.class)
	public ResponseEntity<BaseResponse> handleServiceUnreachableException(RuntimeException e) {
		return new ResponseEntity<>(BaseResponse.builder().message(e.getMessage()).success(false).build(),
				HttpStatus.BAD_REQUEST);
	}
}
