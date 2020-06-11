package practice;

import java.io.File;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PayPal_Post {

	
	@DataProvider (name = "files", parallel = true)
	public String[][] productData() {
		
		String[][] fileNames = new String[2][1];
		fileNames[0][0] = "./Paypal_Post.json";
		fileNames[1][0] = "./Paypal_Post2.json";
		
		return fileNames;
	}
	
	
	
	
	@Test (dataProvider = "files")
	public  void postProduct(String fileNames) {
		// TODO Auto-generated method stub
		
		File jsonFile = new File(fileNames);
		
		RestAssured.baseURI = "https://api.sandbox.paypal.com/v1/catalogs/products";
		RestAssured.authentication = RestAssured.preemptive()
				.basic("Ae08vm4G9F6TCKcPA8niO219DVK8KzjyDUrumiBgXfKhAw86Ytyo9Qz90CtIpTJQw_scfeLWYvjKwyc2","EJ2vOS31jmNcbp439aExbbP7vYlFgdEWtHtRElkJasp4LnVHWhr4MbWiBr-zV5JbncskoYGZkJow5TN3");
		
		
		Response response = RestAssured
							.given()
							.contentType(ContentType.JSON)
							.body(jsonFile)
							.post();
		
		response.prettyPrint();
		
		System.out.println("Verify the response HTTP status code " + response.statusCode());

		System.out.println("Verify the Response Time < 600 ms " + response.getTime());
		

	}

}
