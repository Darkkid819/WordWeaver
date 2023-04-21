package com.wordweaver.core;

import com.wordweaver.model.BabyLinkedList;
import com.wordweaver.model.MainLinkedList;


public class TextGenerator {

    private MainLinkedList mainLinkedList;

    public TextGenerator(MainLinkedList mainLinkedList) {
        this.mainLinkedList = mainLinkedList;
    }

    private String capitalizeFirstLetter(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }

    private boolean isEndOfSentence(String word) {
        if (word == null || word.isEmpty()) {
            return false;
        }
        char lastChar = word.charAt(word.length() - 1);
        return lastChar == '.' || lastChar == '!' || lastChar == '?';
    }

    private boolean isPunctuation(String word) {
        if (word.length() == 1) {
            return word.equals(".") || word.equals(",") || word.equals("!") || word.equals("?") || word.equals(";") || word.equals(":");
        } else {
            return word.matches("['’]\\w+");
        }
    }

    public String generateParagraph(String startingWord, int wordCount) {
        StringBuilder paragraph = new StringBuilder(startingWord);
        String currentWord = startingWord;
        int wordsGenerated = 1;
        boolean isNewSentence = false;

        while (wordsGenerated < wordCount) {
            BabyLinkedList babyLinkedList = mainLinkedList.findMainLink(currentWord).getBabyList();
            if (babyLinkedList != null) {
                currentWord = babyLinkedList.getRandomWord();
                if (currentWord == null) {
                    break;
                }
            } else {
                break;
            }
            String word = currentWord;

            if (isNewSentence) {
                word = capitalizeFirstLetter(currentWord);
                isNewSentence = false;
            }

            if (isEndOfSentence(word)) {
                isNewSentence = true;
            }

            if (isPunctuation(word)) {
                paragraph.append(word);
            } else {
                paragraph.append(" ").append(word);
            }
            wordsGenerated++;
        }

        return paragraph.toString();
    }
}