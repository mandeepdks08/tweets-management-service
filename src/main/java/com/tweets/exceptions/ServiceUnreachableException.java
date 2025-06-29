package com.tweets.exceptions;

@SuppressWarnings("serial")
public class ServiceUnreachableException extends RuntimeException {
	public ServiceUnreachableException(String message, Throwable cause) {
        super(message, cause);
    }
}
