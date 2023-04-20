package com.wordweaver.core;

import com.wordweaver.model.MainLinkedList;
import com.wordweaver.util.BlacklistUtils;
import com.wordweaver.util.FileUtils;

import java.io.IOException;
import java.util.List;

public class WordWeaverBuilder {

    public static void build(String dataFolder, String outputFolder, String outputFileName, String blacklist) throws IOException {
        String blacklistFilePath = dataFolder + "/" + blacklist;
        BlacklistUtils blacklistUtils = new BlacklistUtils(blacklistFilePath);

        List<String> texts = FileHandler.readTextFiles(dataFolder);
        String[] words = TextProcessor.processText(texts, null, blacklistUtils);

        MainLinkedList mainLinkedList = new MainLinkedList();
        for (int i = 0; i < words.length - 1; i++) {
            String keyword = words[i];
            String followingWord = words[i + 1];

            mainLinkedList.addMainLink(keyword).getBabyList().addBabyLink(followingWord);
        }

        TextGenerator textGenerator = new TextGenerator(mainLinkedList);
        String startingWord = "The";
        int wordCount = 100;
        String generatedParagraph = textGenerator.generateParagraph(startingWord, wordCount);

        String outputFilePath = outputFolder + "/" + outputFileName;
        FileUtils.writeStringToFile(generatedParagraph, outputFilePath);
    }
}
