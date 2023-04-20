package com.wordweaver;

import com.wordweaver.core.WordWeaverBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Word Weaver");
        stage.setScene(scene);
        stage.show();
        stage.setMinWidth(scene.getWidth());
        stage.setMinHeight(scene.getHeight());
    }

    public static void main(String[] args) throws IOException {
        String dataFolder = "data";
        String outputFolder = "output";
        String outputFileName = "output.txt";
        String blacklist = "blacklist.txt";

        WordWeaverBuilder.build(dataFolder, outputFolder, outputFileName, blacklist);
        launch(args);
    }

}