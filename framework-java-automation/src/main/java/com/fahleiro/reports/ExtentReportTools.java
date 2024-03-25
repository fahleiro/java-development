package com.fahleiro.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;

public class ExtentReportTools {

    private static ExtentReports extent;

    public static ExtentReports createExtentReport(String filename, String directory) {
        if (extent == null) {
            File reportDir = new File(directory);
            if (!reportDir.exists()) {
                reportDir.mkdirs();
            }

            String filePath = directory + File.separator + filename;
            ExtentSparkReporter spark = new ExtentSparkReporter(filePath);
            extent = new ExtentReports();
            extent.attachReporter(spark);
        }
        return extent;
    }

    public static void flushExtentReport() {
        if (extent != null) {
            extent.flush();
        }
    }

    public static ExtentReports getExtentReport() {
        return extent;
    }

    public static ExtentTest createTest(String name, String description) {
        ExtentTest test = extent.createTest(name, description);
        return test;
    }

    public static void logTest(ExtentTest test, String message, String... fileName) {
        if (fileName.length > 0 && fileName[0] != null && !fileName[0].trim().isEmpty()) {
            String screenshotPath = "screenshots/" + fileName[0];
            test.pass(message, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } else {
            test.pass(message);
        }
    }

}