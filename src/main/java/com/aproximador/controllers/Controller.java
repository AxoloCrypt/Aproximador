package com.aproximador.controllers;

import com.aproximador.data.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    private final Materials materials = new Materials();
    private final Services services = new Services();
    private final Connector connector = new Connector("juca", "g*$0Pe$h18cyiyJC");

    @FXML
    private VBox vBoxMaterials;

    public void addMaterial(){
        DialogPane dialogPane = new DialogPane();
        dialogPane.getChildren().add(new Label("Huevos"));
        dialogPane.setHeader(new Label("Huevos Header"));


        vBoxMaterials.getChildren().add(dialogPane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
