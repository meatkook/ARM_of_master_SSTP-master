package com.matao.arm.arm_of_master_sstp.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "transformers_types")
public class TransformerTypes {
    public static final String NAME = "type";

    @DatabaseField (generatedId = true, columnName = "_id")
    private int id;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "type")
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "трансформатор " + type;
    }
}
