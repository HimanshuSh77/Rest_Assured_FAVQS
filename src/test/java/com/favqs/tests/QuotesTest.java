package com.favqs.tests;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.favqs.base.QuotesService;
import com.favqs.base.UsersService;
import com.favqs.util.PropertyFileUtil;
import com.github.javafaker.Faker;
import com.model.request.CreateQuoteRequestPayload;
import com.model.request.CreateUserRequestPayload;
import com.model.response.GetUserResponsePayload;

import io.restassured.response.Response;

public class QuotesTest {

	private static HashMap<String, String> headers;
	private static HashMap<String, String> queryParams;
	private static QuotesService quoteService;
	private String quoteId;
	private String author;
	private String quote;

	@BeforeMethod
	public void setUp() {

		queryParams = new HashMap<String, String>();

		headers = new HashMap<String, String>();
		headers.put("Authorization", PropertyFileUtil.getProperty("Authorization"));
		headers.put("User-Token", PropertyFileUtil.getProperty("User-Token"));

		quoteService = new QuotesService();

	}

	@Test(description = "Verify Add Quote API")
	public void addAnQuoteTest() {

		Faker randomData = new Faker();
		author = randomData.name().fullName();
		quote = randomData.shakespeare().hamletQuote();

		CreateQuoteRequestPayload requestPayload = new CreateQuoteRequestPayload(author, quote);

		Response response = quoteService.addAnQuote("", headers, queryParams, requestPayload);

		Assert.assertEquals(response.getStatusCode(), 200, "Invalid Status Code Detected");
		Assert.assertEquals(response.getBody().jsonPath().getString("author"), author, "Invalid Author Name Detected");
		Assert.assertEquals(response.getBody().jsonPath().getString("body"), quote, "Invalid Author Name Detected");

		quoteId = response.getBody().jsonPath().getString("id");
		System.out.println(response.then().log().all());

	}

	@Test(description = "Verify Hide Quote API", dependsOnMethods = {"addAnQuoteTest"},priority = 2)
	public void hideAnQuoteTest() {

		Response response = quoteService.updateAnQuote(quoteId + "/hide", headers, queryParams, "");

		Assert.assertEquals(response.getStatusCode(), 200, "Invalid Status Code Detected");
		Assert.assertEquals(response.getBody().jsonPath().getString("author"), author, "Invalid Author Name Detected");
		Assert.assertEquals(response.getBody().jsonPath().getString("body"), quote, "Invalid Author Name Detected");
		Assert.assertTrue(response.getBody().jsonPath().getBoolean("user_details.hidden"), "Quote is Not Hidded Yet");

		System.out.println(response.then().log().all());

	}

	@Test(description = "Verify Mark Favourite Quote API", dependsOnMethods = {"addAnQuoteTest"},priority = 1)
	public void markAnQuoteFavTest() {

		Response response = quoteService.updateAnQuote(quoteId + "/fav", headers, queryParams, "");

		Assert.assertEquals(response.getStatusCode(), 200, "Invalid Status Code Detected");
		Assert.assertEquals(response.getBody().jsonPath().getString("author"), author, "Invalid Author Name Detected");
		Assert.assertEquals(response.getBody().jsonPath().getString("body"), quote, "Invalid Author Name Detected");
		Assert.assertTrue(response.getBody().jsonPath().getBoolean("user_details.favorite"), "Quote is Not Hidded Yet");

		System.out.println(response.then().log().all());

	}

	
	
	@AfterMethod
	public void clear() {

		headers.clear();
		queryParams.clear();
		quoteService = null;

	}

}
