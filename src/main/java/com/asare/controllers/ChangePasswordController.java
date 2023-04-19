package com.asare.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ChangePasswordController implements Initializable {

    @FXML private PasswordField txtOldPassword;
    @FXML private PasswordField txtNewPassword;

    private Controller mainController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        txtOldPassword.setOnKeyPressed(event -> {

            if(event.getCode() == KeyCode.ENTER)
                txtNewPassword.requestFocus();

        });

        txtNewPassword.setOnKeyPressed(event -> {
            try {

                if(event.getCode() == KeyCode.ENTER){
                    if(!mainController.getConnector().changeUserPassword(txtOldPassword.getText(), txtNewPassword.getText(), mainController.getUser()))
                        throw new SQLException();

                    Stage stage = (Stage) txtNewPassword.getScene().getWindow();
                    stage.close();
                }

            }catch (SQLException e){
                e.printStackTrace();
            }

        });

    }

    public void init(Controller mainController){
        this.mainController = mainController;
    }


}
