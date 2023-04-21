package com.wordweaver;

import com.wordweaver.core.WordWeaverBuilder;
import com.wordweaver.util.GlobalVariables;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Word Weaver");
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/wordweaver/images/logo.png")));
        stage.show();
        stage.setMinWidth(scene.getWidth());
        stage.setMinHeight(scene.getHeight());
    }

    public static void main(String[] args) throws IOException {
        GlobalVariables.getInstance().setWordWeaver(WordWeaverBuilder.build(GlobalVariables.getInstance().getDataFolder(),
                GlobalVariables.getInstance().getOutputFolder(),
                GlobalVariables.getInstance().getOutputFileName(),
                GlobalVariables.getInstance().getBlacklist()));
        launch(args);
    }

}