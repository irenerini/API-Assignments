package practice;

import java.io.File;
import java.util.List;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class TomTom {

	public static String adminKey;
	public static String key = "XOzEPkhXYG7ElG3K0GZGQmgJ68xJUuo4";
	static String myprojectID;
	static String fenceid;
	static String fenceproj = "e1200373-8e43-4807-bc05-015c8932742e";

	
	@Test (priority = 1)
	public static void generateKey() {

		File jsonfile = new File("./data/tomtomadminkey.json");

		RestAssured.baseURI = "https://api.tomtom.com/geofencing/1";

		Response response = RestAssured.given().log().all().contentType(ContentType.JSON).body(jsonfile)
				.post("/regenerateKey?key="+key);

		response.prettyPrint();
		
		response.then().assertThat().statusCode(200);

		adminKey = response.body().path("adminKey");
		System.out.println(adminKey);

	}

	@Test (dependsOnMethods = "generateKey")
	public void createProject() {

		File projectfile = new File("./data/tomtompostproject.json");

		RestAssured.baseURI = "https://api.tomtom.com/geofencing/1";

		Response response = RestAssured.given().log().all().contentType(ContentType.JSON).body(projectfile)
				.post("/projects/project?key=" + key + "&adminKey=" + adminKey);

		response.prettyPrint();

		response.then().assertThat().statusCode(201);

		myprojectID = response.body().path("id");
		//myprojectID = "hdghsddf";
		System.out.println(myprojectID);

	}

	@Test(dependsOnMethods = "createProject")
	public void getProjects() {

		RestAssured.baseURI = "https://api.tomtom.com/geofencing/1";

		Response response = RestAssured.given().log().all().contentType(ContentType.JSON).get("projects?key=" + key);

		response.prettyPrint();

		response.then().assertThat().statusCode(200);

		List<String> projectid = response.jsonPath().getList("projects.id");

		for (String id : projectid) {

			System.out.println(id);

			if (id.contentEquals(myprojectID)) {
				System.out.println("******"+id+"******");
				System.out.println("Newly created project is present");
			}
			
		}

	}
	
	@Test (dependsOnMethods = "generateKey")
	public void createFence() {

		File fencefile = new File("./data/tomtomcreatefence.json");

		RestAssured.baseURI = "https://api.tomtom.com/geofencing/1";

		Response response = RestAssured.given().log().all().contentType(ContentType.JSON).body(fencefile)
				.post("/projects/"+ fenceproj +"/fence?key=" + key + "&adminKey=" + adminKey);

		response.prettyPrint();

		response.then().assertThat().statusCode(201);
		
		fenceid = response.body().path("id");
		System.out.println(fenceid);

	}
	
	@Test(dependsOnMethods = "createFence")
		public void getFence() {

			RestAssured.baseURI = "https://api.tomtom.com/geofencing/1";

			Response response = RestAssured.given().log().all().contentType(ContentType.JSON).get("projects/"+fenceproj+"/fences?key="+key);

			response.prettyPrint();

			response.then().assertThat().statusCode(200);

			List<String> fenceids = response.jsonPath().getList("fences.id");

			for (String fnid : fenceids) {

				System.out.println(fnid);

				if (fnid.contentEquals(fenceid)) {
					System.out.println("****"+fnid+"****");
					System.out.println("Newly created fence is present");
				}
				
			}

		}
	
	
}
