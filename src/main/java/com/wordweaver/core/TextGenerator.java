package com.wordweaver.core;

import com.wordweaver.model.BabyLinkedList;
import com.wordweaver.model.MainLink;
import com.wordweaver.model.MainLinkedList;


public class TextGenerator {

    private MainLinkedList mainLinkedList;

    public TextGenerator(MainLinkedList mainLinkedList) {
        this.mainLinkedList = mainLinkedList;
    }

    public String generateParagraph(String startingWord, int wordCount) {
        StringBuilder paragraph = new StringBuilder();
        String currentWord = startingWord;

        for (int i = 0; i < wordCount; i++) {
            paragraph.append(currentWord).append(" ");

            BabyLinkedList babyLinkedList = mainLinkedList.findMainLink(currentWord).getBabyList();
            if (babyLinkedList != null) {
                currentWord = babyLinkedList.getRandomWord();
                if (currentWord == null) {
                    break;
                }
            } else {
                break;
            }
        }

        return paragraph.toString().trim();
    }
}
