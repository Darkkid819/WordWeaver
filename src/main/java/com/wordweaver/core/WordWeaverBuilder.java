package com.wordweaver.core;

import com.wordweaver.model.MainLinkedList;
import com.wordweaver.util.BlacklistUtils;
import com.wordweaver.util.ConsoleUtils;
import com.wordweaver.util.FileUtils;

import java.io.IOException;
import java.util.List;
import java.util.function.BiConsumer;

public class WordWeaverBuilder {

    public static MainLinkedList build(String dataFolder, String outputFolder, String outputFileName, String blacklist) throws IOException {
        String blacklistFilePath = dataFolder + "/" + blacklist;
        BlacklistUtils blacklistUtils = new BlacklistUtils(blacklistFilePath);

        List<String> texts = readTextFiles(dataFolder);
        String[] words = processText(texts, blacklistUtils);
        MainLinkedList mainLinkedList = trainModel(words);

        generateOutput(mainLinkedList, outputFolder, outputFileName);
        return mainLinkedList;
    }

    private static List<String> readTextFiles(String dataFolder) throws IOException {
        List<String> texts = FileHandler.readTextFiles(dataFolder, (currentFile, totalFiles) ->
                ConsoleUtils.printLoadingBar("Reading text", currentFile, totalFiles));
        System.out.println();
        return texts;
    }

    private static String[] processText(List<String> texts, BlacklistUtils blacklistUtils) {
        String[] filteredText = TextProcessor.processText(texts, null, blacklistUtils, (currentWord, totalWords) ->
                ConsoleUtils.printLoadingBar("Processing text", currentWord, totalWords));
        System.out.println();
        return filteredText;
    }

    private static MainLinkedList trainModel(String[] words) {
        BiConsumer<Integer, Integer> progressCallback = (currentWord, totalWords) ->
                ConsoleUtils.printLoadingBar("Training model", currentWord, totalWords);

        MainLinkedList mainLinkedList = new MainLinkedList();
        for (int i = 0; i < words.length - 1; i++) {
            String keyword = words[i];
            String followingWord = words[i + 1];

            mainLinkedList.addMainLink(keyword).getBabyList().addBabyLink(followingWord);

            // Update progress
            progressCallback.accept(i + 1, words.length - 1);
        }
        System.out.println();

        return mainLinkedList;
    }

    private static void generateOutput(MainLinkedList mainLinkedList, String outputFolder, String outputFileName) throws IOException {
        TextGenerator textGenerator = new TextGenerator(mainLinkedList);
        String startingWord = "The"; // temp
        int wordCount = 100; // temp
        String generatedParagraph = textGenerator.generateParagraph(startingWord, wordCount);

        String outputFilePath = outputFolder + "/" + outputFileName;
        FileUtils.writeFile(generatedParagraph, outputFilePath);
    }
}
