package com.favqs.base;

import java.util.HashMap;

import io.restassured.response.Response;

public class QuotesService extends BaseService {

	private static final String BASE_PATH = "api/quotes/";

	public Response getAnQuote(String endpoint, HashMap<String, String> headers, HashMap<String, String> queryParams) {
		return getRequest(BASE_PATH + endpoint,headers,queryParams);
	}

	public Response updateAnQuote(String endpoint, HashMap<String, String> headers, HashMap<String, String> queryParams,
			Object payload) {

		return putRequest(BASE_PATH + endpoint,headers,queryParams,payload);
	}

	public Response addAnQuote(String endpoint, HashMap<String, String> headers, HashMap<String, String> queryParams,
			Object payload) {

		return postRequest(BASE_PATH + endpoint,headers,queryParams,payload);
	}

	public Response deleteAnQuote(String endpoint, HashMap<String, String> headers, HashMap<String, String> queryParams) {
		return deleteRequest(BASE_PATH + endpoint,headers,queryParams);
	}

}
