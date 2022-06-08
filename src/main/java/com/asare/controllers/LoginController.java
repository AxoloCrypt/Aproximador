package com.asare.controllers;

import com.asare.app.*;
import com.asare.data.Connector;
import com.asare.data.User;
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
    @FXML private Label lblEmail;

    private static boolean invalidLogin = false;


    public void openApp(ActionEvent actionEvent) throws IOException {

        try {
            if (!connector.validateUser(txtEmail.getText(), txtPassword.getText())){
                invalidLogin = true;
                wrongUser();
                return;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return;
        }
        btnLogin.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader(App.class.getResource("app.fxml"));


        User user = new User(txtEmail.getText(), txtPassword.getText());

        Parent root = loader.load();
        controller = loader.getController();
        controller.initConnection(connector, user);

        Scene appScene = new Scene(root);
        Stage appStage = new Stage();
        appStage.setTitle("Asare");

        appStage.setScene(appScene);
        appStage.initModality(Modality.NONE);
        appStage.initOwner(btnLogin.getScene().getWindow());
        appStage.show();
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
        lblEmail.setTranslateY(lblEmail.getTranslateY() - 5);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lblError.setVisible(false);

        txtEmail.setOnMouseClicked(event -> {
            txtEmail.setStyle(null);
            txtPassword.setStyle(null);

            if(invalidLogin){
                lblEmail.setTranslateY(lblEmail.getTranslateY() + 5);
                invalidLogin = false;
                lblError.setVisible(false);
            }

        });

        txtPassword.setOnMouseClicked(event -> {
            txtPassword.setStyle(null);
            txtEmail.setStyle(null);

            if(invalidLogin){
                lblEmail.setTranslateY(lblEmail.getTranslateY() + 5);
                invalidLogin = false;
                lblError.setVisible(false);
            }

        });

    }
}
