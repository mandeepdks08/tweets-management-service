package com.tweets.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tweets.datamodel.User;

public class SystemContext {
	private static ThreadLocal<User> loggedInUser;
	private static ThreadLocal<HttpServletRequest> httpRequest;
	private static ThreadLocal<HttpServletResponse> httpResponse;

	static {
		loggedInUser = new ThreadLocal<>();
		httpRequest = new ThreadLocal<>();
		httpResponse = new ThreadLocal<>();
	}

	public static void setLoggedInUser(User user) {
		loggedInUser.set(user);
	}

	public static User getLoggedInUser() {
		return loggedInUser.get();
	}

	public static void setHttpRequest(HttpServletRequest request) {
		httpRequest.set(request);
	}

	public static HttpServletRequest getHttpRequest() {
		return httpRequest.get();
	}

	public static void setHttpResponse(HttpServletResponse response) {
		httpResponse.set(response);
	}

	public static HttpServletResponse getHttpResponse() {
		return httpResponse.get();
	}

	public static String getParentCorrelationId() {
		HttpServletResponse response = httpResponse.get();
		if (response != null) {
			return response.getHeader(AppConstants.PARENT_CORRELATION_ID_HEADER.getValue());
		}
		return null;
	}

	public static String getChildCorrelationId() {
		HttpServletResponse response = httpResponse.get();
		if (response != null) {
			return response.getHeader(AppConstants.CHILD_CORRELATION_ID_HEADER.getValue());
		}
		return null;
	}
}