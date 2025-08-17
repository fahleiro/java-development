package utils;

import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Appium {

    public static final int appiumPort = 4723;

    public static DesiredCapabilities appiumCaps() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("UDID", "emulator-5554");
        caps.setCapability("appPackage", "fahleiro.apk.tests");
        caps.setCapability("appActivity", "fahleiro.apk.tests.MainActivity");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("newCommandTimeout", "14400");
        caps.setCapability("noReset", true);

        return caps;
    }
}
