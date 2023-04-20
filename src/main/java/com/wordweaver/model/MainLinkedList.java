package com.wordweaver.model;


import java.util.LinkedList;

public class MainLinkedList {
    private LinkedList<MainLink> mainLinks;

    public MainLinkedList() {
        this.mainLinks = new LinkedList<>();
    }

    public MainLink addMainLink(String keyword) {
        MainLink existingLink = findMainLink(keyword);
        if (existingLink == null) {
            MainLink newLink = new MainLink(keyword);
            mainLinks.add(newLink);
            return newLink;
        }
        return existingLink;
    }

    public MainLink findMainLink(String keyword) {
        for (MainLink link : mainLinks) {
            if (link.getKeyword().equals(keyword)) {
                return link;
            }
        }
        return null;
    }
}
