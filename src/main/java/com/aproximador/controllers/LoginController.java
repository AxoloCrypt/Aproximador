package com.aproximador.controllers;

import com.aproximador.app.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {


    @FXML
    private Button btnLogin;

    @FXML
    public Button btnExit;


    public void openApp(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(App.class.getResource("app.fxml"));

        Scene LoginScene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Aproximador");
        primaryStage.setScene(LoginScene);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initOwner(btnLogin.getScene().getWindow());
        primaryStage.show();
        Stage primaryStage = (Stage) this.btnLogin.getScene().getWindow();
        primaryStage.close();;
    }

    public void ExitApp(ActionEvent actionEvent) {
        System.exit(0);
    }
}
