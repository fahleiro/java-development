package com.fahleiro.driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import org.openqa.selenium.remote.DesiredCapabilities;

public class MobileDriver {
    public MobileDriver() {
    }

    public static AppiumDriver createAndroidDriver(String appiumServerUrl, DesiredCapabilities capabilities) {
        try {
            AndroidDriver driver = new AndroidDriver(new URL(appiumServerUrl), capabilities);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30)); // Adicionar o tempo de espera
            System.out.println("Framework de automação mobile desenvolvido por Gabriel Reinisch Faleiro");
            return driver;
        } catch (MalformedURLException var3) {
            throw new RuntimeException("Erro ao criar o driver do Appium: URL inválida.", var3);
        }
    }
}
