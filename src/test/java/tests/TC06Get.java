package tests;

import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.testng.annotations.Test;
import pages.Book;
import utilities.ConfigReader;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

public class TC06Get {

    Response response;
    String endpoint= "https://demoqa.com/BookStore/v1/Books";
    JsonPath jsonPath;

    public void getMethod(){
        response=given().
                accept(ContentType.JSON).
                auth().oauth2(ConfigReader.getProperty("token")).
                when().
                get(endpoint);
        // response.prettyPrint();
        jsonPath=response.jsonPath();
    }

    @Test
    public void TC0601(){
//        1- Kullanici herbir objenin;
//        "isbn":
//        "title":
//        "subTitle":
//        "author":
//        "publish_date":
//        "publisher":
//        "pages":
//        "description":
//        "website":
//        keylerine sahip ve satatus kodunun 200 oldugunu verify eder.

        getMethod();

        response.then().assertThat().statusCode(HttpStatus.SC_OK);

        assertTrue(response.getBody().asString().contains("isbn"));
        assertTrue(response.getBody().asString().contains("title"));
        assertTrue(response.getBody().asString().contains("subTitle"));
        assertTrue(response.getBody().asString().contains("author"));
        assertTrue(response.getBody().asString().contains("publish_date"));
        assertTrue(response.getBody().asString().contains("publisher"));
        assertTrue(response.getBody().asString().contains("pages"));
        assertTrue(response.getBody().asString().contains("description"));
        assertTrue(response.getBody().asString().contains("website"));

    }

    @Test
    public void TC0602(){
//        2- Kullanici;
//              1. entpoint'in icinde toplam 8 kitap(obje) oldugunu dogrular.
//              2. 2020 yilinda basilan kitaplarin sayisini bularak, asert eder.
        getMethod();

        List<String> allBooksList = jsonPath.getList("books");
        System.out.println("AllBooks  : " + allBooksList.size());
        Assert.assertEquals(allBooksList.size(),8);

        int count=0;
        for (int i=0;i<allBooksList.size()-1;i++) {
            String time = jsonPath.getString("books["+i+"].publish_date").substring(0,4);
            System.out.println(time);
            if (time.equals("2020")){
                Assert.assertTrue(time.equals("2020"));
                count++;}
        }
        System.out.println("Number of published books in 2020 : " + count);
    }
    @Test
    public void TC0603(){
        //3- Kullanici; herbir objenin "website" kisminin bos olmadigini dogrular.
        getMethod();
        Assert.assertFalse(jsonPath.getString("books.website").isEmpty());
    }
    @Test
    public void TC0604(){
//        4- Kullanici;
//               1. kitablarin "isbn" numaralarinin esit uzunlukta oldugunu sececegi uc kitabin isbn numaralarini karsilastirarak dogrular.
//               2. "isbn" numaralarinin uniq oldugunu dogrular.
        getMethod();
        String isbn1 = jsonPath.getString("books[0].isbn");
        String isbn2 = jsonPath.getString("books[1].isbn");
        String isbn3 = jsonPath.getString("books[2].isbn");
        System.out.println("1 : " +isbn1 + "\n2 : " + isbn2 + "\n3 : " + isbn3);
        Assert.assertEquals(isbn1.length(),isbn2.length(),isbn3.length());

        List<Integer> isbnList = jsonPath.getList("books.isbn");
        System.out.println(isbnList);

        for (int i=0;i<isbnList.size()-1;i++) {
            String isbnA = jsonPath.getString("books["+i+"].isbn");
            String isbnB = jsonPath.getString("books["+(i+1)+"].isbn");
           Assert.assertFalse(isbnA.equals(isbnB));
        }
    }

}
