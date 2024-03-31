package com.fahleiro.driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import org.openqa.selenium.remote.DesiredCapabilities;

public class MobileDriver {

    public static AppiumDriver startAppium(String appiumServerIp, int appiumServerPort, DesiredCapabilities capabilities, boolean detailedErrors, boolean redirectAppiumPort) {
        System.out.println("Starting Appium");
        MobileDriverUtils.startSession(appiumServerIp, appiumServerPort, capabilities, detailedErrors, redirectAppiumPort);
        return MobileDriverUtils.driver;
    }

    public static void stopAppium(){
        MobileDriverUtils.stopSession();
    }

}
