package com.favqs.tests;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.favqs.base.UsersService;
import com.favqs.util.PropertyFileUtil;
import com.github.javafaker.Faker;
import com.model.request.CreateUserRequestPayload;
import com.model.response.GetUserResponsePayload;

import io.restassured.response.Response;

public class UsersTest {

	private static HashMap<String, String> headers;
	private static HashMap<String, String> pathParams;
	private static UsersService userService;
	private static String login;
	private static String email;

	@BeforeMethod
	public void setUp() {

		pathParams = new HashMap<String, String>();

		headers = new HashMap<String, String>();
		headers.put("Authorization", PropertyFileUtil.getProperty("Authorization"));

		userService = new UsersService();

	}

	@Test(description = "Verify Create Users API")
	public void createAnUserTest() {

		Faker randomData = new Faker();
		login = randomData.name().firstName().concat("_test").toLowerCase();
		email = login.concat("@gamil.com");
		String Password = randomData.internet().password();

		CreateUserRequestPayload userPayload = new CreateUserRequestPayload(login, email, Password);

		Response response = userService.createAnUser("", headers, pathParams, userPayload);

		Assert.assertEquals(response.getStatusCode(), 200, "Invalid Status Code Detected");
		Assert.assertEquals(response.getBody().jsonPath().getString("login"), login, "Invalid Login Name Detected");

		PropertyFileUtil.setProperty("User-Token", response.getBody().jsonPath().getString("User-Token"));

		System.out.println(response.then().log().all());

	}

	@Test(description = "Verify Get Users API", dependsOnMethods = { "createAnUserTest" },priority =1)
	public void getAnUserTest() {

		headers.put("User-Token", PropertyFileUtil.getProperty("User-Token"));

		Response response = userService.getAnUser(login, headers, pathParams);
		GetUserResponsePayload responsePayload = response.as(GetUserResponsePayload.class);

		Assert.assertEquals(response.getStatusCode(), 200, "Invalid Status Code Detected");
		Assert.assertEquals(responsePayload.getLogin(), login, "Invalid Login Name Detected");

		System.out.println(response.then().log().all());

	}
	
	
	@Test(description = "Verify Update Users API", dependsOnMethods = { "createAnUserTest" },priority =2)
	public void updateAnUserTest() {
		
		Faker randomData = new Faker();
		String updated_Login = randomData.name().firstName().concat("_test").toLowerCase();
		String updated_Email = login.concat("@gamil.com");
		String Password = randomData.internet().password();

		CreateUserRequestPayload userPayload = new CreateUserRequestPayload(updated_Login, updated_Email, Password);


		headers.put("User-Token", PropertyFileUtil.getProperty("User-Token"));

		Response response = userService.updateAnUser(login, headers, pathParams,userPayload);

		Assert.assertEquals(response.getStatusCode(), 200, "Invalid Status Code Detected");
		Assert.assertEquals(response.getBody().jsonPath().getString("message"), "User successfully updated.", "Invalid Message Detected");

		System.out.println(response.then().log().all());

	}


	@AfterMethod
	public void clear() {

		headers.clear();
		pathParams.clear();
		userService = null;
		 

	}

}
