package com.wordweaver.core;

import com.wordweaver.util.BlacklistUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextProcessor {
    private static String[] tokenize(String inputText) {
        //includes words and punctuation
        Pattern pattern = Pattern.compile("\\b\\w+([’']\\w+)?\\b|[.,!?;:`]");
        Matcher matcher = pattern.matcher(inputText);

        List<String> words = new LinkedList<>();
        while (matcher.find()) {
            words.add(matcher.group());
        }

        return words.toArray(new String[0]);
    }

    // Method to process text by tokenizing and filtering words
    public static String[] processText(List<String> texts, BlacklistUtils blacklistUtils) {
        String inputText = String.join(" ", texts);

        String[] words = tokenize(inputText);

        if (blacklistUtils != null) {
            words = blacklistUtils.replaceBlacklistedWords(words, "");
        }

        return words;
    }

}
