package tests;

import com.aventstack.extentreports.ExtentTest;

import static com.fahleiro.reports.ExtentReportTools.logTest;
import static utils.BuildTools.extent2;
import interactions.Interactions;


public class TestApp {





    public static void TestMethod1() {

        Interactions interactions = new Interactions ();

        /* Test 1 */
        ExtentTest test1 = extent2.createTest ("", "")
        .assignAuthor ("")
        .assignCategory ("");
        try {
            interactions
                .methodInteraction ("", "", "");
                logTest (test1, "", "");

            test1.pass ("Test 1 passed!");
        } catch (Exception e) {
            test1.fail( "Test 1 failed. Error: " + e.getMessage ());
        }



        /* Test 2 */
        ExtentTest teste2 = extent2.createTest ("", "");
        teste2.assignAuthor ("");
        teste2.assignCategory ("");
        try {
            interactions.methodInteraction ("", "", "");
            logTest (teste2, "", "");

            teste2.pass ("Test 2 passed!");
        } catch (Exception e) {
            teste2.fail ("Test 2 failed. Error: " + e.getMessage ());
        }
    }







}