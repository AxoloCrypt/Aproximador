package com.aproximador.controllers;

import com.aproximador.view.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

;

public class AddRecordController implements Initializable
{
    private Controller mainController;
    private String recordType;

    @FXML private Label lblRecord;
    @FXML private Label lblCost;
    @FXML private Button btnOk;
    @FXML private TextField txtName;
    @FXML private TextField txtCost;
    @FXML private TextArea txtDescription;


    public AddRecordController() {}


    public void registerRecord(){
        RecordPane recordPane = new RecordPane(txtName.getText(), txtCost.getText(), txtDescription.getText(), mainController);

        if (recordType.toLowerCase(Locale.ROOT).equals("material"))
            mainController.getvBoxMaterials().getChildren().add(recordPane);
        else
            mainController.getvBoxServices().getChildren().add(recordPane);

    }

    public void init(Controller mainController, String recordType) {
        this.mainController = mainController;
        this.recordType = recordType;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lblRecord.setText("Name");
        lblCost.setText("Cost");

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
