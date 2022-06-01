package com.asare.controllers;

import com.asare.app.App;
import com.asare.data.Connector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private final Connector connector = new Connector("juca", "g*$0Pe$h18cyiyJC");

    @FXML private Button btnLogin;
    @FXML public Button btnExit;
    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;

    public void openApp(ActionEvent actionEvent) throws IOException {

        try {
            if (!connector.validateUser(txtUsername.getText(), txtPassword.getText()))
                return;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
