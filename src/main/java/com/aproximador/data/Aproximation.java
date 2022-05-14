package com.aproximador.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Aproximation
{
    private String name;
    private List<Record<?>> records;
    private BigDecimal totalCost;
    private int numberMaterials;
    private int numberServices;
    private String description;
    private Date date;

    public Aproximation(){
        totalCost = new BigDecimal("0.00");
        records = new ArrayList<>();
    }

    public Aproximation(String name, BigDecimal totalCost, int numberMaterials,
                        int numberServices, String description, Date date){
        this.name = name;
        this.totalCost = totalCost;
        this.numberMaterials = numberMaterials;
        this.numberServices = numberServices;
        this.description = description;
        this.date = date;
    }

    private BigDecimal calculateTtotal(){

        BigDecimal total = new BigDecimal("0.00");

        for (Record<?> record : records) {
            total.add(record.getUnitCost());
        }

        return total;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Record<?>> getRecords() {
        return records;
    }

    public void setRecords(List<Record<?>> records) {
        this.records = records;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public int getNumberMaterials() {
        return numberMaterials;
    }

    public void setNumberMaterials(int numberMaterials) {
        this.numberMaterials = numberMaterials;
    }

    public int getNumberServices() {
        return numberServices;
    }

    public void setNumberServices(int numberServices) {
        this.numberServices = numberServices;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
