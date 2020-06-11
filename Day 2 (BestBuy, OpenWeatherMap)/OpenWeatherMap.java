package practice;

import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class OpenWeatherMap {

	@Test(dataProvider = "getData")
	public static void weather(String val) throws IOException {

		RestAssured.baseURI = "https://api.openweathermap.org";

		Response response = RestAssured.given().log().all()
				.get("/data/2.5/weather?q=" + val + "&appid=05c248e1e379c2410a4cd59e22bb347f");

		String main = response.body().path("weather[0].main");
		System.out.println(main);

		String city = response.body().path("name");

		if (main.contains("Clouds") || main.contains("Thunderstorm")) {
			System.out.println("***********" + city);
		}

		response.prettyPrint();

	}

	@DataProvider(name = "getData")
	public static String[] getData() throws IOException {

		XSSFWorkbook wbook = new XSSFWorkbook("./data/city_list.xlsx");
		XSSFSheet sheet = wbook.getSheet("Sheet1");
		int lastRowNum = sheet.getLastRowNum();
		System.out.println(lastRowNum);
		String[] data = new String[lastRowNum];

		for (int j = 1; j <= lastRowNum; j++) {

			XSSFRow row = sheet.getRow(j);

			XSSFCell cell = row.getCell(1);
			String value = cell.getStringCellValue();
			// System.out.println("*********"+value);
			data[j - 1] = value;
		}

		// System.out.println(data[0]);
		// System.out.println(data[1]);
		wbook.close();
		return data;

	}

}
