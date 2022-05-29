package com.aproximador.view;


import com.aproximador.controllers.Controller;
import com.aproximador.data.Aproximation;
import com.aproximador.data.History;
import javafx.geometry.Pos;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class AproximationPane extends DialogPane {

    private final LocalDateTime dateCreation;

    public AproximationPane(String name, LocalDateTime dateCreation, Controller controller) {

        this.dateCreation = dateCreation;

        Label lblName = new Label(name);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Label lblDate = new Label(sdf.format(dateCreation));
        lblDate.setAlignment(Pos.CENTER_RIGHT);

        this.setHeader(lblName);
        this.setContent(lblDate);

        this.setOnMouseClicked(event -> {

            for (Aproximation aproximation : controller.getHistory().getSavedAproximations()){

                if (aproximation.getDateCreation().isEqual(this.dateCreation)){

                    AproximationTab aproximationTab = new AproximationTab(aproximation.getName(),
                            aproximation.getNumberMaterials(), aproximation.getNumberServices(),aproximation.getTotalCost().toString(), aproximation.getRecords());

                    controller.getTabAproximations().getTabs().add(aproximationTab);
                }

            }

        });
    }

}
