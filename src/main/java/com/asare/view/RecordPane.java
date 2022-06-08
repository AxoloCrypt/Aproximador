package com.asare.view;

import com.asare.controllers.Controller;
import com.asare.data.Materials;
import com.asare.data.Services;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;

public class RecordPane extends DialogPane {

    private Label lblCost;
    private Label lblDescription;
    private Label lblName;

    // Attributes for panes in aproximations
    private Button btnRemove;
    private Button btnAdd;
    private TextField txtQuantity;
    private String description;
    private BigDecimal originalCost;
    private BigDecimal currentCost;


    // For record section
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

            VBox vBoxRecords = (VBox) node.lookup("VBox"); //Search for vBoxRecords on the current tab

            vBoxRecords.getChildren().add(new RecordPane(lblName.getText(), new BigDecimal(lblCost.getText().replace("$", "")), lblDescription.getText(), controller, isMaterial));

            if (isMaterial)
                controller.getAproximations().get(tabIndex).getRecords().add(new Materials(lblName.getText(),
                        new BigDecimal(lblCost.getText().replace("$", "")), lblDescription.getText(), 1));
            else
                controller.getAproximations().get(tabIndex).getRecords().add(new Services(lblName.getText(),
                        new BigDecimal(lblCost.getText().replace("$", "")), lblDescription.getText(), 1));
        });

        this.setOnMouseEntered(event -> {
            this.setStyle("-fx-background-color: black");
        });

        this.setOnMouseExited(event -> {
            this.setStyle("-fx-background-color: white");
        });

    }

    //For aproximations pane
    public RecordPane(String recordName, BigDecimal cost, String description, Controller controller, boolean isMaterial){
        this.setHeader(new Label(recordName));
        originalCost = cost;
        currentCost = originalCost; //Initialize
        this.description = description;

        btnRemove = new Button("R");
        btnRemove.setOnAction(event -> {

            int tabIndex = controller.getTabAproximations().getSelectionModel().getSelectedIndex();
            int currentAmount = Integer.parseInt(txtQuantity.getText());

            currentAmount = currentAmount > 1 ? currentAmount - 1 : 1;

            //If true, searches the index of the selected material to subtract the original cost on the current approximation
            if(currentAmount != 1){

                if(isMaterial){
                    int selectedMaterial = controller.getAproximations().get(tabIndex).getRecords().indexOf(new Materials(recordName,
                            currentCost, this.description));

                    currentCost = currentCost.subtract(originalCost);

                    controller.getAproximations().get(tabIndex).getRecords().get(selectedMaterial).setUnitCost(currentCost);
                    controller.getAproximations().get(tabIndex).getRecords().get(selectedMaterial).setAmount(currentAmount);
                }
                else{
                    int selectedService = controller.getAproximations().get(tabIndex).getRecords().indexOf(new Services(recordName,
                            currentCost, this.description));

                    currentCost = currentCost.subtract(originalCost);

                    controller.getAproximations().get(tabIndex).getRecords().get(selectedService).setUnitCost(currentCost);
                    controller.getAproximations().get(tabIndex).getRecords().get(selectedService).setAmount(currentAmount);
                }
            }
            else{
                //When current amount == 1 makes a last calculation and disables the button to avoid negatives

                btnRemove.setDisable(true);

                if(isMaterial){
                    int selectedMaterial = controller.getAproximations().get(tabIndex).getRecords().indexOf(new Materials(recordName,
                            currentCost, this.description));

                    currentCost = currentCost.subtract(originalCost);

                    controller.getAproximations().get(tabIndex).getRecords().get(selectedMaterial).setUnitCost(currentCost);
                    controller.getAproximations().get(tabIndex).getRecords().get(selectedMaterial).setAmount(currentAmount);
                }
                else{
                    int selectedService = controller.getAproximations().get(tabIndex).getRecords().indexOf(new Services(recordName,
                            currentCost, this.description));

                    currentCost = currentCost.subtract(originalCost);

                    controller.getAproximations().get(tabIndex).getRecords().get(selectedService).setUnitCost(currentCost);
                    controller.getAproximations().get(tabIndex).getRecords().get(selectedService).setAmount(currentAmount);
                }
            }
            txtQuantity.setText(String.valueOf(currentAmount));
        });
        btnRemove.setDisable(true);

        txtQuantity = new TextField("1");
        txtQuantity.setMaxSize(50, 10);
        txtQuantity.setFocusTraversable(false);

        btnAdd = new Button("Add");
        btnAdd.setOnAction(event -> {
            int tabIndex = controller.getTabAproximations().getSelectionModel().getSelectedIndex();
            int currentAmount = Integer.parseInt(txtQuantity.getText());

            currentAmount += 1;

            if (btnRemove.isDisable())
                btnRemove.setDisable(false);

            //Same shit as above but for adding the original cost to the selected material

            if(isMaterial){
                int selectedMaterial = controller.getAproximations().get(tabIndex).getRecords().indexOf(new Materials(recordName,
                        currentCost, this.description));

                currentCost = currentCost.add(originalCost);

                controller.getAproximations().get(tabIndex).getRecords().get(selectedMaterial).setUnitCost(currentCost);
                controller.getAproximations().get(tabIndex).getRecords().get(selectedMaterial).setAmount(currentAmount);
            }
            else{
                int selectedService = controller.getAproximations().get(tabIndex).getRecords().indexOf(new Services(recordName,
                        currentCost, this.description));

                currentCost = currentCost.add(originalCost);

                controller.getAproximations().get(tabIndex).getRecords().get(selectedService).setUnitCost(currentCost);
                controller.getAproximations().get(tabIndex).getRecords().get(selectedService).setAmount(currentAmount);
            }
            txtQuantity.setText(String.valueOf(currentAmount));
        });

        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.CENTER_RIGHT);

        flowPane.getChildren().add(btnRemove);
        flowPane.getChildren().add(txtQuantity);
        flowPane.getChildren().add(btnAdd);

        this.setContent(flowPane);
    }

    public RecordPane(String name, int amount){
        this.setHeader(new Label(name));

        Label lblAmount = new Label("Amount: " + amount);
        lblAmount.setAlignment(Pos.BOTTOM_RIGHT);

        this.setContent(lblAmount);
    }


}
