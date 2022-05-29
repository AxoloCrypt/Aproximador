package com.aproximador.data;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    private LocalDate dateCreation;

    public Aproximation(){
        totalCost = new BigDecimal("0.00");
        numberMaterials = 0;
        numberServices = 0;
        records = new ArrayList<>();
    }

    public Aproximation(String name){
        this.name = name;
        totalCost = new BigDecimal("0.00");
        numberMaterials = 0;
        numberServices = 0;
        records = new ArrayList<>();
    }

    public Aproximation(Aproximation aproximation, LocalDate dateCreation){
        this.name = aproximation.getName();
        this.records = aproximation.getRecords();
        this.totalCost = aproximation.getTotalCost();
        this.numberMaterials = aproximation.getNumberMaterials();
        this.numberServices = aproximation.getNumberServices();
        this.dateCreation = dateCreation;
    }

    public Aproximation(String name, BigDecimal totalCost, int numberMaterials,
                        int numberServices, LocalDate dateCreation){
        this.name = name;
        this.totalCost = totalCost;
        this.numberMaterials = numberMaterials;
        this.numberServices = numberServices;
        this.dateCreation = dateCreation;
    }

    /*
    @param none
    @return BigDecimal
    Calculates the total of the aproximation and the number of materials and services
    by iterating through records list.
     */
    public BigDecimal calculateTotal(){

        BigDecimal total = new BigDecimal("0.00");
        int numberMaterials = 0, numberServices = 0;

        for (Record<?> record : records) {

            if (record instanceof Materials)
                numberMaterials++;
            else if (record instanceof Services)
                numberServices++;

            total = total.add(record.getUnitCost());
        }

        setTotalCost(total);
        setNumberMaterials(numberMaterials);
        setNumberServices(numberServices);

        return total;
    }

    //Setter & Getters

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

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }
}
