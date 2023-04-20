package com.wordweaver.model;


public class BabyLink {
    private WordFrequencyPair wordFrequencyPair;

    public BabyLink(WordFrequencyPair wordFrequencyPair) {
        this.wordFrequencyPair = wordFrequencyPair;
    }

    public WordFrequencyPair getWordFrequencyPair() {
        return wordFrequencyPair;
    }

    public void setWordFrequencyPair(WordFrequencyPair wordFrequencyPair) {
        this.wordFrequencyPair = wordFrequencyPair;
    }
}
