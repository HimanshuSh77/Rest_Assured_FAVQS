package com.favqs.base;

import java.util.HashMap;

import io.restassured.response.Response;

public class UsersService extends BaseService {

	private static final String BASE_PATH = "api/users/";

	public Response getAnUser(String endpoint, HashMap<String, String> headers, HashMap<String, String> queryParams) {
		return getRequest(BASE_PATH + endpoint,headers,queryParams);
	}

	public Response updateAnUser(String endpoint, HashMap<String, String> headers, HashMap<String, String> queryParams,
			Object payload) {

		return putRequest(BASE_PATH + endpoint,headers,queryParams,payload);
	}

	public Response createAnUser(String endpoint, HashMap<String, String> headers, HashMap<String, String> queryParams,
			Object payload) {

		return postRequest(payload, BASE_PATH + endpoint,headers,queryParams,payload);
	}

	public Response deleteAnUser(String endpoint, HashMap<String, String> headers, HashMap<String, String> queryParams) {
		return deleteRequest(BASE_PATH + endpoint,headers,queryParams);
	}

}
