package com.aproximador.view;


import com.aproximador.controllers.Controller;
import javafx.geometry.Pos;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.util.Date;

public class AproximationPane extends DialogPane {
    private Label lblName;
    private Label lblDate;

    public AproximationPane(String name, LocalDate dateCreation, Controller controller) {

        lblName = new Label(name);

        lblDate = new Label(dateCreation.toString());
        lblDate.setAlignment(Pos.CENTER_RIGHT);

        this.setHeader(lblName);
        this.setContent(lblDate);

        this.setOnMouseClicked(event -> {

        });

    }

}
