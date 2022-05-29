package com.aproximador.view;

import com.aproximador.controllers.Controller;
import com.aproximador.data.Aproximation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class AproximationTab extends Tab
{
    private SplitPane splitPane;
    private ScrollPane scrollPane;
    private VBox vBoxRecords;
    private VBox vBoxResult;
    private ToolBar toolBar;
    private Button btnSave, btnCalculate;
    private Label lblUsedMaterials, lblUsedServices, lblTotal;

    private String tabname;

    public AproximationTab(String tabName, Controller controller){
        super(tabName);
        this.tabname = tabName;

        vBoxRecords = new VBox();

        toolBar = new ToolBar();

        scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToWidth(true);

        lblUsedMaterials = new Label("Used Materials: ");
        lblUsedServices = new Label("Used Services: ");
        lblTotal = new Label("Total: ");

        vBoxResult = new VBox(25);
        vBoxResult.getChildren().add(lblUsedMaterials);
        vBoxResult.getChildren().add(lblUsedServices);
        vBoxResult.getChildren().add(lblTotal);

        btnCalculate = new Button("Calculate Total");
        btnCalculate.setOnAction(event -> {
            int tabIndex = controller.getTabAproximations().getSelectionModel().getSelectedIndex();

            Aproximation aproximation = controller.getAproximations().get(tabIndex);

            BigDecimal totalCost = aproximation.calculateTotal();

            lblTotal.setText("Total " + totalCost.toString());
            lblUsedMaterials.setText("Used Materials: " + aproximation.getNumberMaterials());
            lblUsedServices.setText("Used Services: " + aproximation.getNumberServices());

        });

        btnSave = new Button("Save");
        btnSave.setAlignment(Pos.BOTTOM_RIGHT);
        btnSave.setOnAction(event -> {

            int tabIndex = controller.getTabAproximations().getSelectionModel().getSelectedIndex();

            Aproximation currentAproximation = controller.getAproximations().get(tabIndex);

            Aproximation savedAproximation = new Aproximation(currentAproximation, LocalDate.now());

            controller.getHistory().addToHistory(savedAproximation);
            controller.getvBoxHistory().getChildren().add(new AproximationPane(savedAproximation.getName(), savedAproximation.getDateCreation(), controller));
        });

        toolBar.getItems().add(btnSave);
        toolBar.getItems().add(btnCalculate);

        vBoxResult.getChildren().add(toolBar);

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
