package pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class Page {

    @AndroidFindBy(xpath = "//android.view.View/android.widget.EditText[1]")
    public WebElement usernameLabel;

    @AndroidFindBy(accessibility = "//android.view.View/android.widget.EditText[2]")
    public WebElement passwordLabel;

}
