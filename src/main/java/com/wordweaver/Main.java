package com.wordweaver;

import com.wordweaver.core.WordWeaverBuilder;
import com.wordweaver.util.GlobalVariables;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static HostServices hostServices;
    public static HostServices getHostServicesInstance() {
        return hostServices;
    }

    @Override
    public void start(Stage stage) throws IOException {
        hostServices = getHostServices();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/wordweaver/images/logo.png")));
        stage.setTitle("Word Weaver");
        stage.setScene(scene);
        stage.show();

        // set minimum window size
        stage.setMinWidth(scene.getWidth());
        stage.setMinHeight(scene.getHeight());
    }

    public static void main(String[] args) throws Exception {
        // if there is a command line argument, use it as the data folder
        if (args.length > 0) {
            String dataFolder = args[0];
            GlobalVariables.getInstance().setWordWeaver(WordWeaverBuilder.build(dataFolder));
        }

        launch(args);
    }

}