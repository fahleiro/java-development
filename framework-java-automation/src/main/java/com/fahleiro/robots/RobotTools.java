package com.fahleiro.robots;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class RobotTools {
    private AppiumDriver driver;
    public RobotTools(AppiumDriver driver) {

        this.driver = driver;
    }


    public void waitElement(WebElement element, int... waitTimeSeconds) {
        int maxTries = waitTimeSeconds.length > 0 ? waitTimeSeconds[0] / 3 : 3;
        int tryCount = 0;
        while (tryCount < maxTries) {
            try {
                if (element.isDisplayed()) {
                    return;
                }
            } catch (Exception e) {
                System.out.println("Elemento não disponível, tentando novamente...");
            }
            try {
                Thread.sleep(waitTimeSeconds.length > 0 ? waitTimeSeconds[0] * 1000 : 3000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            tryCount++;
        }
        throw new RuntimeException("Elemento não encontrado após " + maxTries + " tentativas.");
    }


    public void validateElement(WebElement element) {
        try {
            waitElement(element);
            if (!element.isDisplayed()) {
                System.out.println("Elemento não encontrado: " + element.toString());
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
        waitElement (element);
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
