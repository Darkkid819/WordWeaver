package com.wordweaver.controller;

import com.wordweaver.Main;
import com.wordweaver.core.TextGenerator;
import com.wordweaver.core.WordWeaverBuilder;
import com.wordweaver.model.MainLink;
import com.wordweaver.model.MainLinkedList;
import com.wordweaver.util.BlacklistUtils;
import com.wordweaver.util.FileUtils;
import com.wordweaver.util.GlobalVariables;
import com.wordweaver.util.TextToSpeech;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.*;

import java.io.File;
import java.io.IOException;

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
    MenuItem saveMenuItem;
    @FXML
    MenuItem saveAsMenuItem;
    @FXML
    MenuItem importMenuItem;
    @FXML
    MenuItem quitMenuItem;
    @FXML
    MenuItem documentationMenuItem;
    @FXML
    MenuItem aboutMenuItem;

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

    @FXML
    private void saveFile(ActionEvent event) throws IOException {
        String outputFilePath = GlobalVariables.getInstance().getOutputFolder() + "/" + GlobalVariables.getInstance().getOutputFileName();
        String generatedParagraph = chatTextArea.getText();

        if (generatedParagraph.isEmpty()) {
            // Text area is empty, show a warning dialog
            showAlert(Alert.AlertType.WARNING, "Empty text", "Please generate a paragraph first.");
            return;
        }
        FileUtils.createDirectory(GlobalVariables.getInstance().getOutputFolder());
        FileUtils.writeFile(generatedParagraph, outputFilePath);
    }

    @FXML
    private void saveAsFile(ActionEvent event) throws IOException {
        String generatedParagraph = chatTextArea.getText();

        if (generatedParagraph.isEmpty()) {
            // Text area is empty, show a warning dialog
            showAlert(Alert.AlertType.WARNING, "Empty text", "Please generate a paragraph first.");
            return;
        }

        String userDirectory = System.getProperty("user.dir");
        FileUtils.createDirectory(GlobalVariables.getInstance().getOutputFolder());
        File defaultDirectory = new File(userDirectory, GlobalVariables.getInstance().getOutputFolder());

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(defaultDirectory);
        fileChooser.setInitialFileName(FileUtils.generateUniqueFileName());
        fileChooser.setTitle("Save As");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        Window window = chatTextArea.getScene().getWindow();
        File file = fileChooser.showSaveDialog(window);
        if (file != null) {
            FileUtils.writeFile(generatedParagraph, file.getAbsolutePath());
        }
    }

    @FXML
    private void importText(ActionEvent event) throws IOException {
        String userDirectory = System.getProperty("user.dir");
        File dataDirectory = new File(userDirectory, GlobalVariables.getInstance().getDataFolder());

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(dataDirectory);
        fileChooser.setTitle("Import");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        Window window = chatTextArea.getScene().getWindow();
        File file = fileChooser.showOpenDialog(window);
        if (file != null) {
            MainLinkedList wordWeaver = GlobalVariables.getInstance().getWordWeaver();
            String dataFolder = GlobalVariables.getInstance().getDataFolder();
            String blacklist = GlobalVariables.getInstance().getBlacklist();
            String blacklistPath = dataFolder + "/" + blacklist;
            BlacklistUtils blacklistUtils = new BlacklistUtils(blacklistPath);

            Stage progressStage = createProgressPopup();
            progressStage.show();

            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    WordWeaverBuilder.addTextToModel(wordWeaver, file.getAbsolutePath(), blacklistUtils);
                    return null;
                }
            };

            task.setOnSucceeded(e -> progressStage.close());
            task.setOnFailed(e -> progressStage.close());

            new Thread(task).start();
        }
    }

    private Stage createProgressPopup() {
        // Create a progress indicator
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setMaxSize(100, 100);
        progressIndicator.setStyle("-fx-background-color: #444653;");

        // Create a new stage for the progress popup
        Stage progressStage = new Stage();
        progressStage.initModality(Modality.APPLICATION_MODAL);
        progressStage.setResizable(false);
        progressStage.initStyle(StageStyle.UNDECORATED);

        // Create a scene with the progress indicator and add it to the stage
        Scene progressScene = new Scene(progressIndicator);
        progressStage.setScene(progressScene);

        return progressStage;
    }

    @FXML
    private void quit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void openDocumentation(ActionEvent event) {
        String githubURL = "https://github.com/Darkkid819/WordWeaver";
        try {
            Main.getHostServicesInstance().showDocument(githubURL);
        } catch (Exception e) {
            showAlert(Alert.AlertType.WARNING, "Cannot Open URL", "Please open the following URL manually in your web browser:\n" + githubURL);
        }
    }

    @FXML
    private void showAboutDialog(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Word Weaver");
        String aboutText = "Version: 1.0.0\n"
                + "Author: Jordan Mitacek\n"
                + "Description: Word Weaver is an intelligent text generation application that uses a Markov chain " +
                "model to create realistic, coherent paragraphs based on a provided seed word. Users can easily import text " +
                "files for training the model, set a blacklist to exclude specific words, and generate paragraphs with a " +
                "specified length. The application also offers an intuitive user interface for easy interaction, text-to-speech functionality, " +
                "and options to save generated text. This versatile tool is perfect for writers, content creators, and anyone seeking " +
                "inspiration for their creative projects.";
        alert.setContentText(aboutText);
        alert.showAndWait();
    }
}