package com.wordweaver.core;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextProcessor {

    public static String[] tokenize(String inputText) {
        Pattern pattern = Pattern.compile("\\b\\w+\\b");
        Matcher matcher = pattern.matcher(inputText);

        List<String> words = new ArrayList<>();
        while (matcher.find()) {
            words.add(matcher.group());
        }

        return words.toArray(new String[0]);
    }

    public static String removeUnwantedCharacters(String inputText, String[] unwantedCharacters) {
        if (unwantedCharacters == null || unwantedCharacters.length == 0) {
            return inputText;
        }

        String regex = "[" + String.join("", unwantedCharacters) + "]";
        return inputText.replaceAll(regex, "");
    }

    public static String[] filterWords(String[] words, String[] blacklist) {
        if (blacklist == null || blacklist.length == 0) {
            return words;
        }

        List<String> filteredWords = new ArrayList<>();
        for (String word : words) {
            boolean isBlacklisted = false;

            for (String forbiddenWord : blacklist) {
                if (word.equalsIgnoreCase(forbiddenWord)) {
                    isBlacklisted = true;
                    break;
                }
            }

            if (!isBlacklisted) {
                filteredWords.add(word);
            }
        }

        return filteredWords.toArray(new String[0]);
    }

    public static String[] processMultipleTexts(List<String> texts, String[] unwantedCharacters, String[] blacklist) {
        StringBuilder combinedText = new StringBuilder();

        for (String text : texts) {
            combinedText.append(text).append(" ");
        }

        String inputText = combinedText.toString();

        if (unwantedCharacters != null && unwantedCharacters.length > 0) {
            inputText = removeUnwantedCharacters(inputText, unwantedCharacters);
        }

        String[] words = tokenize(inputText);

        if (blacklist != null && blacklist.length > 0) {
            words = filterWords(words, blacklist);
        }

        return words;
    }

}
