package com.tweets.util;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ServiceConstants {
	
	public static String USER_SERVICE_DNS;
	
	@Autowired
	private Environment env;
	
	@PostConstruct
	private void init() {
		USER_SERVICE_DNS = env.getProperty("USER_SERVICE_DNS");
		String ALB_DNS = env.getProperty("ALB_DNS");
		if (StringUtils.isNotBlank(ALB_DNS)) {
			USER_SERVICE_DNS = ALB_DNS + "/user";
		}
	}
}
