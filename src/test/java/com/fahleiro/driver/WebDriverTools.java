package com.fahleiro.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverTools {

    private static WebDriver driver;

    public static WebDriver startChromeDriver() {
        System.out.println("Starting new WebDriver session");

        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();

        return driver;
    }

    public static void stopWebDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
