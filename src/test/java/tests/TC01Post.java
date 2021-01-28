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
import static io.restassured.RestAssured.*;

public class TC01Post {

    Response response;
    String endpoint = "https://demoqa.com/Account/v1/Authorized";
    Map<String,Object> myPostData = new HashMap<>();
    JsonPath jsonPath;

    public void postMethod(Map body){
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
        String body = "{\n" +
                "  \"userName\": \"ttrycatch\",\n" +
                "  \"password\": \"JQ3iPpTEKTLjSQJ!\"\n" +
                "}";
        response = given().contentType(ContentType.JSON).
                auth().basic("saltuk","Aalen21").
                body(body).
                post(endpoint);
        response.prettyPrint();

        System.out.println(response.getStatusCode());
        System.out.println(response.getContentType());

        Assert.assertEquals((response.prettyPrint()),"false");
        Assert.assertEquals(response.getStatusCode(),200);
        response.then().assertThat().statusCode(HttpStatus.SC_OK).contentType(ContentType.JSON);
    }

    @Test
    public void TC0102(){
        //second way instead body
        Map<String,String> myTestBody = new HashMap<>();
        myTestBody.put("userName","C");
        myTestBody.put("password","JQ3iPpTEKTLjSQJ!");

        postMethod(myTestBody);
//
//        response = given().contentType(ContentType.JSON).
//                auth().oauth2(ConfigReader.getProperty("token")).
//                body(myTestBody).
//                post(endpoint);
//        response.prettyPrint();
        jsonPath = response.jsonPath();

        System.out.println(response.getStatusCode());
        System.out.println(response.getContentType());
        response.then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND).contentType(ContentType.JSON);
        Assert.assertEquals(jsonPath.getString("message"),"User not found!");
    }
    @Test
    public void TC0103(){
        String body = "{\n" +
                "  \"userName\": \"TC0101\",\n" +
                "  \"password\": \"JQ3iPpTEKTLjSQJ!\"\n" +
                "}";
        response = given().contentType(ContentType.JSON).
                auth().oauth2(ConfigReader.getProperty("token")).
                body(body).
                post(endpoint);
        response.prettyPrint();

        jsonPath=response.jsonPath();

        Assert.assertEquals(jsonPath.getString("message"),"User not found!");
        System.out.println(response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(),404);

    }
    @Test
    public void TC0104(){
        // with Pojo
  //    DemoqaPojo1 objem = new DemoqaPojo1("123456","");
        DemoqaPojo1 yeniobjem = new DemoqaPojo1();
        yeniobjem.setUserName("123456");
        yeniobjem.setPassword("");

        response = given().contentType(ContentType.JSON).
                auth().oauth2(ConfigReader.getProperty("token")).
                body(yeniobjem).
                post(endpoint);
        response.prettyPrint();

        jsonPath=response.jsonPath();

        Assert.assertEquals(jsonPath.getString("message"),"UserName and Password required.");
        System.out.println(response.getStatusCode());
        System.out.println(response.getContentType());

    }

}
