package com.convo.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.convo.util.SnowflakeIdGenerator;

@Component
public class CorrelationIdInterceptor implements HandlerInterceptor {

	private static final String PARENT_CORRELATION_ID_HEADER = "X-Parent-Correlation-ID";
	private static final String CHILD_CORRELATION_ID_HEADER = "X-Child-Correlation-ID";

	private static final String MDC_PARENT_CORRELATION_ID_KEY = "parentCorrelationId";
	private static final String MDC_CHILD_CORRELATION_ID_KEY = "childCorrelationId";

	@Autowired
	private SnowflakeIdGenerator snowflakeIdGenerator;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String parentCorrelationId = request.getHeader(PARENT_CORRELATION_ID_HEADER);
		if (parentCorrelationId == null) {
			parentCorrelationId = String.valueOf(snowflakeIdGenerator.nextId());
		}

		String childCorrelationId = String.valueOf(snowflakeIdGenerator.nextId());

		response.addHeader(PARENT_CORRELATION_ID_HEADER, parentCorrelationId);
		response.addHeader(CHILD_CORRELATION_ID_HEADER, childCorrelationId);

		MDC.put(MDC_PARENT_CORRELATION_ID_KEY, parentCorrelationId);
		MDC.put(MDC_CHILD_CORRELATION_ID_KEY, childCorrelationId);

		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		MDC.remove(MDC_PARENT_CORRELATION_ID_KEY);
		MDC.remove(MDC_CHILD_CORRELATION_ID_KEY);
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

}
