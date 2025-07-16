package com.favqs.base;

import java.util.HashMap;

import io.restassured.response.Response;

public class ActivityService extends BaseService {

	private static final String BASE_PATH = "api/activities/";

	public Response getAnActivity(String endpoint, HashMap<String, String> headers, HashMap<String, String> queryParams) {
		return getRequest(BASE_PATH + endpoint,headers,queryParams);
	}

	public Response updateAnActivity(String endpoint, HashMap<String, String> headers, HashMap<String, String> queryParams,
			Object payload) {

		return putRequest(BASE_PATH + endpoint,headers,queryParams,payload);
	}

	public Response addAnActivity(String endpoint, HashMap<String, String> headers, HashMap<String, String> queryParams,
			Object payload) {

		return postRequest(payload, BASE_PATH + endpoint,headers,queryParams,payload);
	}

	public Response deleteAnActivity(String endpoint, HashMap<String, String> headers, HashMap<String, String> queryParams) {
		return deleteRequest(BASE_PATH + endpoint,headers,queryParams);
	}

}
