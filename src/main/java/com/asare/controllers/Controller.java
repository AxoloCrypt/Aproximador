package com.asare.controllers;

import com.asare.app.App;
import com.asare.data.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    private final Materials materials = new Materials();
    private final Services services = new Services();
    private final History history = new History();
    private Connector connector;
    private final List<Aproximation> aproximations = new ArrayList<>();

    @FXML AddRecordController addRecordController;
    @FXML CreateAproximationController createAproximationController;

    @FXML private VBox vBoxMaterials;
    @FXML private VBox vBoxServices;
    @FXML private VBox vBoxHistory;
    @FXML private Button btnAddMaterial;
    @FXML private Button btnAddService;
    @FXML private TabPane tabAproximations;

    /*
    @param: String recordType
    @return: void
    Loads the addRecordPopUp and inits the addRecordController
     */
    public void popUpAddRecord(String recordType) throws IOException {

        FXMLLoader loader = new FXMLLoader(App.class.getResource("addRecordPopUp.fxml"));

        Parent root = loader.load();
        Scene addRecordScene = new Scene(root);
        Stage addStage = new Stage();
        addStage.setScene(addRecordScene);
        addStage.initModality(Modality.NONE);
        addStage.show();

        addRecordController = loader.getController();
        addRecordController.init(this, recordType);

    }

    /*
    @param: None
    @return: void
    Loads the createAproximationPopUp and inits the createAproximationController
     */
    public void createNewAproximation() throws IOException {

        FXMLLoader loader = new FXMLLoader(App.class.getResource("createAproximationPopUp.fxml"));

        Parent root = loader.load();
        Scene createAproximationScene = new Scene(root);
        Stage createAproximationStage = new Stage();
        createAproximationStage.setScene(createAproximationScene);
        createAproximationStage.initModality(Modality.NONE);
        createAproximationStage.show();

        createAproximationController = loader.getController();
        createAproximationController.init(this);
    }

    public void initConnection(Connector connector){
        this.connector = connector;
    }

    @Override
     public void initialize(URL location, ResourceBundle resources) {

        btnAddMaterial.setOnAction(event -> {
            try {
                popUpAddRecord("material");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnAddService.setOnAction(event -> {
            try {
                popUpAddRecord("service");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public VBox getvBoxMaterials() {
        return vBoxMaterials;
    }

    public VBox getvBoxServices() {
        return vBoxServices;
    }

    public TabPane getTabAproximations() {
        return tabAproximations;
    }

    public Materials getMaterials() {
        return materials;
    }

    public Services getServices() {
        return services;
    }

    public List<Aproximation> getAproximations() {
        return aproximations;
    }

    public History getHistory() {
        return history;
    }

    public VBox getvBoxHistory() {
        return vBoxHistory;
    }

    public void setvBoxHistory(VBox vBoxHistory) {
        this.vBoxHistory = vBoxHistory;
    }

    public Connector getConnector() {
        return connector;
    }
}
