package com.matao.arm.arm_of_master_sstp.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "history_hardware")
public class HardwareHistory {

    public static final String PLAN_DATE = "date_maintenance";

    @DatabaseField(generatedId = true, columnName = "_id")
    private Integer id;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "date_maintenance")
    private String dateMaintest;

    @DatabaseField(foreign = true, foreignColumnName = "_id", columnName = "hardware_id")
    private Hardware hardware;

    @DatabaseField(foreign = true, foreignColumnName = "_id", columnName = "person")
    private Staff staff;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDateMaintest() {
        return dateMaintest;
    }

    public void setDateMaintest(String dateMaintest) {
        this.dateMaintest = dateMaintest;
    }

    public Hardware getHardware() {
        return hardware;
    }

    public void setHardware(Hardware hardware) {
        this.hardware = hardware;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Staff getStaff() {
        return staff;
    }
}
