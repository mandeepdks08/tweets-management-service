package com.tweets.communicator;

import java.net.URI;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.tweets.exceptions.ServiceUnreachableException;
import com.tweets.util.ServiceConstants;

@Component
public class UserServiceRestClient {
	
	public <T> JSONObject getResponse(String endpoint, T input, RequestMethod requestMethod) {
		RestTemplate restTemplate = new RestTemplate();
		if (requestMethod.equals(RequestMethod.POST)) {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> requestEntity = new HttpEntity<>(input.toString(), headers);
			String response = restTemplate.postForObject(URI.create(ServiceConstants.USER_SERVICE_DNS + endpoint),
					requestEntity, String.class);
			return new JSONObject(response);
		} else if (requestMethod.equals(RequestMethod.GET)) {
			String response = restTemplate.getForObject(URI.create(ServiceConstants.USER_SERVICE_DNS + endpoint),
					String.class);
			return new JSONObject(response);
		}
		return null;
	}

	public <T> JSONObject fallback(String endpoint, T input, RequestMethod requestMethod, Throwable t) {
		throw new ServiceUnreachableException("The user management service is unreachable at the moment", t);
	}

}
