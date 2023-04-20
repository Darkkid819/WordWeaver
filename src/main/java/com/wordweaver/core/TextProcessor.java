package com.wordweaver.core;

import com.wordweaver.util.BlacklistUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextProcessor {

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

    private static String[] filterWords(String[] words, BlacklistUtils blacklistUtils) {
        if (blacklistUtils == null) {
            return words;
        }

        List<String> filteredWords = new LinkedList<>();
        for (String word : words) {
            if (!blacklistUtils.isBlacklisted(word)) {
                filteredWords.add(word);
            }
        }

        return filteredWords.toArray(new String[0]);
    }


    public static String[] processText(List<String> texts, String[] unwantedCharacters, BlacklistUtils blacklistUtils) {
        String inputText = String.join(" ", texts);

        if (unwantedCharacters != null && unwantedCharacters.length > 0) {
            inputText = removeUnwantedCharacters(inputText, unwantedCharacters);
        }

        String[] words = tokenize(inputText);

        if (blacklistUtils != null) {
            words = filterWords(words, blacklistUtils);
        }

        return words;
    }

}
