package com.matao.arm.arm_of_master_sstp.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tools")
public class Tool {

    public static final String PLAN_DATE = "next_verification";
    public static final String LPS_ID = "LPS_id";
    public static final String TOOL_NAME = "name";

    @DatabaseField(generatedId = true, columnName = "_id")
    private Integer id;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "name")
    private String name;

    @DatabaseField(canBeNull = true, dataType = DataType.STRING, columnName = "stock_number")
    private String number;

    @DatabaseField(canBeNull = true, dataType = DataType.STRING, columnName = "next_verification")
    private String nextVerification;

    @DatabaseField(canBeNull = true, dataType = DataType.INTEGER, columnName = "LPS_id")
    private int lpsID;

    @DatabaseField(foreign = true, foreignColumnName = "_id", columnName = "LPS_id")
    private LPS lps;


    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLpsID(int lpsID) {
        this.lpsID = lpsID;
    }

    public int getLpsID() {
        return lpsID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNextVerification() {
        return nextVerification;
    }

    public void setNextVerification(String nextVerification) {
        this.nextVerification = nextVerification;
    }

    public LPS getLps() {
        return lps;
    }

    public void setLps(LPS lps) {
        this.lps = lps;
    }
}
