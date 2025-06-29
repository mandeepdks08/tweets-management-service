package com.tweets.communicator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tweets.datamodel.User;

@Component
public class UserServiceCommunicator {

	@Autowired
	private UserServiceRestClient userServiceClient;

	public List<User> getUsersDetails(List<String> userIds) {
		JSONObject request = new JSONObject();
		request.put("userIds", ObjectUtils.firstNonNull(userIds, new ArrayList<>()));
		JSONObject usersListResponse = new JSONObject(
				userServiceClient.getResponse("/user/v1/list", request, RequestMethod.POST));
		JSONArray usersJsonArray = usersListResponse.getJSONArray("userIds");
		List<User> usersDetails = usersJsonArray.toList().stream().map(obj -> (User) obj).collect(Collectors.toList());
		return ObjectUtils.firstNonNull(usersDetails, new ArrayList<>());
	}

	public User authenticate(String token) throws Exception {
		JSONObject request = new JSONObject();
		request.put("token", token);
		JSONObject authenticationResponse = userServiceClient.getResponse("/user/v1/authenticate", request,
				RequestMethod.POST);
		if (authenticationResponse.getJSONObject("user") != null) {
			return User.createUserFromJsonObjet(authenticationResponse.getJSONObject("user"));
		} else {
			String error = (String) authenticationResponse.getJSONArray("errors").toList().get(0);
			throw new Exception("Failed to authenticate the user. Error message is: " + error);
		}
	}

}
