package pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class Page {

    @AndroidFindBy(xpath = "//android.view.View/android.widget.EditText[1]")
    public WebElement usernameLabel;

    @AndroidFindBy(xpath = "//android.view.View/android.widget.EditText[2]")
    public WebElement passwordLabel;

    @AndroidFindBy(xpath = "//android.widget.Button[@text='Login']")
    public WebElement loginButton;

}
