package interactions;

import DAO.Queries.Query;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.Alert;
import pages.Page;
import org.openqa.selenium.support.PageFactory;
import java.sql.SQLException;
import org.openqa.selenium.WebElement;
import java.util.List;

import static tests.Hooks.driver;
import static utils.BuildTools.robots;

public class Interactions extends Page {

    public Interactions() {

        PageFactory.initElements(new AppiumFieldDecorator (driver), this);
    }

    public void setUserInteraction(String user) throws InterruptedException {
        robots.setText(usernameLabel, user);
        Thread.sleep (10000);
    }
    
    public void setPasswordInteraction(String pass) throws InterruptedException {
        robots.setText(passwordLabel, pass);
    }

    public void btLoginClick(){
        robots.clickElement(loginButton);
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void consultarPedidos() throws SQLException, ClassNotFoundException {
        Query.queryMethod();
    }

}
