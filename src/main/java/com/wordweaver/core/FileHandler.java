package com.wordweaver.core;

import com.wordweaver.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class FileHandler {

    public static List<String> readTextFiles(String folderPath) throws IOException {
        List<String> allTexts = new LinkedList<>();
        File folder = new File(folderPath);
        Stack<File> stack = new Stack<>();

        if (folder.isDirectory()) {
            stack.push(folder);
        }

        while (!stack.isEmpty()) {
            File currentFile = stack.pop();

            if (currentFile.getName().equals("blacklist.txt")) {
                continue;
            }

            if (currentFile.isDirectory()) {
                for (File file : currentFile.listFiles()) {
                    stack.push(file);
                }
            } else if (isTextFile(currentFile)) {
                String content = FileUtils.readFileToString(currentFile.getPath());
                allTexts.add(content);
            }
        }

        return allTexts;
    }

    private static boolean isTextFile(File file) {
        String fileName = file.getName();
        return fileName.toLowerCase().endsWith(".txt");
    }
}