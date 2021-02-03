package tests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;
import pojos.DemoqaPojo1;
import utilities.ConfigReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class TC07Post {

    Response response;
    String endpoint = "https://demoqa.com/BookStore/v1/Books";
    DemoqaPojo1 obj = new DemoqaPojo1();
    Map<String, Object> myPostData = new HashMap<>();
    JsonPath jsonPath;


    public void postMethod(Object body) {
        response = given().contentType(ContentType.JSON).
                auth().oauth2(ConfigReader.getProperty("token")).
                body(body).
                post(endpoint);
        response.prettyPrint();
    }

    @Test
    public void TC0701() {
//        Kullanici adi No Auth ile
//                * "message": "User Id not correct!"
//                *  response un "application/json" tipinde oldugunu
//        *  Status kodunun un 401 oldugunu dogrulayiniz.

        String body = "{\n" +
                "  \"userId\": \"string\",\n" +
                "  \"collectionOfIsbns\": [\n" +
                "    {\n" +
                "      \"isbn\": \"9781449325862\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        postMethod(body);
        System.out.println(response.getContentType());
        System.out.println(response.statusCode());

        response.then().assertThat().contentType("application/json").statusCode(HttpStatus.SC_UNAUTHORIZED);
        jsonPath = response.jsonPath();
        Assert.assertTrue(jsonPath.getString("message").equals("User Id not correct!"));
    }

    @Test
    public void TC0702() {

//        Kullanici adi  "ahmet" ve sifresi "Zahmet01#02" olan
//                *   "code": "1200",    "message": "User not authorized!"
//                * Status kodunun un 401 oldugunu dogrulayiniz.

        String body = "{\n" +
                "  \"userId\": \"string\",\n" +
                "  \"collectionOfIsbns\": [\n" +
                "    {\n" +
                "      \"isbn\": \"string\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        response = given().contentType(ContentType.JSON).
                auth().basic("ahmet", "Zahmet01#02").
                body(body).
                post(endpoint);
        response.prettyPrint();
        System.out.println(response.getContentType());
        System.out.println(response.statusCode());

        response.then().assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED);
        jsonPath = response.jsonPath();

        Assert.assertTrue(jsonPath.getString("code").equals(String.valueOf(1200)));
        Assert.assertTrue(jsonPath.getString("message").equals("User not authorized!"));


    }

    @Test
    public void TC0703() {
//        Kullanici adi  "ahmet" ve sifresi "Zahmet01#02" olan  user id = "cf581dae-6ea5-4eea-9b8d-5d8fb200d038"
//        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyTmFtZSI6ImFobWV0IiwicGFzc3dvcmQiOiJaYWhtZXQwMSMwMiIsImlhdCI6MTYxMjIxNDYxNn0.a7Py0iME3ptIZHy1UOqgUzYvpQoRzK-DIA_Ru0XgWUo";
//                * kullanicinin sepetinde 3 adet kitap oldugunu


        response = given().accept(ContentType.JSON).
                auth().oauth2(ConfigReader.getProperty("token")).
                when().
                get(endpoint);
        response.prettyPrint();
        jsonPath = response.jsonPath();

        List<String> listOfBooksAhmet = jsonPath.getList("books");
        System.out.println("Ahmet toplam kitap : " + listOfBooksAhmet.size());
        Assert.assertEquals(listOfBooksAhmet.size(), 8);
    }

    @Test
    public void TC0704() {
        //        * Book Store'dan "Speaking JavaScript" isimli kitabi sepete eklendigini dogrulayiniz.
//                *Status kodunun un 201 oldugunu dogrulayiniz.
        String body = "{\n" +
                "  \"userId\": \"cf581dae-6ea5-4eea-9b8d-5d8fb200d038\",\n" +
                "  \"collectionOfIsbns\": [\n" +
                "    {\n" +
                "      \"isbn\": \"9781593275846\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        String token1 = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyTmFtZSI6ImFobWV0IiwicGFzc3dvcmQiOiJaYWhtZXQwMSMwMiIsImlhdCI6MTYxMjIxNDYxNn0.a7Py0iME3ptIZHy1UOqgUzYvpQoRzK-DIA_Ru0XgWUo";
        response = given().contentType(ContentType.JSON).
                auth().preemptive().basic("ahmet","Zahmet01#02").
                body(body).
                post(endpoint);
        response.prettyPrint();
        System.out.println(response.statusCode());

        if(response.statusCode()==201){
            System.out.println("Kitap eklendi");
        }else{
            System.out.println("Kitap önceden eklenmis");
        }

    }

}
