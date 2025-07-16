package com.favqs.tests;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.favqs.base.ActivityService;
import com.favqs.base.QuotesService;
import com.favqs.base.UsersService;
import com.favqs.util.PropertyFileUtil;
import com.github.javafaker.Faker;
import com.model.request.CreateQuoteRequestPayload;
import com.model.request.CreateUserRequestPayload;
import com.model.response.GetUserResponsePayload;

import io.restassured.response.Response;

public class ActivityTest {

	private static HashMap<String, String> headers;
	private static HashMap<String, String> queryParams;
	private static ActivityService activityService;
	private String activityID;

	@BeforeMethod
	public void setUp() {

		queryParams = new HashMap<String, String>();

		headers = new HashMap<String, String>();
		headers.put("Authorization", PropertyFileUtil.getProperty("Authorization"));
		headers.put("User-Token", PropertyFileUtil.getProperty("User-Token"));

		activityService = new ActivityService();

	}

	@Test(description = "Verify Get Activity API")
	public void addAnQuoteTest() {

		Response response = activityService.getAnActivity("", headers, queryParams);

		Assert.assertEquals(response.getStatusCode(), 200, "Invalid Status Code Detected");
		activityID = response.getBody().jsonPath().getString("activity_id");

	}

	@Test(description = "Verify Follow Activity API")
	public void followAnActivityTest() {

		queryParams.put("type", "author");

		Response response = activityService.updateAnActivity("follow", headers, queryParams, "");

		Assert.assertEquals(response.getStatusCode(), 204, "Invalid Status Code Detected");

	}

	@Test(description = "Verify Un-Follow Activity API", dependsOnMethods = { "followAnActivityTest" })
	public void unfollowAnActivityTest() {

		queryParams.put("type", "author");

		Response response = activityService.updateAnActivity("unfollow", headers, queryParams, "");

		Assert.assertEquals(response.getStatusCode(), 204, "Invalid Status Code Detected");

	}

	@Test(description = "Verify Delete Activity API", dependsOnMethods = { "addAnQuoteTest" })
	public void deleteAnActivityTest() {

		Response response = activityService.deleteAnActivity(activityID, headers, queryParams);

		Assert.assertEquals(response.getStatusCode(), 200, "Invalid Status Code Detected");

	}

	@AfterMethod
	public void clear() {

		headers.clear();
		queryParams.clear();
		activityService = null;

	}

}
