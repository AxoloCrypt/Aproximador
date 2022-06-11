package com.asare.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application
{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));


        primaryStage.getIcons().add(new Image("https://github.com/AxoloCrypt/Asare/blob/test/src/main/resources/com/asare/images/Logo.png?raw=true"));

        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Login");

        primaryStage.setScene(scene);
        primaryStage.show();


    }
}
