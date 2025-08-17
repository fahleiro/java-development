package tests;

import com.aventstack.extentreports.ExtentTest;

import static com.fahleiro.reports.ExtentReportTools.logTest;
import static utils.BuildTools.extent2;
import interactions.Interactions;


public class TestApp {

    public static void TestMethod1() {

        Interactions interactions = new Interactions ();

        /* Test 1 */
        ExtentTest test1 = extent2.createTest ("Realizar login com usuario valido", "Deve realizar login com sucesso")
        .assignAuthor ("Author")
        .assignCategory ("Category");
        try {
            interactions
                .setUserInteraction ("usuario1","setusuario1.png","src/test/report/screenshots");
                logTest (test1, "Set usuario 1", "setusuario1.png");
            interactions
                    .setPasswordInteraction ("senha1","setsenha1.png", "src/test/report/screenshots");
            logTest (test1, "Set senha 1", "setsenha1.png");
            interactions.btLoginClick("btLoginClick.png", "src/test/report/screenshots");
            logTest (test1, "Clico no botao de login", "btLoginClick.png");


            test1.pass ("Test 1 passed!");
        } catch (Exception e) {
            test1.fail( "Test 1 failed. Error: " + e.getMessage ());
        }
    }







}