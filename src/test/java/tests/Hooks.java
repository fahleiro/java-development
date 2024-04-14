package tests;

import com.fahleiro.driver.MobileDriverTools;
import com.fahleiro.robots.RobotTools;
import io.appium.java_client.AppiumDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import static utils.Appium.*;
import static utils.BuildTools.extent2;

public class Hooks {

    public static AppiumDriver driver;

    @BeforeSuite
    public static void before() {
        driver = MobileDriverTools.startAppium(null, appiumPort, appiumCaps (), true, false );
        String directoryExtentCriado = "src/test/java/report/";
        //extent2.createExtentReport("Report.html", directoryExtentCriado);
        System.out.println ("log to registry start of execution @BeforeSuite");
    }

    @AfterSuite
    public static void after() {
        MobileDriverTools.stopAppium();

        //extent2.flushExtentReport();

        //RobotTools.createZip("src/test/java", "report", "src/test/resources/zip");

        String[] recipients = {"", ""};
        String subject = "";
        String attachmentPath = "src/test/resources/zip/report.zip";
        //RobotTools.sendMail("", "", "", 587, recipients, subject, "src/test/resources/mail/Mail.html", attachmentPath);
    }
}
