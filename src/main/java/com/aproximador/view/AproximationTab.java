package com.aproximador.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class AproximationTab extends Tab
{
    private SplitPane splitPane;
    private VBox vBoxRecords;
    private VBox vBoxResult;

    public AproximationTab(String tabName, Button btnSave){
        super(tabName);

        vBoxRecords = new VBox();

        vBoxResult = new VBox(5);
        vBoxResult.getChildren().add(new Label("Number of Used Materials: "));
        vBoxResult.getChildren().add(new Label("Number of Used Services: "));
        vBoxResult.getChildren().add(new Label("Total: "));

        FlowPane flowPane = new FlowPane();

        btnSave.setAlignment(Pos.BASELINE_RIGHT);

        flowPane.getChildren().add(btnSave);

        vBoxResult.getChildren().add(flowPane);
        splitPane = new SplitPane(vBoxRecords, vBoxResult);

        this.setContent(splitPane);
    }

    public SplitPane getSplitPane() {
        return splitPane;
    }

    public void setSplitPane(SplitPane splitPane) {
        this.splitPane = splitPane;
    }

    public VBox getvBoxRecords() {
        return vBoxRecords;
    }

    public void setvBoxRecords(VBox vBoxRecords) {
        this.vBoxRecords = vBoxRecords;
    }
}
