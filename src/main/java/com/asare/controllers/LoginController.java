package com.asare.controllers;

import com.asare.app.*;
import com.asare.data.Connector;
import com.asare.data.User;
import com.asare.exceptions.InvalidUserException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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

    @FXML private Label lblCreateAccount;

    private static boolean invalidLogin = false;


    public void openApp(ActionEvent actionEvent) throws IOException {

        try {
            if (!connector.validateUser(txtEmail.getText(), txtPassword.getText()))
                throw new InvalidUserException();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return;
        }
        catch (InvalidUserException iue){
            invalidLogin = true;
            wrongUser();
            return;
        }finally {
            connector.closeConnection();
        }

        btnLogin.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader(App.class.getResource("app.fxml"));


        User user;

        try {
            user = connector.getUserinfo(txtEmail.getText());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            connector.closeConnection();
        }

        Parent root = loader.load();
        controller = loader.getController();
        controller.initConnection(connector, user);

        Scene appScene = new Scene(root);
        Stage appStage = new Stage();
        appStage.setTitle("Asare");

        appStage.setScene(appScene);
        appStage.initModality(Modality.NONE);
        appStage.initOwner(btnLogin.getScene().getWindow());
        appStage.getIcons().add(new Image("https://github.com/AxoloCrypt/Asare/blob/test/src/main/resources/com/asare/images/Logo.png?raw=true"));
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
        txtPassword.setText(null);

        lblError.setVisible(true);
        lblEmail.setTranslateY(lblEmail.getTranslateY() - 5);
    }

    private void changeToSignUpScene(){




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

        lblCreateAccount.setOnMouseClicked(event -> {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("signUp.fxml"));
            Parent root;

            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            SignUpController signUpController = loader.getController();
            signUpController.init(connector);

        });

    }
}
