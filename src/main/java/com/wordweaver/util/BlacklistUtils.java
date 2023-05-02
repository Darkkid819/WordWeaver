package com.wordweaver.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class BlacklistUtils {
    private final Set<String> blacklist;

    public BlacklistUtils(String dataFolder) throws Exception {
        if (dataFolder == null || dataFolder.isEmpty()) {
            throw new IllegalArgumentException("Invalid data folder: " + dataFolder);
        }

        this.blacklist = new HashSet<>();
        loadBlacklist(dataFolder, "blacklist.dat");
    }

    private void loadBlacklist(String dataFolder, String fileName) throws Exception {
        Path blacklistFilePath = Paths.get(dataFolder, fileName);
        if (!Files.exists(blacklistFilePath)) {
            return;
        }

        FileEncryptionUtils.readEncryptedFile(blacklistFilePath, blacklist);
    }

    public String[] replaceBlacklistedWords(String[] inputText, String replacement) {
        List<String> filteredWords = new LinkedList<>();

        for (String text : inputText) {
            String[] words = text.split("\\b");
            for (String word : words) {
                if (isBlacklisted(word)) {
                    filteredWords.add(replacement);
                } else {
                    filteredWords.add(word);
                }
            }
        }

        return filteredWords.toArray(new String[0]);
    }

    public boolean isBlacklisted(String word) {
        return blacklist.contains(word.toLowerCase());
    }
}