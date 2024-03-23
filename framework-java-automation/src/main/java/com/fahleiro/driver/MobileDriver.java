package com.fahleiro.driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import org.openqa.selenium.remote.DesiredCapabilities;

public class MobileDriver {

    public static AppiumDriver createAndroidDriver(String appiumServerUrl, DesiredCapabilities capabilities) {
        try {
            AndroidDriver driver = new AndroidDriver(new URL(appiumServerUrl), capabilities);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            System.out.println("Creating Appium Driver");
            return driver;
        } catch (MalformedURLException var3) {
            throw new RuntimeException("Error while creating Appium Driver", var3);
        }
    }

}
