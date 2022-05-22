package com.aproximador.controllers;

import com.aproximador.app.App;
import com.aproximador.data.*;
import com.aproximador.view.AproximationTab;
import com.aproximador.view.RecordPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    private final Materials materials = new Materials();
    private final Services services = new Services();
    private final Connector connector = new Connector("juca", "g*$0Pe$h18cyiyJC");

    @FXML
    public static VBox vBoxMaterials;
    public Button btnAddMaterial;
    public Button btnAddService;
    public TabPane tabAproximations;

    public void popUpAddRecord(String recordType, String cost) throws IOException {

        Parent root = FXMLLoader.load(App.class.getResource("addRecordPopUp.fxml"));

        Scene addRecordScene = new Scene(root);
        Stage addStage = new Stage();
        addStage.setScene(addRecordScene);
        addStage.initModality(Modality.NONE);
        addStage.show();

        new AddRecordController(recordType, cost);
    }

    public void createNewAproximation(){
        Button btnSave = new Button();

        btnSave.setOnAction(event -> {
            System.out.println("Test");
        });

        tabAproximations.getTabs().add(new AproximationTab("Tab", btnSave));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnAddMaterial.setOnAction(event -> {
            try {
                popUpAddRecord("Material", "Unit Cost");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnAddService.setOnAction(event -> {
            try {
                popUpAddRecord("Service", "Cost");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
