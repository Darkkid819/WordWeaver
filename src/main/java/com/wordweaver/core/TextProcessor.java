package com.wordweaver.core;

import com.wordweaver.util.BlacklistUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextProcessor {

    // Method to split a string into words
    private static String[] tokenize(String inputText) {
        Pattern pattern = Pattern.compile("\\b\\w+\\b");
        Matcher matcher = pattern.matcher(inputText);

        List<String> words = new ArrayList<>();
        while (matcher.find()) {
            words.add(matcher.group());
        }

        return words.toArray(new String[0]);
    }

    private static String removeUnwantedCharacters(String inputText, String[] unwantedCharacters) {
        if (unwantedCharacters == null || unwantedCharacters.length == 0) {
            return inputText;
        }

        String regex = "[" + String.join("", unwantedCharacters) + "]";
        return inputText.replaceAll(regex, "");
    }

    // Method to filter out blacklisted words from an array of words
    private static String[] filterWords(String[] words, BlacklistUtils blacklistUtils, BiConsumer<Integer, Integer> progressCallback) {
        if (blacklistUtils == null) {
            return words;
        }

        List<String> filteredWords = new LinkedList<>();
        int totalWords = words.length;
        for (int i = 0; i < totalWords; i++) {
            String word = words[i];
            if (!blacklistUtils.isBlacklisted(word)) {
                filteredWords.add(word);
            }

            // Update progress
            progressCallback.accept(i + 1, totalWords);
        }

        return filteredWords.toArray(new String[0]);
    }

    // Method to process text by tokenizing, filtering, and removing unwanted characters
    public static String[] processText(List<String> texts, String[] unwantedCharacters, BlacklistUtils blacklistUtils, BiConsumer<Integer, Integer> progressCallback) {
        String inputText = String.join(" ", texts);

        if (unwantedCharacters != null && unwantedCharacters.length > 0) {
            inputText = removeUnwantedCharacters(inputText, unwantedCharacters);
        }

        String[] words = tokenize(inputText);

        if (blacklistUtils != null) {
            words = filterWords(words, blacklistUtils, progressCallback);
        }

        return words;
    }

}
