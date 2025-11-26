package com.mindreader007.nsucash;

// Color Palette
// COLOR 1 - #d1dbe4
// COLOR 2 - #a3b7ca
// COLOR 3 - #7593af
// COLOR 4 - #476f95
// COLOR 5 - #194a7a
// COLOR 6 - #061742

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    private static Stage stg;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stg = primaryStage;

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/LoginScreen.fxml")));

        Scene scene = new Scene(root);

        primaryStage.setTitle("NSUCash");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void changeScene(String fxml, double width, double height) throws IOException {
        Parent pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));

        Scene newScene = new Scene(pane, width, height);
        stg.setScene(newScene);
        Platform.runLater(() -> stg.centerOnScreen());
    }

    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
        stg.getScene().setRoot(pane);
    }

    public static void main(String[] args){
        launch();
    }
}
