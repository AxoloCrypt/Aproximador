package com.aproximador.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Record <T>
{
    private String name;
    private BigDecimal unitCost;
    private String description;
    private List<T> records;


    public Record(String name, BigDecimal unitCost, String description)
    {
        this.name = name;
        this.unitCost = unitCost;
        this.description = description;
        records = new ArrayList<>();
    }

    public Record() {
        records = new ArrayList<>();
    }


    public boolean addRecord(T record) throws NullPointerException{

        if (record == null)
            throw new NullPointerException();

        return records.add(record);
    }

    public boolean deleteRecord(T record){

        return records.remove(record);
    }

    public void editRecord(String name, BigDecimal unitCost, String description){

        Record<T> tmpRecord = new Record<T>(name, unitCost, description);

        for (T record : records){

            if(record.equals(tmpRecord)){

                setName(tmpRecord.getName());
                setUnitCost(tmpRecord.unitCost);
                setDescription(description);
            }
        }
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record<?> record = (Record<?>) o;
        return getName().equals(record.getName()) && getUnitCost().equals(record.getUnitCost()) && getDescription().equals(record.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getUnitCost(), getDescription());
    }
}
