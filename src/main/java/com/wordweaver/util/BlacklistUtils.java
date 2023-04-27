package com.wordweaver.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class BlacklistUtils {
    private final Set<String> blacklist;

    public BlacklistUtils(String dataFolder) throws Exception {
        this.blacklist = new HashSet<>();
        loadBlacklist(dataFolder);
    }

    private void loadBlacklist(String dataFolder) throws Exception {
        Path blacklistFilePath = Paths.get(dataFolder + "/blacklist.dat");
        if (!Files.exists(blacklistFilePath)) {
            return;
        }

        FileEncryptionUtils.readEncryptedFile(blacklistFilePath, blacklist);
    }

    public boolean isBlacklisted(String word) {
        return blacklist.contains(word.toLowerCase());
    }

    public Set<String> getBlacklist() {
        return blacklist;
    }

    public String replaceBlacklistedWords(String inputText, String replacement) {
        String[] words = inputText.split("\\s+");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (isBlacklisted(word)) {
                result.append(replacement);
            } else {
                result.append(word);
            }
            result.append(' ');
        }

        return result.toString().trim();
    }
}