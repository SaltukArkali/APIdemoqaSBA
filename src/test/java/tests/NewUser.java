package tests;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import pojos.DemoqaPojo1;

import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;

public class NewUser {
    Response response;
    JsonPath json;
    DemoqaPojo1 bodyPojo = new DemoqaPojo1();
    String token;


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
        // System.out.println("username="+name);
        bodyPojo.setUserName(name);
        return name;
    }

    public String  password(){
        String upperString= random(2,65,90);
        String lowerString = random(2,97,122);
        String digits = random(2,48,57);
        String specialchar = random(2,33,38);
        String password= upperString+lowerString+digits+specialchar;
        //System.out.println("password= "+password);
        bodyPojo.setPassword("userpassword="+password);
        return password;
    }

    //UserID üretelim:
    public String UserID(){
        response= given().
                contentType(ContentType.JSON).
                auth().basic(name(),password()).
                body(bodyPojo).
                when().
                post("https://demoqa.com/Account/v1/User");
        //response.prettyPrint();
        json= response.jsonPath();
        //  System.out.println(json.getString("userID"));
        String userID = json.getString("userID");
        return userID;
    }

    //authorization yapip token üretelim:
    public String token(){
        response = given().
                contentType(ContentType.JSON).
                auth().basic("Zehra","1234!Name").
                body(bodyPojo).
                when().
                post("https://demoqa.com/Account/v1/GenerateToken");
        //response.prettyPrint();
        json = response.jsonPath();
        //  System.out.println(json.getString("token"));
        token =json.getString("token");
        return token;
    }

}