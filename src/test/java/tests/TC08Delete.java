package tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import java.util.List;

public class TC08Delete {

    Response response;

    NewUser new_user = new  NewUser();
    String userid = new_user.UserID();
    String token = new_user.token();

    String endpointBooksDelete ="https://demoqa.com/BookStore/v1/Books?UserId="+userid;

    String username =new_user.bodyPojo.getUserName().toString();
    String password = new_user.bodyPojo.getPassword().toString();



    public List isbnGet() {
        //tum isbn leri getirelim:
        String endpointBooksGet = "https://demoqa.com/BookStore/v1/Books";
        response = given().
                accept(ContentType.JSON).
                when().
                get(endpointBooksGet);
        List<Object> isbnList = response.jsonPath().getList("books.isbn");
        // System.out.println(isbnList);
        return isbnList;
    }


    public void bookPost() {
        //bu isbn lerden bazilarini user a ekleyelim:
        String endpointBooksPost = "https://demoqa.com/BookStore/v1/Books";
        int a = (int) Math.random()*isbnGet().size();


        String body ="{\n" +
                "  \"userId\": \""+userid+"\",\n" +
                "  \"collectionOfIsbns\": [\n" +
                "    {\n" +
                "      \"isbn\": \""+isbnGet().get(a)+"\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";


        response = given().
                contentType(ContentType.JSON).
                auth().oauth2(token).
                body(body).
                when().
                post(endpointBooksPost);
        //  response.statusCode();
        // response.prettyPrint();

    }

    @Test
    public void deleteBooks() {
        String userName = "ojdiexcelDENmu23";
        String password = "ABCahc987+%&";
        String endPoint = "https://demoqa.com/BookStore/v1/Books?UserId=78b7c691-0c29-41b6-9c88-be94000c6edf";

        Response response = given().
                accept(ContentType.JSON).
                auth().preemptive().basic(userName,password).
                when().
                delete(endPoint);

        response.prettyPrint();

    }



}
