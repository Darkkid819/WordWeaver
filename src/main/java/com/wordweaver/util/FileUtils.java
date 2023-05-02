package com.wordweaver.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class FileUtils {

    public static String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        }
        return content.toString();
    }

    public static void writeFile(String content, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        }
    }

    public static void createDirectory(String directoryPath) throws IOException {
        Path directory = Paths.get(directoryPath);
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }
    }

    public static String generateUniqueFileName() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = today.format(dateFormatter);
        String randomId = UUID.randomUUID().toString().substring(0, 8);
        return formattedDate + "_" + randomId + ".txt";
    }

    public static boolean dataFolderExists(String dataFolder) throws IOException {
        Path folder = Paths.get(dataFolder);
        if (!Files.exists(folder)) {
            throw new IOException("Data folder path not found: " + dataFolder);
        }
        return true;
    }

    public static String getBlacklistFilePath(String dataFolder) {
        Path blacklistFilePath = Paths.get(dataFolder + "/blacklist.dat");
        if (!Files.exists(blacklistFilePath)) {
            System.err.println("Blacklist file (blacklist.dat) not found in the specified data folder: " + new File(dataFolder).getName());
            return null;
        }

        return blacklistFilePath.toString();
    }
}