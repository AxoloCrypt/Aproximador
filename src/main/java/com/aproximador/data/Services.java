package com.aproximador.data;

import java.math.BigDecimal;

public class Services extends Record<Services>{

    public Services() {}

    public Services(String name, BigDecimal unitCost, String description) {
        super(name, unitCost, description);
    }

    @Override
    public void editRecord(String name, BigDecimal unitCost, String description, String newName, BigDecimal newUnitCost, String newDescription) {

        Services tmpService = new Services(name, unitCost, description);

        for (Services service : super.getRecords()){

            if (tmpService.equals(service)){

                service.setName(newName);
                service.setUnitCost(newUnitCost);
                service.setDescription(newDescription);

                return;
            }

        }
    }

}
