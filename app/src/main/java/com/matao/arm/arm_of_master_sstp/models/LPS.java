package com.matao.arm.arm_of_master_sstp.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "LPS")
public class LPS {

    public static final String NAME = "name";

    @DatabaseField(generatedId = true, columnName = "_id")
    private Integer id;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "name")
    private String name;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "address")
    private String address;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "stock_number")
    private String stockNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(String stockNumber) {
        this.stockNumber = stockNumber;
    }

    @Override
    public String toString() {
        return name + ' ' + address;
    }
}
