package com.fahleiro.driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class AppiumSessionManager {

    private AppiumDriverLocalService appiumService;
    private AppiumDriver driver;
    private String appiumServerIp;
    private int appiumServerPort;
    private boolean detailedErrors;

    public AppiumSessionManager() {
        this.appiumServerIp = "127.0.0.1";
        this.appiumServerPort = 4723;
        this.detailedErrors = false;
    }

    public AppiumSessionManager(String appiumServerIp, int appiumServerPort, boolean detailedErrors) {
        this.appiumServerIp = appiumServerIp;
        this.appiumServerPort = appiumServerPort;
        this.detailedErrors = detailedErrors;
    }

    public void startSession(DesiredCapabilities capabilities) {
        stopSession ();

        AppiumServiceBuilder appiumServiceBuilderbuilder = new AppiumServiceBuilder()
                .usingPort(appiumServerPort)
                .withIPAddress(appiumServerIp);

        if (detailedErrors) {
            appiumServiceBuilderbuilder.withArgument(GeneralServerFlag.LOG_LEVEL, "debug");
        } else {
            appiumServiceBuilderbuilder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");
        }

        appiumService = AppiumDriverLocalService.buildService(appiumServiceBuilderbuilder);

        try {
            InetAddress localHost = InetAddress.getLocalHost();
            String localIpAddress = localHost.getHostAddress();

            if (appiumServerIp.equals(localIpAddress)) {
                System.out.println("IP informado local, iniciando servidor.");
                appiumService.start();
            } else {
                System.out.println("IP informado é diferente dos disponíveis local, iniciando sessão em appium server virtual.");
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        driver = new AppiumDriver(appiumService.getUrl(), capabilities);

        String finalAppiumUrl = appiumService.getUrl().toString();
        System.out.println("Sessão do Appium iniciada com sucesso em: " + finalAppiumUrl);
    }

    public void stopSession() {
        if (driver != null) {
            System.out.println("Closing existing driver.");
            driver.quit();
        }

        if (appiumService != null && appiumService.isRunning()) {
            System.out.println("Closing existing session.");
            appiumService.stop();
        }
    }

    public AppiumDriver getDriver() {
        return driver;
    }
}
