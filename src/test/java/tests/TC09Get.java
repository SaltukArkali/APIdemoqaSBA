package tests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.testng.annotations.Test;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static io.restassured.RestAssured.given;

public class TC09Get {

    Response response;
    JsonPath jsonPath;
    String endPoint = "https://demoqa.com/BookStore/v1/Book?ISBN=9781593277574";
    String isbnNumber = "9781593277574";

    Response getResponse(String endPoint, String isbnNumber) {
        response = given().
                queryParam("ISBN", isbnNumber).
                when().
                get(endPoint);
        return response;
    }

    @Test
    public void TC0901() {
        //Kullanici BookStore-Get'i acabilmeli ve ISBN Kutucuguna "9781593277574" ISBN kodunu girip execute edebilmelidir. (Assert)
        getResponse(endPoint, isbnNumber).prettyPrint();
        Assert.assertFalse(response.equals(null));
    }

    @Test
    public void TC0902() {
        //    Kullanici "9781593277574" ISBN kodunu girdiginde ve execute ettiginde sirasiyla asagidakilerini Verify edebilmelidir:
        //            1. Status codun==> 200 oldugunu,
        //            2. "subTitle" in ==> "The Definitive Guide for JavaScript Developers" oldugunu,
        //            3. "publish_date" in ==> "2016-09-03T00:00:00.000Z" oldugunu
        //        4.  "pages" in==> 352 oldugunu,
        //            5. "description" inin ==> "ECMAScript 6 represents the biggest update" ile baslayip,
        //"that E" ile bittigini ve icinde "expert developer Nicholas C. Zakas" isminin gectigini,
        //        6. Söz konusu Kitabin Download edilip edilmedigini.(???)
        Response response2 = getResponse(endPoint, isbnNumber);
        response2.then().assertThat().statusCode(HttpStatus.SC_OK).body("subTitle", Matchers.equalTo("The Definitive Guide for JavaScript Developers"),
                "publish_date", Matchers.equalTo("2016-09-03T00:00:00.000Z"),
                "pages", Matchers.equalTo(352));
        jsonPath = response.jsonPath();
        Assert.assertTrue(jsonPath.getString("description").startsWith("ECMAScript 6 represents the biggest update"));
        Assert.assertTrue(jsonPath.getString("description").endsWith("that E"));
        Assert.assertTrue(jsonPath.getString("description").contains("expert developer Nicholas C. Zakas"));
    }

    String returnTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("E, dd MMM yyy");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
//        yyyy-MM-dd	"1988-09-29"
//        dd/MM/yyyy	"29/09/1988"
//        dd-MMM-yyyy	"29-Sep-1988"
//        E, MMM dd yyyy	"Thu, Sep 29 1988"
//        E, dd MMM yyy HH:mm:ss 'GMT'
        return dtf.format(now);
    }

    @Test
    public void TC0903() {
        //    Kullanici "9781593277574" ISBN kodunu girdiginde cikan Response Headers' ta sirasiyla asagidakilerini dogrulayabilmelidir:
        //            1. "content-length" in ==>560  oldugunu,
        //            2. "content-type" in ==> "application/json; charset=utf-8" oldugunu,
        //            3. "date" in test yapilan gündeki, güncel tarih zaman dilimi oldugunu,
        //        4.  "server" ==> in  "nginx/1.17.10 (Ubuntu)" oldugunu
        Response response3 = getResponse(endPoint, isbnNumber);
        response3.then().assertThat().headers("content-length", Matchers.equalTo("560"),
                "content-type", Matchers.equalTo("application/json; charset=utf-8"),
                "server", Matchers.equalTo("nginx/1.17.10 (Ubuntu)"));
        System.out.println(response3.getHeader("date"));//Thu, 04 Feb 2021 21:05:13 GMT
        Assert.assertEquals(response3.getHeader("date").substring(0,16),returnTime());
    }
}


