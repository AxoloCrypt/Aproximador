package com.aproximador.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddRecordController implements Initializable
{
    @FXML
    private Label lblRecord;
    @FXML
    private Label lblCost;
    @FXML
    private Button btnOk;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnOk.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER){
                    Stage stage = (Stage) btnOk.getScene().getWindow();
                    stage.close();
                }
            }
        });
    }
}
