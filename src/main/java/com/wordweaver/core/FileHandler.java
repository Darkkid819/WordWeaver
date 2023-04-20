package com.wordweaver.core;

import com.wordweaver.model.MainLinkedList;
import com.wordweaver.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class FileHandler {

    public static MainLinkedList processInputFile(String inputFilePath, String[] blacklist) throws IOException {
        String inputText = FileUtils.readFileToString(inputFilePath);
        String[] words = TextProcessor.tokenize(inputText);

        if (blacklist != null && blacklist.length > 0) {
            words = TextProcessor.filterWords(words, blacklist);
        }

        MainLinkedList mainLinkedList = new MainLinkedList();

        for (int i = 0; i < words.length - 1; i++) {
            String keyword = words[i];
            String followingWord = words[i + 1];

            mainLinkedList.addMainLink(keyword).getBabyList().addBabyLink(followingWord);
        }

        return mainLinkedList;
    }

    public static List<String> readAllTextFilesInFolder(String folderPath) throws IOException {
        List<String> allTexts = new LinkedList<>();
        File folder = new File(folderPath);
        Stack<File> stack = new Stack<>();

        if (folder.isDirectory()) {
            stack.push(folder);
        }

        while (!stack.isEmpty()) {
            File currentFile = stack.pop();

            if (currentFile.isDirectory()) {
                for (File file : currentFile.listFiles()) {
                    stack.push(file);
                }
            } else if (isTextFile(currentFile)) {
                String content = Files.readString(currentFile.toPath());
                allTexts.add(content);
            }
        }

        return allTexts;
    }

    public static void writeGeneratedTextToFile(String outputFilePath, String generatedText) throws IOException {
        FileUtils.writeStringToFile(generatedText, outputFilePath);
    }

    private static boolean isTextFile(File file) {
        String fileName = file.getName();
        return fileName.toLowerCase().endsWith(".txt");
    }
}