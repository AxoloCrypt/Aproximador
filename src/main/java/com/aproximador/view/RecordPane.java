package com.aproximador.view;

import com.aproximador.controllers.Controller;
import com.aproximador.data.Materials;
import com.aproximador.data.Services;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.math.BigDecimal;

public class RecordPane extends DialogPane {

    private Label lblCost;
    private Label lblDescription;
    private Label lblName;

    // Attributes for panes in aproximations
    private Button btnRemove;
    private Button btnAdd;
    private TextField txtQuantity;

    public RecordPane(String recordName, String cost, String description, Controller controller, boolean isMaterial){

        lblName = new Label(recordName);

        lblCost = new Label("$" + cost);
        lblCost.setAlignment(Pos.CENTER_RIGHT);

        lblDescription = new Label(description);
        lblDescription.setAlignment(Pos.CENTER_LEFT);
        lblDescription.setTextOverrun(OverrunStyle.ELLIPSIS);
        lblDescription.setEllipsisString("...");

        HBox hBox = new HBox();
        hBox.getChildren().add(lblDescription);
        hBox.getChildren().add(lblCost);

        this.setHeader(lblName);
        this.setContent(hBox);

        this.setOnMouseClicked(event -> {
            Tab currentAproximationTab = controller.getTabAproximations().getSelectionModel().getSelectedItem();
            int tabIndex = controller.getTabAproximations().getSelectionModel().getSelectedIndex();


            Node node = currentAproximationTab.getContent();

            VBox vBoxRecords = (VBox) node.lookup("VBox");

            vBoxRecords.getChildren().add(new RecordPane(lblName.getText()));

            if (isMaterial)
                controller.getAproximations().get(tabIndex).getRecords().add(new Materials(lblName.getText(),
                        new BigDecimal(lblCost.getText()), lblDescription.getText()));
            else
                controller.getAproximations().get(tabIndex).getRecords().add(new Services(lblName.getText(),
                        new BigDecimal(lblCost.getText()), lblDescription.getText()));
        });

        this.setOnMouseEntered(event -> {
            this.setStyle("-fx-background-color: black");
        });

        this.setOnMouseExited(event -> {
            this.setStyle("-fx-background-color: white");
        });

    }

    public RecordPane(String recordName){
        this.setHeader(new Label(recordName));

        btnRemove = new Button("R");

        txtQuantity = new TextField("1");
        txtQuantity.setMaxSize(20, 10);
        txtQuantity.setEditable(false);
        txtQuantity.setFocusTraversable(false);

        btnAdd = new Button("Add");

        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.CENTER_RIGHT);

        flowPane.getChildren().add(btnRemove);
        flowPane.getChildren().add(txtQuantity);
        flowPane.getChildren().add(btnAdd);

        this.setContent(flowPane);
    }

}
