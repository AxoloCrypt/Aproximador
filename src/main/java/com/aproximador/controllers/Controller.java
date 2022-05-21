package com.aproximador.controllers;

import com.aproximador.app.App;
import com.aproximador.data.*;
import com.aproximador.view.RecordPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private VBox vBoxMaterials;

    public void addRecord() throws IOException {

        Parent root = FXMLLoader.load(App.class.getResource("addRecordPopUp.fxml"));

        Scene addRecordScene = new Scene(root);
        Stage addStage = new Stage();
        addStage.setScene(addRecordScene);
        addStage.initModality(Modality.NONE);
        addStage.show();

        RecordPane recordPane = new RecordPane("Huevos", "10.00", "");


        vBoxMaterials.getChildren().add(recordPane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
