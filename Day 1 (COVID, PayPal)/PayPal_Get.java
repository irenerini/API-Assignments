package practice;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PayPal_Get {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI = "https://api.sandbox.paypal.com/v1/catalogs/products";
		RestAssured.authentication = RestAssured.preemptive()
				.basic("Ae08vm4G9F6TCKcPA8niO219DVK8KzjyDUrumiBgXfKhAw86Ytyo9Qz90CtIpTJQw_scfeLWYvjKwyc2","EJ2vOS31jmNcbp439aExbbP7vYlFgdEWtHtRElkJasp4LnVHWhr4MbWiBr-zV5JbncskoYGZkJow5TN3");
				
		Response response = RestAssured
				.given()
				.log()
				.all()
				.contentType(ContentType.JSON)
				.get();		
		
		response.prettyPrint();
		

	}

}
