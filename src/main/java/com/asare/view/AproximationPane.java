package com.asare.view;


import com.asare.controllers.Controller;
import com.asare.data.Aproximation;
import javafx.geometry.Pos;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class AproximationPane extends DialogPane {

    private final LocalDateTime dateCreation;

    public AproximationPane(String name, LocalDateTime dateCreation, Controller controller) {

        this.dateCreation = dateCreation;

        Label lblName = new Label(name);

        Label lblDate = new Label(DateTimeFormatter.ISO_LOCAL_DATE.format(this.dateCreation));
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
