package com.aproximador.view;

import com.aproximador.controllers.Controller;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class RecordPane extends DialogPane {

    private Label lblCost;
    private Label lblDescription;

    public RecordPane(String recordName, String cost, String description, Controller controller){

        this.setHeader(new Label(recordName));

        lblCost = new Label("$" + cost);
        lblCost.setAlignment(Pos.BASELINE_RIGHT);

        lblDescription = new Label(description);
        lblDescription.setAlignment(Pos.BOTTOM_LEFT);

        FlowPane flowPane = new FlowPane();
        flowPane.getChildren().add(lblCost);
        flowPane.getChildren().add(lblDescription);

        this.setContent(flowPane);

        this.setOnMouseClicked(event -> {
            Tab currentAproximationTab = controller.getTabAproximations().getSelectionModel().getSelectedItem();

            Node node = currentAproximationTab.getContent();

            VBox vBoxRecords = (VBox) node.lookup("VBox");

            vBoxRecords.getChildren().add(new RecordPane(this.getHeaderText(), lblCost.getText(), lblDescription.getText()));

        });

        this.setOnMouseEntered(event -> {
            this.setStyle("-fx-background-color: black");
        });

        this.setOnMouseExited(event -> {
            this.setStyle("-fx-background-color: white");
        });

    }

    public RecordPane(String recordName, String cost, String description){
        this.setHeader(new Label(recordName));

        lblCost = new Label(cost);
        lblCost.setAlignment(Pos.BASELINE_RIGHT);

        lblDescription = new Label(description);
        lblDescription.setAlignment(Pos.BOTTOM_LEFT);

        FlowPane flowPane = new FlowPane();
        flowPane.getChildren().add(lblCost);
        flowPane.getChildren().add(lblDescription);

        this.setContent(flowPane);
    }

}
