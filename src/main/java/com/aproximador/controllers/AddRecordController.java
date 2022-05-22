package com.aproximador.controllers;

import com.aproximador.view.RecordPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddRecordController implements Initializable
{
    @FXML
    public Label lblRecord;
    public Label lblCost;
    public Button btnOk;
    public TextField txtName;
    public TextField txtCost;
    public TextArea txtDescription;

    public AddRecordController(String recordType, String cost){
        lblRecord = new Label(recordType);
        lblCost = new Label(cost);
    }

    public AddRecordController() {}


    public void registerRecord(){
        RecordPane recordPane = new RecordPane(txtName.getText(), txtCost.getText(), txtDescription.getText());

        Controller.vBoxMaterials.getChildren().add(recordPane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnOk.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                Stage stage = (Stage) btnOk.getScene().getWindow();
                stage.close();
            }
        });

        txtName.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER)
                txtCost.requestFocus();
        });

        txtCost.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER)
                txtDescription.requestFocus();
        });

        txtDescription.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER){
                txtDescription.setDisable(true);
                Stage stage = (Stage) txtDescription.getScene().getWindow();
                registerRecord();
                stage.close();
            }
        });

    }
}
