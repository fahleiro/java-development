package com.fahleiro.robots;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.apache.commons.io.FileUtils;
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

    public void createZip(String caminhoDoFolder, String nomeDoFolder, String... caminhoPersonalizado) {
        if (caminhoDoFolder == null || caminhoDoFolder.isEmpty() ||
                nomeDoFolder == null || nomeDoFolder.isEmpty()) {
            System.out.println("The name and path of folder must be provided");
            return;
        }

        try {
            File destino = (caminhoPersonalizado.length > 0 && caminhoPersonalizado[0] != null && !caminhoPersonalizado[0].isEmpty()) ?
                    new File(caminhoPersonalizado[0]) : new File("src/test/zip");

            if (!destino.exists()) {
                destino.mkdirs();
            }

            File folder = new File(caminhoDoFolder, nomeDoFolder);
            if (!folder.exists()) {
                System.out.println("The especified folder does not exist");
                return;
            }

            FileOutputStream fos = new FileOutputStream(destino.getPath() + File.separator + nomeDoFolder + ".zip");
            ZipOutputStream zos = new ZipOutputStream(fos);
            RobotUtils.zipFolder(folder, folder.getName(), zos);
            zos.close();
            fos.close();

            System.out.println("Folder zipped successfully. Zipped file save at: " + destino.getPath() + File.separator + nomeDoFolder + ".zip");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMail(String host, String mail, String password, int port, String[] recipients,
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
