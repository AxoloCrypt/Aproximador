package com.aproximador.controllers;

import com.aproximador.app.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    private Button btnLogin;

    public void openApp(ActionEvent actionEvent) throws IOException {
        System.out.println(App.class.getResource("app.fxml"));

        FXMLLoader loader = FXMLLoader.load(App.class.getResource("app.fxml"));
        Pane root = loader.load();

        Scene LoginScene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Aproximador");
        primaryStage.setScene(LoginScene);
        primaryStage.initModality(Modality.WINDOW_MODAL);
        primaryStage.initOwner(btnLogin.getScene().getWindow());
        primaryStage.show();
    }
}
