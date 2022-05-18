package com.aproximador.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    private Button btnLogin;

    public void openApp(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("app.fxml"));

        Scene LoginScene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Aproximador");
        primaryStage.setScene(LoginScene);
        primaryStage.initModality(Modality.WINDOW_MODAL);
        primaryStage.initOwner(btnLogin.getScene().getWindow());
        primaryStage.show();
    }
}
