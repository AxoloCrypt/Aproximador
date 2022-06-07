package com.asare.controllers;

import com.asare.app.*;
import com.asare.data.Connector;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private final Connector connector = new Connector("juca", "g*$0Pe$h18cyiyJC");

    @FXML Controller controller;

    @FXML private Button btnLogin;
    @FXML public Button btnExit;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblError;


    public void openApp(ActionEvent actionEvent) throws IOException {

        try {
            if (!connector.validateUser(txtEmail.getText(), txtPassword.getText())){
                wrongUser();
                return;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return;
        }
        btnLogin.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader(App.class.getResource("app.fxml"));
        Parent root = loader.load();

        Scene appScene = new Scene(root);
        Stage appStage = new Stage();
        appStage.setTitle("Asare");

        appStage.setScene(appScene);
        appStage.initModality(Modality.NONE);
        appStage.initOwner(btnLogin.getScene().getWindow());
        appStage.show();

        controller = loader.getController();
        controller.initConnection(connector);
    }

    public void ExitApp() {
        System.exit(0);
    }


    private void wrongUser() {

        txtEmail.setStyle("-fx-border-color: rgb(229,29,78);" +
                "-fx-border-width: 1px;");

        txtPassword.setStyle("-fx-border-color: rgb(229,29,78);" +
                "-fx-border-width: 1px");
        lblError.setVisible(true);
        txtEmail.setTranslateY(txtEmail.getTranslateY() + 10);


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lblError.setVisible(false);

        txtEmail.setOnMouseClicked(event -> {
            txtEmail.setStyle(null);
            txtPassword.setStyle(null);
        });

        txtPassword.setOnMouseClicked(event -> {
            txtPassword.setStyle(null);
            txtEmail.setStyle(null);
        });

    }
}
