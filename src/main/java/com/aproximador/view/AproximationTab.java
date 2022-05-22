package com.aproximador.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

public class AproximationTab extends Tab
{
    private SplitPane splitPane;
    private VBox vBoxRecords;
    private VBox vBoxResult;

    public AproximationTab(String tabName, Button btnSave){
        super(tabName);

        splitPane = new SplitPane();

        vBoxRecords = new VBox();

        vBoxResult = new VBox();
        vBoxResult.getChildren().add(new Label("Number of Used Materials: "));
        vBoxResult.getChildren().add(new Label("Number of Used Services: "));
        vBoxResult.getChildren().add(new Label("Total: "));

        btnSave.setAlignment(Pos.BOTTOM_RIGHT);

        vBoxResult.getChildren().add(btnSave);

        splitPane.getItems().add(vBoxRecords);
        splitPane.getItems().add(vBoxResult);
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
