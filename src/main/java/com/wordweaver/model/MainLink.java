package com.wordweaver.model;

public class MainLink {
    private String keyword;
    private BabyLinkedList babyList;

    public MainLink(String keyword) {
        this.keyword = keyword;
        this.babyList = new BabyLinkedList();
    }

    public String getKeyword() {
        return keyword;
    }

    public BabyLinkedList getBabyList() {
        return babyList;
    }
}