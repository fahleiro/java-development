package utils;

import com.fahleiro.reports.ExtentReportTools;
import com.fahleiro.robots.RobotTools;
import static tests.Hooks.driver;

public class BuildTools {
    public static RobotTools robots;
    public static ExtentReportTools extent2;

    static {
        robots = new RobotTools(driver);
    }

    static {
        extent2 = new ExtentReportTools ();
    }


}
