package com.favqs.tests;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.favqs.base.ActivityService;
import com.favqs.util.PropertyFileUtil;

import io.restassured.response.Response;

public class ActivityTest {

	private static HashMap<String, String> headers;
	private static HashMap<String, String> queryParams;
	private static ActivityService activityService;
	private String activityID;

	@BeforeMethod
	public void setUp() {

		queryParams = new HashMap<>();

		headers = new HashMap<>();
		headers.put("Authorization", PropertyFileUtil.getProperty("Authorization"));
		headers.put("User-Token", PropertyFileUtil.getProperty("User-Token"));

		activityService = new ActivityService();

	}

	@Test(description = "Verify Get Activity API",dependsOnGroups = {"user"}, priority = 1)
	public void getAnActivityTest() {

		Response response = activityService.getAnActivity("", headers, queryParams);

		Assert.assertEquals(response.getStatusCode(), 200, "Invalid Status Code Detected");
		activityID = response.getBody().jsonPath().getString("activities.activity_id");

	}

	@Test(description = "Verify Follow Activity API", priority = 2)
	public void followAnActivityTest() {

		queryParams.put("type", "author");

		Response response = activityService.updateAnActivity("follow", headers, queryParams, "");

		Assert.assertEquals(response.getStatusCode(), 204, "Invalid Status Code Detected");

	}

	@Test(description = "Verify Un-Follow Activity API", dependsOnMethods = { "followAnActivityTest"}, priority = 3)
	public void unfollowAnActivityTest() {

		queryParams.put("type", "author");

		Response response = activityService.updateAnActivity("unfollow", headers, queryParams, "");

		Assert.assertEquals(response.getStatusCode(), 204, "Invalid Status Code Detected");

	}

	@Test(description = "Verify Delete Activity API", dependsOnMethods = { "getAnActivityTest" }, priority = 4)
	public void deleteAnActivityTest() {

		String activity = activityID.replaceAll("[^a-zA-Z0-9]", "");

		Response response = activityService.deleteAnActivity(activity+"11", headers, queryParams);

		Assert.assertEquals(response.getStatusCode(), 200, "Invalid Status Code Detected");

	}

	@AfterMethod
	public void clear() {

		headers.clear();
		queryParams.clear();
		activityService = null;

	}

}
