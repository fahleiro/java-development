package com.fahleiro.robots;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class RobotUtils {
    public static void zipFolder(File folder, String folderName, ZipOutputStream zos) throws IOException {
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                zipFolder(file, folderName + File.separator + file.getName(), zos);
                continue;
            }
            ZipEntry ze = new ZipEntry(folderName + File.separator + file.getName());
            zos.putNextEntry(ze);

            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, length);
            }
            zos.closeEntry();
            fis.close();
        }
    }

    public static void attachSingleFile(File file, MimeMultipart multipart) throws MessagingException, IOException {
        MimeBodyPart attachmentPart = new MimeBodyPart();
        attachmentPart.attachFile(file);
        multipart.addBodyPart(attachmentPart);
    }

    public static void attachFilesInDirectory(File directory, MimeMultipart multipart) throws MessagingException, IOException {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    attachFilesInDirectory(file, multipart);
                } else {
                    attachSingleFile(file, multipart);
                }
            }
        }
    }


}
