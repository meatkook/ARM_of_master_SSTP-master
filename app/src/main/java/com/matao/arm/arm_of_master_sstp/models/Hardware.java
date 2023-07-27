package com.matao.arm.arm_of_master_sstp.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DataType;

import java.util.Date;

@DatabaseTable(tableName = "hardware")
public class Hardware {

    public static final String PLAN_DATE = "plan_date_maintest";
    public static final String LPS_ID = "LPS_id";
    public static final String NAME = "name";

    @DatabaseField(generatedId = true, columnName = "_id")
    private Integer id;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "name")
    private String name;

    @DatabaseField(dataType = DataType.STRING, columnName = PLAN_DATE)
    private String dateMaintest;

    @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = "LPS_id")
    private int lpsID;

    @DatabaseField(foreign = true, foreignColumnName = "_id", columnName = "LPS_id")
    private LPS lps;

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

    public String getDateMaintest() {
        return dateMaintest;
    }

    public void setDateMaintest(String dateMaintest) {
        this.dateMaintest = dateMaintest;
    }

    public int getLpsID() {
        return lpsID;
    }

    public void setLpsID(Integer lpsID) {
        this.lpsID = lpsID;
    }

    public LPS getLps() {
        return lps;
    }

    public void setLps(LPS lps) {
        this.lps = lps;
    }

    @Override
    public String toString() {
        return name;
    }
}
