package com.wordweaver.controller;

import com.wordweaver.Main;
import com.wordweaver.core.TextGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    TextArea chatTextArea;
    @FXML
    TextField keywordTextField;
    @FXML
    TextField lengthTextField;
    @FXML
    Button submitButton;
    @FXML
    ImageView submitImageView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        StringBuilder textContent = new StringBuilder();
//        String outputFilePath = Main.outputFolder + "/" + Main.outputFileName;
//        File inputFile = new File(outputFilePath);
//
//        try (Scanner lines = new Scanner(inputFile)) {
//            while (lines.hasNextLine()) {
//                Scanner words = new Scanner(lines.nextLine());
//                while (words.hasNext()) {
//                    textContent.append(words.next()).append(" ");
//                }
//                words.close();
//            }
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//        chatTextArea.setText("textContent.toString()");
    }

    @FXML
    private void changeImagePressed(MouseEvent event) {
        submitImageView.setImage(new Image(getClass().getResourceAsStream("/com/wordweaver/submit-pressed.png")));
    }

    @FXML
    private void changeImageReleased(MouseEvent event) {
        submitImageView.setImage(new Image(getClass().getResourceAsStream("/com/wordweaver/submit.png")));
    }

    @FXML
    private void submit(ActionEvent event) {
        String keyword = keywordTextField.getText();
        String lengthString = lengthTextField.getText();

        if (keyword.isEmpty() || lengthString.isEmpty()) {
            // One or both fields are empty, show a warning dialog
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Missing input");
            alert.setContentText("Please enter a keyword and a text length.");
            alert.showAndWait();
        } else if (!keyword.matches("\\w+")) {
            // Keyword is not a single word, show a warning dialog
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Invalid keyword");
            alert.setContentText("Please enter a single word as the keyword.");
            alert.showAndWait();
        } else if (!lengthString.matches("\\d+")) {
            // Length is not a valid number, show a warning dialog
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Invalid text length");
            alert.setContentText("Please enter a valid number as the text length.");
            alert.showAndWait();
        } else {
            try {
                int length = Integer.parseInt(lengthString);
                TextGenerator textGenerator = new TextGenerator(Main.wordWeaver);
                String paragraph = textGenerator.generateParagraph(keyword, length);
                chatTextArea.setText(paragraph);
            } catch (NullPointerException e) {
                // MainLinkedList doesn't contain the keyword, show a warning dialog
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Invalid keyword");
                alert.setContentText("Please enter a valid keyword.");
                alert.showAndWait();
            }
        }
    }
}