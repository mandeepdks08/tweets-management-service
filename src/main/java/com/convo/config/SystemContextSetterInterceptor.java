package com.convo.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.convo.communicator.UserServiceCommunicator;
import com.convo.datamodel.User;
import com.convo.util.SystemContext;

@Component
public class SystemContextSetterInterceptor implements HandlerInterceptor {

	@Autowired
	private UserServiceCommunicator userServiceCommunicator;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = request.getHeader("Authorization");
		User user = userServiceCommunicator.authenticate(token);
		SystemContext.setLoggedInUser(user);
		SystemContext.setHttpRequest(request);
		return true;
	}
	
}
