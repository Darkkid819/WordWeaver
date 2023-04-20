package com.wordweaver.util;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BlacklistUtils {
    private final Set<String> blacklist;

    public BlacklistUtils(String blacklistFilePath) throws IOException {
        this.blacklist = new HashSet<>();
        loadBlacklist(blacklistFilePath);
    }

    private void loadBlacklist(String filePath) throws IOException {
        List<String> lines = FileUtils.readLines(filePath);
        for (String line : lines) {
            blacklist.add(line.trim().toLowerCase());
        }
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