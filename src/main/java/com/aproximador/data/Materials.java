package com.aproximador.data;

import java.math.BigDecimal;

public class Materials extends Record<Materials>{

    public Materials() {}

    public Materials(String name, BigDecimal unitCost, String description) {
        super(name, unitCost, description);
    }

    @Override
    public void editRecord(String name, BigDecimal unitCost, String description, String newName, BigDecimal newUnitCost, String newDescription) {

        Materials tmpMaterial = new Materials(name, unitCost, description);

        for (Materials material : super.getRecords()){

            if (tmpMaterial.equals(material)){
                material.setName(newName);
                material.setUnitCost(newUnitCost);
                material.setDescription(newDescription);

                return;
            }
        }
    }

}
