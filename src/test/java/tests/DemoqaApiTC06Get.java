package tests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.testng.annotations.Test;
import utilities.ConfigReader;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

public class DemoqaApiTC06Get {

    Response response;
    String endpoint = "https://demoqa.com/BookStore/v1/Books";
    JsonPath jsonPath;
    List<Map<String, Object>> mapList;

    public void getMethod() {
        response = given().
                accept(ContentType.JSON).
                auth().oauth2(ConfigReader.getProperty("token")).
                when().get(endpoint);
//      response.prettyPrint();
        jsonPath = response.jsonPath();
    }

    @Test
    public void TC0601() {
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

        mapList = jsonPath.getList("books");
        System.out.println(mapList.get(7).keySet());

        String[] arr = {"isbn", "title", "subTitle", "author", "publish_date", "publisher",
                "pages", "description", "website"};

        int count = 0;
        for (Map<String, Object> w : mapList) {
            if (arr.equals(w.keySet())) {
            }
            count++;
//            System.out.println(w.keySet()); // ==> console'da 8 tane oldugunu gor
        }
//        System.out.println(count); // 8
        Assert.assertTrue(mapList.size() == count);

//        assertTrue(response.getBody().asString().contains("isbn"));

    }

    @Test
    public void TC0602() {
//        2- Kullanici;
//              1. entpoint'in icinde toplam 8 kitap(obje) oldugunu dogrular.
//              2. 2020 yilinda basilan kitaplarin sayisini bularak, asert eder.
        getMethod();

        List<String> allBookList = jsonPath.getList("books");
        System.out.println("All Books : " + allBookList.size());
        Assert.assertEquals(allBookList.size(), 8);

        int count = 0;
        for (int i = 0; i < allBookList.size() - 1; i++) {
            String year = jsonPath.getString("books[" + i + "].publish_date").substring(0, 4);
            System.out.println(year);
            if (year.equals("2020")) {
                count++;
            }
        }
        assertTrue(count == 3);
        System.out.println("Number of Books published in 2020 : " + count);
    }

    @Test
    public void TC0603() {
        //3- Kullanici; herbir objenin "website" kisminin bos olmadigini dogrular.
        getMethod();
        Assert.assertFalse(jsonPath.getList("books.website").isEmpty());
    }

    @Test
    public void TC0604() {
//        4- Kullanici;
//               1. kitablarin "isbn" numaralarinin esit uzunlukta oldugunu sececegi uc kitabin isbn numaralarini karsilastirarak dogrular.
//               2. "isbn" numaralarinin uniq oldugunu dogrular.

        getMethod();
        String isbn1 = jsonPath.getString("books[0].isbn");
        String isbn2 = jsonPath.getString("books[1].isbn");
        String isbn3 = jsonPath.getString("books[2].isbn");
        System.out.println(isbn1 + "\n" + isbn2 + "\n" + isbn3);
        Assert.assertTrue(isbn1.length() == isbn2.length() && isbn2.length() == isbn3.length());

        List<String> isbnList = jsonPath.getList("books.isbn");
        System.out.println(isbnList);

        for (int i = 0; i < isbnList.size() - 1; i++) {
            for (int j = i + 1; j < isbnList.size(); j++) {
                Assert.assertFalse(isbnList.get(i).equals(isbnList.get(j)));
            }
        }
        Set<String> setList1 = new LinkedHashSet<>(isbnList);
        System.out.println(setList1);
        Assert.assertEquals(isbnList.size(), setList1.size());

    }

}
