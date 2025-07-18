package com.favqs.base;

import static io.restassured.RestAssured.*;

import java.util.HashMap;

import com.favqs.util.LoggingFilter;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseService {

	private static final String BASE_URL = "https://favqs.com/";

	private RequestSpecification requestspecification(String basePath, HashMap<String, String> headers,
			HashMap<String, String> queryParams) {
		RequestSpecBuilder spec = new RequestSpecBuilder();
		if (!headers.isEmpty()) {
			spec.addHeaders(headers);
		}
		if (!queryParams.isEmpty()) {
			spec.addQueryParams(queryParams);
		}

		spec.setBaseUri(BASE_URL + basePath);
		spec.setContentType(ContentType.JSON);
		spec.addFilter(new LoggingFilter());

		return spec.build();

	}

	protected Response getRequest(String basePath, HashMap<String, String> headers,
			HashMap<String, String> queryParams) {

		RequestSpecification requestSpecification = requestspecification(basePath, headers, queryParams);

		return given().spec(requestSpecification).get();
	}

	protected Response putRequest(String basePath, HashMap<String, String> headers, HashMap<String, String> queryParams,
			Object payload) {

		RequestSpecification requestSpecification = requestspecification(basePath, headers, queryParams);

		return given().spec(requestSpecification).body(payload).put();

	}

	protected Response postRequest( String basePath, HashMap<String, String> headers,
			HashMap<String, String> queryParams, Object payload) {

		RequestSpecification requestSpecification = requestspecification(basePath, headers, queryParams);

		return given().spec(requestSpecification).body(payload).post();

	}

	protected Response deleteRequest(String basePath, HashMap<String, String> headers,
			HashMap<String, String> queryParams) {

		RequestSpecification requestSpecification = requestspecification(basePath, headers, queryParams);

		return given().spec(requestSpecification).delete();

	}

}
