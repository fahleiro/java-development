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
    public static void zipFolder(File folder, String nomeDoFolder, ZipOutputStream zos) throws IOException {
        // Lista todos os arquivos e pastas dentro do diretório
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                // Se for uma pasta, chama recursivamente o método para zipar a pasta
                zipFolder(file, nomeDoFolder + File.separator + file.getName(), zos);
                continue;
            }
            // Se for um arquivo, cria uma entrada zip para o arquivo
            ZipEntry ze = new ZipEntry(nomeDoFolder + File.separator + file.getName());
            zos.putNextEntry(ze);

            // Escreve o conteúdo do arquivo no arquivo zip
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


    public static void attachFilesInDirectory(File directory, MimeMultipart multipart) throws MessagingException, IOException {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // Se for um diretório, chamar recursivamente esta função
                    attachFilesInDirectory(file, multipart);
                } else {
                    // Se for um arquivo, anexá-lo ao email
                    attachSingleFile(file, multipart);
                }
            }
        }
    }

    public static void attachSingleFile(File file, MimeMultipart multipart) throws MessagingException, IOException {
        MimeBodyPart attachmentPart = new MimeBodyPart();
        attachmentPart.attachFile(file);
        multipart.addBodyPart(attachmentPart);
    }
}
