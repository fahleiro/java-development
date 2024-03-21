package com.fahleiro.robots;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.support.ui.FluentWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.function.Function;

public class RobotTools {
    private AppiumDriver driver;
    public RobotTools(AppiumDriver driver) {

        this.driver = driver;
    }



    public void waitElement(WebElement element, int... customWaitTimes) {
        if (customWaitTimes.length > 0 && customWaitTimes.length != 2) {
            throw new IllegalArgumentException("If you provide custom wait times, you must provide both the polling interval and the total wait time.");
        }

        int pollingIntervalSeconds = 3;
        int timeoutSeconds = 30;

        if (customWaitTimes.length == 2) {
            pollingIntervalSeconds = customWaitTimes[0];
            timeoutSeconds = customWaitTimes[1];
        }

        FluentWait<AppiumDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutSeconds))
                .pollingEvery(Duration.ofSeconds(pollingIntervalSeconds));

        wait.until(new Function<AppiumDriver, Boolean>() {
            public Boolean apply(AppiumDriver driver) {
                try {
                    return element.isDisplayed();
                } catch (Exception e) {
                    System.out.println("The element " + element + " is not displayed, trying again.");
                    return false;
                }
            }
        });
    }



    public void validateElement(WebElement element) {
        try {
            waitElement(element);
            if (!element.isDisplayed()) {
                System.out.println("The element " + element + " is not displayed.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao validar elemento: " + e.getMessage());
        }
    }

    public void clickElement(WebElement element) {
        waitElement(element);
        element.click();
    }

    public void setText(WebElement element, String text) {
        waitElement(element);
        element.sendKeys(text);
    }

    public File takeScreenShotAsFile(WebElement element, String fileName, String directory) throws InterruptedException {
        waitElement(element);
        File screenshotDir = new File(directory);
        if (!screenshotDir.exists()) {
            screenshotDir.mkdirs();
        }
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenshotFilePath = directory + File.separator + fileName;
        try {
            FileUtils.copyFile(screenshotFile, new File(screenshotFilePath));
            return new File(screenshotFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void clear (WebElement element) {
        waitElement (element);
        element.clear ();
    }





}
