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

        btnLogin.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(App.class.getResource("app.fxml"));

        Scene appScene = new Scene(root);
        Stage appStage = new Stage();
        appStage.setTitle("Aproximador");

        appStage.setScene(appScene);
        appStage.initModality(Modality.NONE);
        appStage.initOwner(btnLogin.getScene().getWindow());
        appStage.show();



    }

    public void ExitApp(ActionEvent actionEvent) {
        System.exit(0);
    }
}
