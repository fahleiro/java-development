package com.fahleiro.driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.appium.java_client.screenrecording.CanRecordScreen;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class MobileDriverTools {

    public static AndroidDriver startAppium(String appiumServerIp, int appiumServerPort, DesiredCapabilities capabilities, boolean detailedErrors, boolean redirectAppiumPort) {
        System.out.println("Starting Appium");
        MobileDriverUtils.startSession(appiumServerPort, capabilities, detailedErrors, redirectAppiumPort, appiumServerIp);
        return (AndroidDriver) MobileDriverUtils.driver;
    }

    public void startScreenRecording(AndroidDriver driver){
        driver.startRecordingScreen(new AndroidStartScreenRecordingOptions()
                .withVideoSize("1280x720")
                .withTimeLimit(Duration.ofSeconds(1000)));
    }

    public String stopScreenRecording(AndroidDriver driver){
        return driver.stopRecordingScreen();
    }

    public static void stopAppium(){
        MobileDriverUtils.stopSession();
    }
}
