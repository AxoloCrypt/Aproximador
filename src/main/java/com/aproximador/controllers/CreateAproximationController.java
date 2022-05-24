package com.aproximador.controllers;

import com.aproximador.view.AproximationTab;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateAproximationController implements Initializable
{
    @FXML private TextField txtName;
    private Controller mainController;


    public String getName(){
        return txtName.getText();
    }

    public void createAproximationTab(){

        Button btnSave = new Button();

        btnSave.setOnAction(event -> {
            System.out.println("Test");
        });

        mainController.getTabAproximations().getTabs().add(new AproximationTab(getName(), btnSave));
    }

    public void init(Controller mainController){
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtName.setOnKeyPressed(event -> {

            if(event.getCode() == KeyCode.ENTER){
                Stage stage = (Stage) txtName.getScene().getWindow();
                stage.close();
                createAproximationTab();
            }

        });
    }
}
