package tests;

import com.aventstack.extentreports.ExtentTest;
import static utils.BuildTools.extent2;
import interactions.Interactions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.fahleiro.driver.MobileDriverTools;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TestApp extends Hooks {

    private MobileDriverTools driverTools = new MobileDriverTools();

    @BeforeMethod
    public void beforeMethod() {
        // Iniciar a gravação de tela
        driverTools.startScreenRecording(driver);
    }

    @AfterMethod
    public void afterMethod() {
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

    @Test
    public void TestMethod1() {
        Interactions interactions = new Interactions();

        /* Test 1 */
        ExtentTest test1 = extent2.createTest("Realizar login com usuario valido", "Deve realizar login com sucesso")
                .assignAuthor("Author")
                .assignCategory("Category");
        try {
            interactions.setUserInteraction("usuario1");
            interactions.setPasswordInteraction("senha1");
            test1.pass("Test 1 passed!");
        } catch (Exception e) {
            test1.fail("Test 1 failed. Error: " + e.getMessage());
        }
    }

    @Test
    public void TestMethod2() {
        Interactions interactions = new Interactions();

        /* Test 2 */
        ExtentTest test2 = extent2.createTest("Realizar login com usuario invalido", "NÃO deve realizar login com sucesso")
                .assignAuthor("Author")
                .assignCategory("Category");
        try {
            interactions.setUserInteraction("123213213213");
            interactions.setPasswordInteraction("se123213213213213213213213213213213123nha1");
            test2.pass("Test 2 passed!");
        } catch (Exception e) {
            test2.fail("Test 2 failed. Error: " + e.getMessage());
        }
    }
}
