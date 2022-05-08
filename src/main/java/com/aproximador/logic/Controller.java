package com.aproximador.logic;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller
{
    @FXML
    private Button btnExecuteQuery1;
    @FXML
    private Label lblQueryResult1;
    @FXML
    private Label lblQueryResult2;


    private Connector connector = new Connector("juca", "g*$0Pe$h18cyiyJC");

    public void executeQuery(){
        lblQueryResult1.setText(connector.showDataFromTable("users"));
        System.out.println(lblQueryResult1.getText());
        lblQueryResult2.setText(connector.makeQuery("SELECT aproximations.idAprox,aproximations.totalCost FROM aproximations INNER JOIN users ON aproximations.idAprox = users.idAprox LIMIT 3;"));
    }

}
