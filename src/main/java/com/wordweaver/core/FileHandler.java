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
        int totalWords = getTotalWords(files);
        int currentWords = 0;

        for (File file : files) {
            if (file.isFile()) {
                String content = Files.readString(file.toPath(), StandardCharsets.UTF_8);
                texts.add(content);
                String[] words = content.split("\\s+");
                for (int i = 0; i < words.length; i++) {
                    currentWords++;

                    // Update progress
                    progressCallback.accept(currentWords, totalWords);
                }
            }
        }

        return texts;
    }

    private static int getTotalWords(File[] files) throws IOException {
        int totalWords = 0;

        for (File file : files) {
            if (file.isFile()) {
                String content = Files.readString(file.toPath(), StandardCharsets.UTF_8);
                totalWords += content.split("\\s+").length;
            }
        }

        return totalWords;
    }
}