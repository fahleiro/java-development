package com.fahleiro.driver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.Duration;

public class MobileDriverUtils {

    private static AppiumDriverLocalService appiumService;
    public static AndroidDriver driver; // Alterado para AndroidDriver
    private static String redirectAppiumPortVar;

    private static boolean isPortInUse(String ip, int port) {
        try {
            Socket socket = new Socket(ip, port);
            socket.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static int findAvailablePort(String ip, int startingPort) {
        for (int port = startingPort; port <= 65535; port++) {
            if (!isPortInUse(ip, port)) {
                return port;
            }
        }
        return -1;
    }

    public static void startSession(int appiumServerPort, DesiredCapabilities capabilities, boolean detailedErrors, boolean redirectAppiumPort, String... appiumServerIp) {
        String ipAddress;
        if (appiumServerIp == null || appiumServerIp.length == 0 || appiumServerIp[0] == null) {
            System.out.println("AppiumServerIP not provided, starting local server");
            ipAddress = getLocalIpAddress();
        } else {
            ipAddress = appiumServerIp[0];
        }

        boolean portInUse = isPortInUse(ipAddress, appiumServerPort);
        if (redirectAppiumPort) {
            redirectAppiumPortVar = "Y";
        } else {
            redirectAppiumPortVar = "N";
        }
        if (portInUse) {
            System.out.println("The port " + appiumServerPort + " is in use or inaccessible.");
            System.out.println("Port redirection active? " + redirectAppiumPortVar);
            if (redirectAppiumPort) {
                int newPort = findAvailablePort(ipAddress, appiumServerPort);
                if (newPort != -1) {
                    System.out.println("Redirecting to alternate port: " + newPort);
                    appiumServerPort = newPort;
                } else {
                    System.out.println("Unable to find an available alternate port. Closing the session");
                    return;
                }
            }
        } else {
            System.out.println("The port " + appiumServerPort + " is available for use.");
        }

        AppiumServiceBuilder appiumServiceBuilder = new AppiumServiceBuilder()
                .usingPort(appiumServerPort)
                .withIPAddress(ipAddress);

        if (detailedErrors) {
            appiumServiceBuilder.withArgument(GeneralServerFlag.LOG_LEVEL, "debug");
        } else {
            appiumServiceBuilder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");
        }

        appiumService = AppiumDriverLocalService.buildService(appiumServiceBuilder);

        try {
            InetAddress localHost = InetAddress.getLocalHost();
            String localIpAddress = localHost.getHostAddress();

            if (ipAddress.equals(localIpAddress)) {
                System.out.println("Local IP address provided, starting server");
                appiumService.start();
            } else {
                System.out.println("Provided IP address is different from available local addresses, starting session on remote appium server");
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        driver = new AndroidDriver(appiumService.getUrl(), capabilities); // Alterado para AndroidDriver
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        String finalAppiumUrl = appiumService.getUrl().toString();
        System.out.println("Appium session started successfully at: " + finalAppiumUrl);
    }

    private static String getLocalIpAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void stopSession() {
        if (driver != null) {
            System.out.println("Closing existing driver.");
            driver.quit();
        }

        if (appiumService != null && appiumService.isRunning()) {
            System.out.println("Closing existing session.");
            appiumService.stop();
        }
    }
}
