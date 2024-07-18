package tests;

import com.fahleiro.driver.MobileDriverTools;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import static utils.Appium.*;
import static utils.BuildTools.extent2;

public class Hooks {

    public static AndroidDriver driver;
    private static MobileDriverTools driverTools = new MobileDriverTools();

    @BeforeSuite
    public static void before() {
        driver = MobileDriverTools.startAppium("192.168.0.18", appiumPort, appiumCaps(), true, false);
        String directoryExtentCriado = "src/test/report/";
        extent2.createExtentReport("Report.html", directoryExtentCriado);
        System.out.println("Log to registry start of execution @BeforeSuite");


    }

@BeforeTest
public static void beforeTest(){

    // Iniciar a gravação de tela
    driverTools.startScreenRecording(driver);

}

    @AfterTest
    public static void afterTest() {
        // Gerar o nome do vídeo com base no horário atual
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH-mm-ss");
        String videoName = LocalTime.now().format(formatter) + ".mp4";
        String pathToSaveVideo = "src/test/report/evidence";

        // Parar a gravação de tela e salvar o vídeo
        String videoPath = driverTools.stopScreenRecording(driver, videoName, pathToSaveVideo);
        if (videoPath != null) {
            System.out.println("Video saved at: " + videoPath);
        } else {
            System.out.println("No video was recorded.");
        }
    }

    @AfterSuite
    public static void after() {


        MobileDriverTools.stopAppium();
        extent2.flushExtentReport();

        // Descomentar se necessário
        // RobotTools.createZip("src/test/java", "report", "src/test/resources/zip");

        String[] recipients = {"", ""};
        String subject = "";
        String attachmentPath = "src/test/resources/zip/report.zip";
        // RobotTools.sendMail("", "", "", 587, recipients, subject, "src/test/resources/mail/Mail.html", attachmentPath);
    }
}
