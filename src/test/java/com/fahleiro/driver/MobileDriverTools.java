package com.fahleiro.driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.appium.java_client.screenrecording.CanRecordScreen;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Base64;

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

    public String stopScreenRecording(AndroidDriver driver, String videoName, String pathToSaveVideo) {
        String videoBase64 = driver.stopRecordingScreen();
        if (videoBase64 != null && !videoBase64.isEmpty()) {
            byte[] videoBytes = Base64.getDecoder().decode(videoBase64);
            String fullPath = pathToSaveVideo + "/" + videoName;

            // Verificar se o diretório existe, e criar se não existir
            File directory = new File (pathToSaveVideo);
            if (!directory.exists()) {
                directory.mkdirs(); // Criar diretórios necessários
            }

            try (FileOutputStream fos = new FileOutputStream(fullPath)) {
                fos.write(videoBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return fullPath;
        }
        return null;
    }

    public static void stopAppium(){
        MobileDriverUtils.stopSession();
    }
}
