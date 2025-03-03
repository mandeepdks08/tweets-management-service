package com.tweets.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.tweets.util.AppConstants;
import com.tweets.util.SnowflakeIdGenerator;

@Component
public class CorrelationIdInterceptor implements HandlerInterceptor {

	@Autowired
	private SnowflakeIdGenerator snowflakeIdGenerator;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String parentCorrelationId = request.getHeader(AppConstants.PARENT_CORRELATION_ID_HEADER.getValue());
		if (parentCorrelationId == null) {
			parentCorrelationId = String.valueOf(snowflakeIdGenerator.nextId());
		}

		String childCorrelationId = String.valueOf(snowflakeIdGenerator.nextId());

		response.addHeader(AppConstants.PARENT_CORRELATION_ID_HEADER.getValue(), parentCorrelationId);
		response.addHeader(AppConstants.CHILD_CORRELATION_ID_HEADER.getValue(), childCorrelationId);

		MDC.put(AppConstants.MDC_PARENT_CORRELATION_ID_KEY.getValue(), parentCorrelationId);
		MDC.put(AppConstants.MDC_CHILD_CORRELATION_ID_KEY.getValue(), childCorrelationId);

		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		MDC.remove(AppConstants.MDC_PARENT_CORRELATION_ID_KEY.getValue());
		MDC.remove(AppConstants.MDC_CHILD_CORRELATION_ID_KEY.getValue());
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

}
