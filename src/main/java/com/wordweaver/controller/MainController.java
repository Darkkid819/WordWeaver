package com.wordweaver.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MainController implements Initializable {
    @FXML
    TextArea chatTextArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StringBuilder textContent = new StringBuilder();
        File inputFile = new File("output/output.txt");

        try (Scanner lines = new Scanner(inputFile)) {
            while (lines.hasNextLine()) {
                Scanner words = new Scanner(lines.nextLine());
                while (words.hasNext()) {
                    textContent.append(words.next()).append(" ");
                }
                words.close();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        chatTextArea.setText(textContent.toString());
    }
}