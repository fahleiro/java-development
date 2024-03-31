package com.fahleiro.driver;

import io.appium.java_client.AppiumDriver;
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
    public static AppiumDriver driver;
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

    public static void startSession(String appiumServerIp, int appiumServerPort, DesiredCapabilities capabilities, boolean detailedErrors, boolean redirectAppiumPort) {
        boolean portInUse = isPortInUse(appiumServerIp, appiumServerPort);
        if (redirectAppiumPort){
            redirectAppiumPortVar = "Y";
        } else {
            redirectAppiumPortVar = "N";
        }
        if (portInUse) {
            System.out.println("A porta " + appiumServerPort + " está em uso ou inacessível.");
            System.out.println("Redirecionamento de porta ativo? " + redirectAppiumPortVar);
            if (redirectAppiumPort) {
                int newPort = findAvailablePort(appiumServerIp, appiumServerPort);
                if (newPort != -1) {
                    System.out.println("Redirecionando para porta alternativa: " + newPort);
                    appiumServerPort = newPort;
                } else {
                    System.out.println("Não foi possível encontrar uma porta alternativa disponível. Encerrando a sessão.");
                    return;
                }
            }
        } else {
            System.out.println("A porta " + appiumServerPort + " está disponível para uso.");
        }


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
                System.out.println("IP informado é diferente dos disponíveis local, iniciando sessão em appium server remote.");
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        driver = new AppiumDriver(appiumService.getUrl(), capabilities);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));


        String finalAppiumUrl = appiumService.getUrl().toString();
        System.out.println("Sessão do Appium iniciada com sucesso em: " + finalAppiumUrl);
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
