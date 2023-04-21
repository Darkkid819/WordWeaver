package com.wordweaver.controller;

import com.wordweaver.core.TextGenerator;
import com.wordweaver.model.MainLink;
import com.wordweaver.util.GlobalVariables;
import com.wordweaver.util.TextToSpeech;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class MainController {

    @FXML
    TextArea chatTextArea;
    @FXML
    TextField keywordTextField;
    @FXML
    TextField lengthTextField;
    @FXML
    Button submitButton;
    @FXML
    Button ttsButton;
    @FXML
    ImageView submitImageView;
    @FXML
    ImageView speechImageView;

    @FXML
    private void changeSubmitImagePressed(MouseEvent event) {
        submitImageView.setImage(new Image(getClass().getResourceAsStream("/com/wordweaver/images/submit-pressed.png")));
    }

    @FXML
    private void changeSubmitImageReleased(MouseEvent event) {
        submitImageView.setImage(new Image(getClass().getResourceAsStream("/com/wordweaver/images/submit.png")));
    }

    @FXML
    private void changeSpeechImagePressed(MouseEvent event) {
        speechImageView.setImage(new Image(getClass().getResourceAsStream("/com/wordweaver/images/speech-pressed.png")));
    }

    @FXML
    private void changeSpeechImageReleased(MouseEvent event) {
        speechImageView.setImage(new Image(getClass().getResourceAsStream("/com/wordweaver/images/speech.png")));
    }


    @FXML
    private void submit(ActionEvent event) {
        String keyword = keywordTextField.getText();
        String lengthString = lengthTextField.getText();

        if (keyword.isEmpty() || lengthString.isEmpty()) {
            // One or both fields are empty, show a warning dialog
            showAlert(Alert.AlertType.WARNING, "Missing input", "Please enter a keyword and a text length.");
        } else if (!keyword.matches("[\\w]+([’']\\w+)?([.,!?;:]+)?")) {
            // Keyword is not a single word, show a warning dialog
            showAlert(Alert.AlertType.WARNING, "Invalid keyword", "Please enter a single word as the keyword.");
        } else if (!lengthString.matches("\\d+")) {
            // Length is not a valid number, show a warning dialog
            showAlert(Alert.AlertType.WARNING, "Invalid text length", "Please enter a valid number as the text length.");
        } else if (GlobalVariables.getInstance().getWordWeaver() == null) {
            // Main.wordWeaver is null, show an error dialog
            showAlert(Alert.AlertType.ERROR, "Error", "WordWeaver is not initialized.");
        } else {
            MainLink mainLink = GlobalVariables.getInstance().getWordWeaver().findMainLink(keyword);
            if (mainLink == null) {
                // MainLinkedList doesn't contain the keyword, show a warning dialog
                showAlert(Alert.AlertType.WARNING, "Invalid keyword", "Please enter a valid keyword.");
            } else {
                int length = Integer.parseInt(lengthString);
                TextGenerator textGenerator = new TextGenerator(GlobalVariables.getInstance().getWordWeaver());
                String paragraph = textGenerator.generateParagraph(keyword, length);
                chatTextArea.setText(paragraph);
            }
        }
    }

    private void showAlert(Alert.AlertType alertType, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    @FXML
    private void speak(ActionEvent event) {
        String paragraph = chatTextArea.getText();
        TextToSpeech.speak(paragraph);
    }
}