package tests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TC05Get {

    Response response;
    JsonPath json;



    @Test
    public void TC0504() throws IOException {
    /*"When I send a GET request to REST API URL
            https://demoqa.com/Account/v1/User/ee45e996-0a24-4a32-847d-aa7533f32de9
            Then HTTP Status Code should be 200
            And response content type is “application/JSON”
            And ""username""  schould be ""Name"",  And user has two books
            And first book data should be like;
         {
            ""isbn"": ""9781449325862"",
            ""title"": ""Git Pocket Guide"",
            ""subTitle"": ""A Working Introduction"",
            ""author"": ""Richard E. Silverman"",
            ""publish_date"": ""2020-06-04T08:48:39.000Z"",
            ""publisher"": ""O'Reilly Media"",
            ""pages"": 234,
            ""description"": ""This pocket guide is the perfect on-the-job companion to Git, the distributed
                      version control system. It provides a compact, readable introduction to Git for new users,
                      as well as a reference to common commands and procedures for those of you with Git exp"",
            ""website"": ""http://chimera.labs.oreilly.com/books/1230000000561/index.html""
        }   */
        String endpoint = "https://demoqa.com/Account/v1/User/71a98051-8e31-4949-a311-eda781a1f4d0";
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyTmFtZSI6Ik5hbWUiLCJwYXNzd29yZCI6IjEyMzQhTmFtZ" +
                "SIsImlhdCI6MTYxMTk1NjM3OX0.96Bakdv_QWzIvCzvdJJKTjupoW1mpUUujkHb3GxrgJs";

        response = given().
                contentType(ContentType.JSON).
                auth().oauth2(token).
                when().
                get(endpoint);
        response.prettyPrint();
        json = response.jsonPath();

        response.
                then().
                assertThat().
                statusCode(200).
                contentType("application/JSON");
        assertEquals(json.getString("username"),"Name");
        List<String> books = json.getList("books");
        assertTrue(books.size()==2);

//        demoqaGetPojo = objectMapper.readValue(response.asString(), DemoqaGetPojo.class);
//        assertEquals(demoqaGetPojo.getBooks().get(0).getIsbn(),"9781449325862");
//        assertEquals(demoqaGetPojo.getBooks().get(0).getTitle(),"Git Pocket Guide");
//        assertEquals(demoqaGetPojo.getBooks().get(0).getSubTitle(),"A Working Introduction");
//        assertEquals(demoqaGetPojo.getBooks().get(0).getAuthor(),"Richard E. Silverman");
//        assertEquals(demoqaGetPojo.getBooks().get(0).getPublishDate(),"2020-06-04T08:48:39.000Z");
//        assertEquals(demoqaGetPojo.getBooks().get(0).getPublisher(),"O'Reilly Media");
//        assertTrue(demoqaGetPojo.getBooks().get(0).getPages()== 234);
//        assertTrue(demoqaGetPojo.getBooks().get(0).getDescription().startsWith("This pocket guide is the"));
//        String website = "http://chimera.labs.oreilly.com/books/1230000000561/index.html";
//        assertEquals(demoqaGetPojo.getBooks().get(0).getWebsite(),website);

    }



}
