package com.aproximador.controllers;

import com.aproximador.app.App;
import com.aproximador.view.RecordPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
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

    private VBox vBoxRecord;

    public AddRecordController(String recordType, String cost, VBox vBoxRecord) throws IOException {
        Parent root = FXMLLoader.load(App.class.getResource("addRecordPopUp.fxml"));
        Scene addRecordScene = new Scene(root);
        Stage addStage = new Stage();
        addStage.setScene(addRecordScene);
        addStage.initModality(Modality.NONE);

        lblRecord.setText(recordType);
        lblCost.setText(cost);
        this.vBoxRecord = vBoxRecord;

        addStage.show();
    }

    public AddRecordController() {}


    public void registerRecord(){
        RecordPane recordPane = new RecordPane(txtName.getText(), txtCost.getText(), txtDescription.getText());

        vBoxRecord.getChildren().add(recordPane);
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
