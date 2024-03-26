package utils;

import org.openqa.selenium.remote.DesiredCapabilities;

public class Appium {

    public static final String appiumUrl = "http://localhost:4723";

    public static DesiredCapabilities appiumCaps() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("UDID", "");
        caps.setCapability("appPackage", "");
        caps.setCapability("appActivity", "");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("newCommandTimeout", "14400");
        caps.setCapability("noReset", true);



        return caps;
    }
}
