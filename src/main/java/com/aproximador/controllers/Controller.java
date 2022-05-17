package com.aproximador.controllers;

import com.aproximador.data.*;
import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Controller
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

}
