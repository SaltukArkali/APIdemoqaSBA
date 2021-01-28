package tests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.testng.annotations.Test;
import pojos.DemoqaPojo1;
import utilities.ConfigReader;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class TC02Post {

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

    @Test
    public void get(){
        response = given().when().get(endpoint);
        response.prettyPrint();
    }
    @Test
    public void TC0101(){
        obj.setUserName("Fadime");
        obj.setPassword("Almanya66");
        postMethod(obj);
        jsonPath=response.jsonPath();

        response.then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND).contentType(ContentType.JSON);
        Assert.assertEquals(jsonPath.getString("message"),"User not found!");

        System.out.println(response.getStatusCode());
        System.out.println(response.getContentType());

//        Assert.assertEquals(response.getStatusCode(),"200");
//        Assert.assertEquals(response.getContentType(),"application/json");
    }



}

