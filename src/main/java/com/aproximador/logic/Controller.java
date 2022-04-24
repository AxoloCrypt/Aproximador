package com.aproximador.logic;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller
{
    @FXML
    private Button btnExecuteQuery;
    @FXML
    private Label lblQueryResult;

    private Connector connector = new Connector("juca", "g*$0Pe$h18cyiyJC");

    public void executeQuery(){
        lblQueryResult.setText(connector.showDataFromTable("tabla_prueba"));
    }

}
