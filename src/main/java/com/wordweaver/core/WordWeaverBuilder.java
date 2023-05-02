package com.wordweaver.core;

import com.wordweaver.model.MainLinkedList;
import com.wordweaver.util.BlacklistUtils;
import com.wordweaver.util.ConsoleUtils;
import com.wordweaver.util.FileUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

public class WordWeaverBuilder {

    public static MainLinkedList build(String dataFolder) throws Exception {
        if (!FileUtils.dataFolderExists(dataFolder)) {
            System.exit(0);
        }

        String blacklistFilePath = FileUtils.getBlacklistFilePath(dataFolder).orElse(null);
        BlacklistUtils blacklistUtils = new BlacklistUtils(blacklistFilePath);
        List<String> texts = readTextFiles(dataFolder);
        String[] words = processText(texts, blacklistUtils);
        MainLinkedList mainLinkedList = trainModel(words);

        return mainLinkedList;
    }

    private static List<String> readTextFiles(String dataFolder) throws IOException {
        List<String> texts = FileHandler.readTextFiles(dataFolder, (currentFile, totalFiles) ->
                ConsoleUtils.printLoadingBar("Reading text", currentFile, totalFiles));
        System.out.println();
        return texts;
    }

    private static String[] processText(List<String> texts, BlacklistUtils blacklistUtils) {
        return TextProcessor.processText(texts, blacklistUtils);
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

    // adds text to model without command line args
    public static void addTextToModel(MainLinkedList mainLinkedList, String textFilePath, BlacklistUtils blacklistUtils) throws IOException {
        String text = FileUtils.readFile(textFilePath);
        String[] words = TextProcessor.processText(Collections.singletonList(text), blacklistUtils);

        for (int i = 0; i < words.length - 1; i++) {
            String keyword = words[i];
            String followingWord = words[i + 1];

            mainLinkedList.addMainLink(keyword).getBabyList().addBabyLink(followingWord);
        }
    }

    // no command line args
    public static MainLinkedList build(){
        return new MainLinkedList();
    }
}
