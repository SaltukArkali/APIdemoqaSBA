package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class DemoqaSwaggerPage {

public DemoqaSwaggerPage(){
    PageFactory.initElements(Driver.getDriver(),this);
}

    @FindBy(xpath = "(//*[.='Account'])[2]")
    public WebElement accountLink;




}
