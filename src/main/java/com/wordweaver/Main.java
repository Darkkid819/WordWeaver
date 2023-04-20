package com.wordweaver;

import com.wordweaver.core.FileHandler;
import com.wordweaver.core.TextGenerator;
import com.wordweaver.core.TextProcessor;
import com.wordweaver.model.MainLinkedList;
import com.wordweaver.util.BlacklistUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Word Weaver");
        stage.setScene(scene);
        stage.show();
        stage.setMinWidth(scene.getWidth());
        stage.setMinHeight(scene.getHeight());
    }

    public static void main(String[] args) throws IOException {
        //build();
        launch();
    }

    public static void build() throws IOException {
        String dataFolder = "data";
        String outputFolder = "output";
        String outputFileName = "output.txt";
        String outputFilePath = outputFolder + "/" + outputFileName;
        String blacklistFilePath = dataFolder + "/blacklist.txt";
        BlacklistUtils blacklistUtils = new BlacklistUtils(blacklistFilePath);
        String[] blacklist = blacklistUtils.getBlacklist().toArray(new String[0]);

        try {
            // Read and process all text files in the "data" folder
            List<String> texts = FileHandler.readAllTextFilesInFolder(dataFolder);
            String[] words = TextProcessor.processMultipleTexts(texts, null, blacklist);

            // Build MainLinkedList from the processed words
            MainLinkedList mainLinkedList = new MainLinkedList();
            for (int i = 0; i < words.length - 1; i++) {
                String keyword = words[i];
                String followingWord = words[i + 1];

                mainLinkedList.addMainLink(keyword).getBabyList().addBabyLink(followingWord);
            }

            // Generate a paragraph using the TextGenerator
            TextGenerator textGenerator = new TextGenerator(mainLinkedList);
            String startingWord = "The";
            int wordCount = 100;
            String generatedParagraph = textGenerator.generateParagraph(startingWord, wordCount);

            // Display the generated paragraph and save it to the output file
            System.out.println(generatedParagraph);
            FileHandler.writeGeneratedTextToFile(outputFilePath, generatedParagraph);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error occurred while reading or writing files.");
        }
    }
}