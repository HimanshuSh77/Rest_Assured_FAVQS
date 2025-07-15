package com.favqs.base;

import java.util.HashMap;

import io.restassured.response.Response;

public class QuotesService extends BaseService {

	private static final String BASE_PATH = "api/quotes/";

	public Response getAnQuote(String endpoint, HashMap<String, String> headers, HashMap<String, String> pathParams) {
		return getRequest(BASE_PATH + endpoint,headers,pathParams);
	}

	public Response updateAnQuote(String endpoint, HashMap<String, String> headers, HashMap<String, String> pathParams,
			Object payload) {

		return putRequest(BASE_PATH + endpoint,headers,pathParams,payload);
	}

	public Response addAnQuote(String endpoint, HashMap<String, String> headers, HashMap<String, String> pathParams,
			Object payload) {

		return postRequest(payload, BASE_PATH + endpoint,headers,pathParams,payload);
	}

	public Response deleteAnQuote(String endpoint, HashMap<String, String> headers, HashMap<String, String> pathParams) {
		return deleteRequest(BASE_PATH + endpoint,headers,pathParams);
	}

}
