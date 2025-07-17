package com.favqs.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class LoggingFilter implements Filter {

	private static final Logger logger = LogManager.getLogger(LoggingFilter.class);

	@Override
	public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
			FilterContext ctx) {
		logRequest(requestSpec);
		Response response = ctx.next(requestSpec, responseSpec);
		logResponse(response);

		return response;// test for assertion
	}

	public void logRequest(FilterableRequestSpecification requestSpec) {
//		logger.info("BASE URI:" + requestSpec.getURI());
//		logger.info("Request Header:" + requestSpec.getHeaders());
//		logger.info("Request PayLoad:" + requestSpec.getBody());

		String body = requestSpec.getBody() != null ? requestSpec.getBody().toString() : "<Null or Empty>";

		LogUtil.log("BASE URI:" + requestSpec.getURI());
		LogUtil.log("Request Header:" + requestSpec.getHeaders());
		LogUtil.log("Request PayLoad:" + body);

		ExtentReportingUtil.getTest().info("Request: " + requestSpec.getMethod() + " " + requestSpec.getURI());
		ExtentReportingUtil.getTest().info("Request Headers : " + requestSpec.getHeaders().toString());
		ExtentReportingUtil.getTest().info(MarkupHelper.createCodeBlock(body, CodeLanguage.JSON));

	}

	public void logResponse(Response response) {
//		logger.info("STATUS CODE:" + response.getStatusCode());
//		logger.info("Response Header :" + response.headers());
//		logger.info("Request Body:" + response.getBody().prettyPrint());

		String body = response.getBody() != null ? response.asPrettyString() : "<Null or Empty>";

		LogUtil.log("STATUS CODE:" + response.getStatusCode());
		LogUtil.log("Response Header :" + response.headers());
		LogUtil.log("Request Body:" + body);

		ExtentReportingUtil.getTest().info("Response Code : " + response.getStatusCode());
		ExtentReportingUtil.getTest().info(MarkupHelper.createCodeBlock(body, CodeLanguage.JSON));
	}

}