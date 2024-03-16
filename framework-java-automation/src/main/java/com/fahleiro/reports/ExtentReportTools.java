package com.fahleiro.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;

public class ExtentReportTools {

    public static ExtentReports createExtentReports(String filename, String directory) {
        File reportDir = new File (directory);
        if (!reportDir.exists ()) {
            reportDir.mkdirs ();
        }

        String filePath = directory + File.separator + filename;
        ExtentSparkReporter spark = new ExtentSparkReporter(filePath);
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(spark);
        return extent;
    }

    public static void logWithScreenshot(ExtentTest test, String message, String fileName) {
        String screenshotPath = "screenshots/" + fileName;
        test.pass(message, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
    }


}