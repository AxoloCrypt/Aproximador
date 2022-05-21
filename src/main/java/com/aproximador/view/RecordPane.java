package com.aproximador.view;

import javafx.geometry.Pos;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;

public class RecordPane extends DialogPane {

    private Label lblCost;

    public RecordPane(String recordName, String cost, String description){

        this.setHeader(new Label(recordName));

        lblCost = new Label("$" + cost);
        lblCost.setAlignment(Pos.BOTTOM_RIGHT);

        this.setContent(lblCost);
    }


}
