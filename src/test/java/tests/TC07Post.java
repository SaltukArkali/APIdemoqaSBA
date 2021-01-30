package tests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import pojos.DemoqaPojo1;
import utilities.ConfigReader;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class TC07Post {

    Response response;
    String endpoint = "https://demoqa.com/Account/v1/Authorized";
    DemoqaPojo1 obj = new DemoqaPojo1();
    Map<String,Object> myPostData = new HashMap<>();
    JsonPath jsonPath;


    public void postMethod(Object body){
        response = given().contentType(ContentType.JSON).
                auth().oauth2(ConfigReader.getProperty("token")).
                body(body).
                post(endpoint);
        response.prettyPrint();
    }







}
