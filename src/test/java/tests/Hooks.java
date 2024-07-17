package tests;

import com.fahleiro.driver.MobileDriverTools;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.FileOutputStream;
import java.io.IOException;
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

        // Iniciar a gravação de tela
        driverTools.startScreenRecording(driver);

    }

    @AfterSuite
    public static void after() {
        // Parar a gravação de tela e salvar o vídeo
        String videoBase64 = driverTools.stopScreenRecording(driver);
        if (videoBase64 != null && !videoBase64.isEmpty()) {
            byte[] videoBytes = Base64.getDecoder().decode(videoBase64);
            try (FileOutputStream fos = new FileOutputStream("src/test/report/screenRecording.mp4")) {
                fos.write(videoBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
