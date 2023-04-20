package com.wordweaver.model;

import com.wordweaver.util.RandomSelector;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class BabyLinkedList {

    private LinkedList<BabyLink> babyLinkList;

    public BabyLinkedList() {
        babyLinkList = new LinkedList<>();
    }

    public void addBabyLink(String word) {
        BabyLink existingBabyLink = findBabyLinkByWord(word);
        if (existingBabyLink == null) {
            babyLinkList.add(new BabyLink(new WordFrequencyPair(word, 1)));
        } else {
            existingBabyLink.getWordFrequencyPair().incrementFrequency();
        }
    }

    public String getRandomWord() {
        if (babyLinkList.isEmpty()) {
            return null;
        }

        RandomSelector randomSelector = new RandomSelector();
        List<String> words = babyLinkList.stream().map(babyLink -> babyLink.getWordFrequencyPair().getWord()).collect(Collectors.toList());
        List<Integer> frequencies = babyLinkList.stream().map(babyLink -> babyLink.getWordFrequencyPair().getFrequency()).collect(Collectors.toList());

        return randomSelector.selectWeightedRandom(words, frequencies);
    }

    private BabyLink findBabyLinkByWord(String word) {
        for (BabyLink babyLink : babyLinkList) {
            if (babyLink.getWordFrequencyPair().getWord().equals(word)) {
                return babyLink;
            }
        }
        return null;
    }
}




