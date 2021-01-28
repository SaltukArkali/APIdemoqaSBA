package tests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
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
                auth().oauth2(ConfigReader.getProperty("token")).
                body(body).
                post(endpoint);
        response.prettyPrint();

        System.out.println(response.getStatusCode());
        System.out.println(response.getContentType());

//        Assert.assertEquals(response.getStatusCode(),"200");
//        Assert.assertEquals(response.getContentType(),"application/json");
    }

    @Test
    public void TC0102(){
        //second way instead body
        Map<String,String> myTestBody = new HashMap<>();
        myTestBody.put("userName","C");
        myTestBody.put("password","JQ3iPpTEKTLjSQJ!");

        response = given().contentType(ContentType.JSON).
                auth().oauth2(ConfigReader.getProperty("token")).
                body(myTestBody).
                post(endpoint);
        response.prettyPrint();

        System.out.println(response.getStatusCode());
        System.out.println(response.getContentType());

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

        Assert.assertTrue(response.asString().contains("User not found!"));
        System.out.println(response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(),"404");

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

        System.out.println(response.getStatusCode());
        System.out.println(response.getContentType());

    }

}
