package practice;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class BestBuy_GetStore {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI = "https://api.bestbuy.com";
		
		getRecords("******Get Store Name********","v1/stores(area(02864,10))?format=json&show=storeId,name,address,distance");
		getRecords("******Canon Price Range********","v1/products(manufacturer=canon&regularPrice>1000&regularPrice<1500)?format=json");
		getRecords("******iPhone XR Prices********","v1/products(name=iPhone XR*&productTemplate=Cell_Phones)?show=name,regularPrice,salePrice&format=json");
		getRecords("******Store Avialibity for 02886********","v1/products/5580432/stores.json?postalCode=02886");
		getRecords("******Store Avialibity for 55109********","v1/products/5580432/stores.json?postalCode=55109");
		
		

	}
	
	public static void getRecords(String Scenario, String UPath) {
		
		System.out.println(Scenario);
		String UrlPath = UPath;
		RequestSpecification httpRequest = RestAssured.given();
		
		Response response = httpRequest
				.given()
				.get(UPath+"&apiKey=qUh3qMK14GdwAs9bO59QRSCJ");			
				
		ResponseBody body = response.body();
		response.prettyPrint();
		
	}

}
