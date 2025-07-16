package com.favqs.base;

import static io.restassured.RestAssured.*;

import java.util.HashMap;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseService {

	private static final String BASE_URL = "https://favqs.com/";

	private RequestSpecification requestspecificationSetUp(String basePath, HashMap<String, String> headers,
			HashMap<String, String> queryParams) {
		RequestSpecBuilder spec = new RequestSpecBuilder();
		if (!headers.isEmpty() || headers != null) {
			spec.addHeaders(headers);
		}
		if (!queryParams.isEmpty() || queryParams != null) {
			spec.addQueryParams(queryParams);
		}

		spec.setBaseUri(BASE_URL + basePath);
		spec.setContentType(ContentType.JSON);

		return spec.build();

	}

	protected Response getRequest(String basePath, HashMap<String, String> headers,
			HashMap<String, String> queryParams) {

		RequestSpecification requestSpecification = requestspecificationSetUp(basePath, headers, queryParams);

		return given().spec(requestSpecification).log().all().get();
	}

	protected Response putRequest(String basePath, HashMap<String, String> headers, HashMap<String, String> queryParams,
			Object payload) {

		RequestSpecification requestSpecification = requestspecificationSetUp(basePath, headers, queryParams);

		return given().spec(requestSpecification).log().all().body(payload).put();

	}

	protected Response postRequest(Object payload, String basePath, HashMap<String, String> headers,
			HashMap<String, String> queryParams, Object payload2) {

		RequestSpecification requestSpecification = requestspecificationSetUp(basePath, headers, queryParams);

		return given().spec(requestSpecification).log().all().body(payload).post();

	}

	protected Response deleteRequest(String basePath, HashMap<String, String> headers,
			HashMap<String, String> queryParams) {

		RequestSpecification requestSpecification = requestspecificationSetUp(basePath, headers, queryParams);

		return given().spec(requestSpecification).log().all().delete();

	}

}
