package tests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import pojos.PojoDeneme;
import utilities.ConfigReader;

import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;

public class TC04Delete {

    Response response;
    PojoDeneme pojo = new PojoDeneme();
    JsonPath jsonPath;

    @Test
    public void get() {
        String endpoint = "https://demoqa.com/Account/v1/User/b9f06ec7-88d9-4369-b40e-703e5b884d55";
        response = given().accept(ContentType.JSON).
                auth().oauth2(ConfigReader.getProperty("tokenDelete")).
                when().
                get(endpoint);
        response.prettyPrint();

        response.then().assertThat().body("books", Matchers.hasSize(3));

        response = given().auth().oauth2("token").when().delete();
        response.prettyPrint();

    }



    public static String random(int q,int x,int y){
        String randomString="";
        for(int i=0;i<q;i++) {
            char randomChar = (char) ThreadLocalRandom.current().nextInt(x, y);
            randomString += randomChar;
        }
        return randomString;
    }

    public String  name(){
        String upperString= random(3,65,90);
        String lowerString = random(4,97,122);
        String name= upperString+lowerString;
//      System.out.println(name);
        pojo.setUserName(name);
        return name;
    }

    public String  password(){
        String upperString= random(2,65,90);
        String lowerString = random(2,97,122);
        String digits = random(2,48,57);
        String specialchar = random(2,33,38);
        String password= upperString+lowerString+digits+specialchar;
 //     System.out.println(password);
        pojo.setPassword(password);
        return password;
    }

//    @Test
//    public void userID() {
//        System.out.println("name     : " +name()+"\n"+ "password  : "+ password());
//
//        response=given().
//                contentType(ContentType.JSON).
//                auth().basic(name(),password()).
//                body(pojo).
//                post("https://demoqa.com/Acoount/v1/User");
//        response.prettyPrint();
//        jsonPath = response.jsonPath();
//        String userID = jsonPath.getString("userID");
//        System.out.println(userID);
//
//    }

    @Test
    public void UserID(){

        response= given().
                contentType(ContentType.JSON).
                auth().basic(name(),password()).
                body(pojo).
                when().
                post("https://demoqa.com/Account/v1/User");
        response.prettyPrint();
        jsonPath= response.jsonPath();
        System.out.println(jsonPath.getString("userID"));
        String userID = jsonPath.getString("userID");
    }

    //token üretelim:
    public String token() {
        response = given()
                .contentType(ContentType.JSON)
                .auth().basic("samet1", "ABCabc987+%&")
                .body(pojo)
                .when()
                .post("https://demoqa.com/Account/v1/GenerateToken");
        response.prettyPrint();
        jsonPath = response.jsonPath();
        System.out.println(jsonPath.getString("token"));
        String token = jsonPath.getString("token");
        return token;

    }


}

