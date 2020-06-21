package practice;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UiBank {

	public static String token;
	public static String loanid;

	@BeforeSuite
	public static void bankLogin() {

		File jsonfile = new File("loginfile.json");

		RestAssured.baseURI = "https://uibank-api.azurewebsites.net/api";

		Response response = RestAssured.given().log().all().contentType(ContentType.JSON).body(jsonfile)
				.post("/users/login");

		response.prettyPrint();
		
		response.then().assertThat().statusCode(200);

		token = response.body().path("id");
		System.out.println(token);

	}

	@Test (priority = 1)
	public static void createAccount() {

		File acctfile = new File("UiBank_Account.json");

		Response response = RestAssured.given().headers("Authorization", "Bearer " + token).log().all()
				.contentType(ContentType.JSON).body(acctfile).post("/accounts");

		response.then().assertThat().statusCode(200);

		response.prettyPrint();

	}

	@Test (dependsOnMethods = "createAccount")
	public static void getAccountDetails() {

		Response response = RestAssured.given().headers("Authorization", "Bearer " + token).log().all()
				.contentType(ContentType.JSON).get("/accounts");

		response.then().assertThat().statusCode(200);

		response.prettyPrint();

	}

	@Test (priority = 2)
	public static void applyLoan() {

		File loanfile = new File("./UiBank_Loan.json");

		Response response = RestAssured.given().headers("Authorization", "Bearer " + token).log().all()
				.contentType(ContentType.JSON).body(loanfile).post("/quotes/newquote");

		loanid = response.body().path("quoteid");

		response.then().assertThat().statusCode(200);

		response.prettyPrint();

	}

	@Test (dependsOnMethods = "applyLoan")
	public static void getLoanStatus() {

		Response response = RestAssured.given().headers("Authorization", "Bearer " + token).log().all()
				.contentType(ContentType.JSON).get("/quotes/"+ loanid);

		response.then().assertThat().statusCode(200);

		response.prettyPrint();

	}

}
