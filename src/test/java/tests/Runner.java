package tests;

import org.testng.annotations.Test;

public class Runner extends Hooks {

    @Test(groups = "group1")
    public void runnerMethod1() {
        TestApp.TestMethod1 ();

    }

    /*
    @Test(groups = "group2")
    public void runnerMethod2() {
    }
    */

}
