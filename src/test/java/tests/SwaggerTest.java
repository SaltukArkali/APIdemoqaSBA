package tests;

import com.sun.javafx.css.StyleCache;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;
import pages.DemoqaSwaggerPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;

public class SwaggerTest {

    DemoqaSwaggerPage swaggerPage = new DemoqaSwaggerPage();
    Actions actions=new Actions(Driver.getDriver());

    @Test
    public void swaggerTest(){
        Driver.getDriver().get(ConfigReader.getProperty("swagger"));
        swaggerPage.accountLink.click();
        ReusableMethods.waitFor(2);
        actions.sendKeys(Keys.PAGE_DOWN).perform();
    }



}
