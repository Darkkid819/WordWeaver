package com.wordweaver;

import com.wordweaver.core.WordWeaverBuilder;
import com.wordweaver.model.MainLinkedList;
import com.wordweaver.util.FileUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    // temp
    // file variables
    public static MainLinkedList wordWeaver;
    public static String dataFolder = "data";
    public static String outputFolder = "output";
    public static String outputFileName = FileUtils.generateUniqueFileName();
    public static String blacklist = "blacklist.txt";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Word Weaver");
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/wordweaver/logo.png")));
        stage.show();
        stage.setMinWidth(scene.getWidth());
        stage.setMinHeight(scene.getHeight());
    }

    public static void main(String[] args) throws IOException {
        wordWeaver = WordWeaverBuilder.build(dataFolder, outputFolder, outputFileName, blacklist);
        launch(args);
    }

}