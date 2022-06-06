package com.asare.controllers;

import com.asare.app.App;
import com.asare.data.Connector;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        clearColor();
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

    public void ExitApp(ActionEvent actionEvent) {
        System.exit(0);
    }


    private void wrongUser() {
        ObservableList<String> borderTxt = txtEmail.getStyleClass();
        ObservableList<String> borderPass = txtPassword.getStyleClass();

        if(!borderTxt.contains("WrongData")) {
            borderTxt.add("WrongData");
        }
        if(!borderPass.contains("WrongData")) {
            borderPass.add("WrongData");
        }
    }

    private void clearColor() {
        ObservableList<String> clearBorderTxt = txtEmail.getStyleClass();
        ObservableList<String> clearPassTxt = txtPassword.getStyleClass();
        clearBorderTxt.removeAll(Collections.singleton("Wrong Data"));
        clearPassTxt.removeAll(Collections.singleton("Wrong Data"));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
