package com.favqs.tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class UsersTest {

	private static HashMap<String, String> headers;
	private static HashMap<String, String> queryParams;
	private static UsersService userService;
	private static String login;

    @BeforeMethod
	public void setUp() {

		queryParams = new HashMap<>();

		headers = new HashMap<>();
		headers.put("Authorization", PropertyFileUtil.getProperty("Authorization"));

		userService = new UsersService();

	}

	@Test(description = "Verify Create Users API",groups = {"user"}, priority = 1)
	public void createAnUserTest() {

		Faker randomData = new Faker();
		login = randomData.name().firstName().concat("_test_user").toLowerCase();
        String email = login.concat("@gmail.com");
		String Password = randomData.internet().password();

		CreateUserRequestPayload userPayload = new CreateUserRequestPayload(login, email, Password);

		Response response = userService.createAnUser("", headers, queryParams, userPayload);

		Assert.assertEquals(response.getStatusCode(), 200, "Invalid Status Code Detected");
		Assert.assertEquals(response.getBody().jsonPath().getString("login"), login, "Invalid Login Name Detected");

		PropertyFileUtil.setProperty("User-Token", response.getBody().jsonPath().getString("User-Token"));

	}

	@Test(description = "Verify Get Users API", dependsOnMethods = { "createAnUserTest" }, priority = 2)
	public void getAnUserTest() throws IOException {

		String schemaPath=System.getProperty("user.dir")+"/test-data/Schemas/GetUserResponseSchema.schema.json";
		headers.put("User-Token", PropertyFileUtil.getProperty("User-Token"));

		Response response = userService.getAnUser(login, headers, queryParams);
		GetUserResponsePayload responsePayload = response.as(GetUserResponsePayload.class);

		Assert.assertEquals(response.getStatusCode(), 200, "Invalid Status Code Detected");
		Assert.assertEquals(responsePayload.getLogin(), login, "Invalid Login Name Detected");
		
		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema( new String (Files.readAllBytes(Paths.get(schemaPath)))));

	}

	@Test(description = "Verify Update Users API", dependsOnMethods = { "createAnUserTest" }, priority = 3)
	public void updateAnUserTest() {

		Faker randomData = new Faker();
		String updated_Login = randomData.name().firstName().concat("_test").toLowerCase();
		String updated_Email = login.concat("@gmail.com");
		String Password = randomData.internet().password();

		CreateUserRequestPayload userPayload = new CreateUserRequestPayload(updated_Login, updated_Email, Password);

		headers.put("User-Token", PropertyFileUtil.getProperty("User-Token"));

		Response response = userService.updateAnUser(login, headers, queryParams, userPayload);

		Assert.assertEquals(response.getStatusCode(), 200, "Invalid Status Code Detected");
		Assert.assertEquals(response.getBody().jsonPath().getString("message"), "User successfully updated.",
				"Invalid Message Detected");

	}

	@AfterMethod
	public void clear() {

		headers.clear();
		queryParams.clear();
		userService = null;

	}

}
