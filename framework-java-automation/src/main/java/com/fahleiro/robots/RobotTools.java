package com.fahleiro.robots;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Duration;
import java.util.Properties;
import java.util.function.Function;
import java.util.zip.ZipOutputStream;

public class RobotTools {

    private WebDriver driver;
    public RobotTools(WebDriver driver) {
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

        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutSeconds))
                .pollingEvery(Duration.ofSeconds(pollingIntervalSeconds));

        wait.until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver driver) {
                try {
                    return element.isDisplayed();
                } catch (Exception e) {
                    System.out.println("The element " + element + " is not displayed, trying again.");
                    return false;
                }
            }
        });
    }

    public void validateElement(WebElement element) throws Exception {
        try {
            waitElement(element);
            if (!element.isDisplayed()) {
                System.out.println("The element " + element + " is not displayed.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao validar elemento: " + e.getMessage());
            throw e;
        }
    }

    public boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            System.out.println("Erro ao verificar se o elemento está visível: " + e.getMessage());
            return false;
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

    public File takeScreenShot(String fileName, String directory) throws InterruptedException {
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

    public File takeScreenShot(String fileName, String directory, WebElement element) throws InterruptedException {
        waitElement(element);
        return takeScreenShot(fileName, directory);
    }

    public void clear (WebElement element) {
        waitElement (element);
        element.clear ();
    }

    public static void createZip(String folderPathToZip, String folderToZip, String... savePathZip) {
        if (folderPathToZip == null || folderPathToZip.isEmpty() ||
                folderToZip == null || folderToZip.isEmpty()) {
            System.out.println("The name and path of folder must be provided");
            return;
        }

        try {
            File destiny = (savePathZip.length > 0 && savePathZip[0] != null && !savePathZip[0].isEmpty()) ?
                    new File(savePathZip[0]) : new File("src/test/zip");

            if (!destiny.exists()) {
                destiny.mkdirs();
            }

            File folder = new File(folderPathToZip, folderToZip);
            if (!folder.exists()) {
                System.out.println("The especified folder does not exist");
                return;
            }

            FileOutputStream fos = new FileOutputStream(destiny.getPath() + File.separator + folderToZip + ".zip");
            ZipOutputStream zos = new ZipOutputStream(fos);
            RobotUtils.zipFolder(folder, folder.getName(), zos);
            zos.close();
            fos.close();

            System.out.println("Folder zipped successfully. Zipped file save at: " + destiny.getPath() + File.separator + folderToZip + ".zip");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendMail(String host, String mail, String password, int port, String[] recipients,
                                String subject, String mailBodyPath, String attachmentPath) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.ssl.trust", host);

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mail, password);
            }
        });

        try {
            Message message = new MimeMessage (session);
            message.setFrom(new InternetAddress (mail));

            for (String recipient : recipients) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            }

            message.setSubject(subject);

            MimeMultipart multipart = new MimeMultipart ();

            File bodyMail = new File(mailBodyPath);
            if (bodyMail.exists()) {
                MimeBodyPart messageBodyPart = new MimeBodyPart();
                String bodyContent = new String(Files.readAllBytes(bodyMail.toPath()), StandardCharsets.UTF_8);
                messageBodyPart.setContent(bodyContent, "text/html; charset=utf-8");
                multipart.addBodyPart(messageBodyPart);
            } else {
                System.out.println("Body File not found");
            }

            File attachFile = new File(attachmentPath);
            if (attachFile.exists()) {
                MimeBodyPart attachmentPart = new MimeBodyPart();
                attachmentPart.attachFile(attachFile);
                multipart.addBodyPart(attachmentPart);
            } else {
                System.out.println("Attachment File not found");
            }

            message.setContent(multipart);

            if (multipart.getCount() == 0) {
                System.out.println("Body mail content must be provided");
            } else {
                Transport.send(message);
                System.out.println("Email sent successfully");
            }
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }

}
