package pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class Page {

    @AndroidFindBy(id = "element1")
    public  WebElement element1;
    @AndroidFindBy(id = "element2")
    public WebElement element2;

}
