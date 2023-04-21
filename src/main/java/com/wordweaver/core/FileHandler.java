package com.wordweaver.core;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

public class FileHandler {

    public static List<String> readTextFiles(String folderPath, BiConsumer<Integer, Integer> progressCallback) throws IOException {
        File folder = new File(folderPath);
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

        if (files == null) {
            throw new IOException("Invalid folder path: " + folderPath);
        }

        List<String> texts = new LinkedList<>();
        int totalWords = 0;
        int currentWords = 0;

        // Calculate the total number of words in all files
        for (File file : files) {
            if (file.isFile()) {
                String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
                totalWords += content.split("\\s+").length;
            }
        }

        for (File file : files) {
            if (file.isFile()) {
                String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
                texts.add(content);
                String[] words = content.split("\\s+");
                for (String word : words) {
                    currentWords++;

                    // Update progress
                    progressCallback.accept(currentWords, totalWords);
                }
            }
        }

        return texts;
    }

    private static boolean isTextFile(File file) {
        String fileName = file.getName();
        return fileName.toLowerCase().endsWith(".txt");
    }
}