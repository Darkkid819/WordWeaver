package com.wordweaver.util;

import com.wordweaver.model.MainLinkedList;

public class GlobalVariables {
    private static GlobalVariables instance = null;

    // file variables
    private MainLinkedList wordWeaver;
    private String dataFolder = "data";
    private String outputFolder = "output";
    private String outputFileName = "output.txt";

    private GlobalVariables() {
        // Exists only to defeat instantiation.
    }

    public static GlobalVariables getInstance() {
        if (instance == null) {
            instance = new GlobalVariables();
        }
        return instance;
    }

    public MainLinkedList getWordWeaver() {
        return wordWeaver;
    }

    public void setWordWeaver(MainLinkedList wordWeaver) {
        this.wordWeaver = wordWeaver;
    }

    public String getDataFolder() {
        return dataFolder;
    }

    public void setDataFolder(String dataFolder) {
        this.dataFolder = dataFolder;
    }

    public String getOutputFolder() {
        return outputFolder;
    }

    public void setOutputFolder(String outputFolder) {
        this.outputFolder = outputFolder;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }
}
