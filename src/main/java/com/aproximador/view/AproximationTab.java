package com.aproximador.view;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class AproximationTab extends Tab
{
    private SplitPane splitPane;
    private ScrollPane scrollPane;
    private VBox vBoxRecords;
    private VBox vBoxResult;
    private ToolBar toolBar;

    public AproximationTab(String tabName, Button btnSave){
        super(tabName);

        vBoxRecords = new VBox();

        toolBar = new ToolBar();

        scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToWidth(true);

        vBoxResult = new VBox(25);
        vBoxResult.getChildren().add(new Label("Number of Used Materials: "));
        vBoxResult.getChildren().add(new Label("Number of Used Services: "));
        vBoxResult.getChildren().add(new Label("Total: "));

        btnSave.setAlignment(Pos.BOTTOM_RIGHT);


        toolBar.getItems().add(btnSave);

        scrollPane.setContent(vBoxRecords);

        splitPane = new SplitPane(scrollPane, vBoxResult);

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
