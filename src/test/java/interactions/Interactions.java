package interactions;

import DAO.Queries.Query;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import pages.Page;
import org.openqa.selenium.support.PageFactory;
import java.sql.SQLException;

import static tests.Hooks.driver;
import static utils.BuildTools.robots;

public class Interactions extends Page {

    public Interactions() {

        PageFactory.initElements(new AppiumFieldDecorator (driver), this);
    }

    public void methodInteraction(String user, String failenamess, String pathss) throws InterruptedException {
        robots.setText(usernameLabel, user);
        robots.takeScreenShot (failenamess, pathss );
    }

    public void consultarPedidos() throws SQLException, ClassNotFoundException {
        Query.queryMethod();
    }

}