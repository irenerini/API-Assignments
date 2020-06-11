package practice;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import com.google.inject.Key;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

public class Covid19Practice {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RestAssured.baseURI = "https://covid-19.dataflowkit.com/v1";
		Response response = RestAssured.get();

		System.out.println("Verify the response HTTP status code " + response.statusCode());

		System.out.println("Verify the Response Time < 600 ms " + response.getTime());
		if (response.getTime() > 600) {
			System.out.println("Response time is more than expected time");
		} else {
			System.out.println("Response time is as expected");
		}

		System.out.println("Verify the Content Type = json" + response.getContentType());


		JsonPath json = response.jsonPath();

		Map<Integer, String> newcase = new TreeMap();
		Map<Integer, String> newcasesort = new LinkedHashMap();
		Map<Integer, String> deathcase = new TreeMap<>();
		
		List<String> couty = json.getList("Country_text");
		List<String> ncase = json.getList("\"New Cases_text\"");
		List<String> dcase = json.getList("\"New Deaths_text\"");
	
		
		List<Map<Object, Object>> list = response.jsonPath().getList("");
		

		for (int i = 0; i < couty.size() - 1; i++) {

				
		if (ncase.get(i) != "" && dcase.get(i) != "") {

				newcase.put(Integer.parseInt(ncase.get(i).replaceAll("[^0-9]", "")), couty.get(i));
				deathcase.put(Integer.parseInt(dcase.get(i).replaceAll("[^0-9]", "")), couty.get(i));
				
			
			}
			
			if(list.get(i).get("Country_text").toString().contentEquals("India")) {
				
				System.out.println("Status of India: " + list.get(i));
			}

		}
		
	
		newcasesort = new TreeMap<>(newcase).descendingMap();
		
		System.out.println("Find the top 5 Country with Highest New Cases");
		
		int count = 0;
		for (String key : newcasesort.values()) {
			if (count == 5) break;
			
			System.out.println(key);
			count++;
		}
		
			
		System.out.println("Find the top 5 Country with lowest New Deaths Cases");
		
		int cout = 0;
		for (String key : deathcase.values()) {
			if (cout == 5) break;
					
			System.out.println(key);
			cout++;
		}
		
		
	}
	
	
	
	
	
	

}
