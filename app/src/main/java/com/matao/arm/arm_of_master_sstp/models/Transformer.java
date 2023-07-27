package com.matao.arm.arm_of_master_sstp.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "transformers")
public class Transformer {

    public static final String PLAN_DATE = "next_verification";
    public static final String LPS_ID = "LPS_id";
    public static final String TYPE_ID = "transformers_types_id";
    public static final String NAME = "name";

    @DatabaseField(generatedId = true, columnName = "_id")
    private Integer id;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "name")
    private String name;

    @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = "transformers_types_id")
    private int typeID;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "ratio")
    private String ratio;

    @DatabaseField(canBeNull = true, dataType = DataType.STRING, columnName = "next_verification")
    private String nextVerification;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "nomber")
    private String number;

    @DatabaseField(canBeNull = true, dataType = DataType.STRING, columnName = "placement")
    private String placement;

    @DatabaseField(canBeNull = true, dataType = DataType.INTEGER, columnName = "LPS_id")
    private int lpsID;

    @DatabaseField(foreign = true, foreignColumnName = "_id", columnName = "LPS_id")
    private LPS lps;

    @DatabaseField(foreign = true, foreignColumnName = "_id", columnName = "transformers_types_id")
    private TransformerTypes type;

    public static String getPlanDate() {
        return PLAN_DATE;
    }

    public static String getLpsId() {
        return LPS_ID;
    }

    public static String getTypeId() {
        return TYPE_ID;
    }

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

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getNextVerification() {
        return nextVerification;
    }

    public void setNextVerification(String nextVerification) {
        this.nextVerification = nextVerification;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPlacement() {
        return placement;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }

    public int getLpsID() {
        return lpsID;
    }

    public void setLpsID(int lpsID) {
        this.lpsID = lpsID;
    }

    public LPS getLps() {
        return lps;
    }

    public void setLps(LPS lps) {
        this.lps = lps;
    }

    public TransformerTypes getType() {
        return type;
    }

    public void setType(TransformerTypes type) {
        this.type = type;
    }
}
