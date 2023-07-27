package com.matao.arm.arm_of_master_sstp.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "human_resources")
public class Staff {

    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String PATRONOMIC = "patronymic";
    public static final String BIRTHDAY = "birthday";
    public static final String TIMECARD = "timecard";
    public static final String HOME_PHONE = "home_phone";
    public static final String MOBILE_PHONE1 = "mobile_phone1";
    public static final String MOBILE_PHONE2 = "mobile_phone2";

    @DatabaseField(generatedId = true, columnName = "_id")
    private Integer id;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "name")
    private String name;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "surname")
    private String surname;

    @DatabaseField(canBeNull = true, dataType = DataType.STRING, columnName = "patronymic")
    private String patronymic;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "birthday")
    private String birthday;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "timecard")
    private String timecard;

    @DatabaseField(canBeNull = true, dataType = DataType.STRING, columnName = "home_phone")
    private String homePhone;

    @DatabaseField(canBeNull = true, dataType = DataType.STRING, columnName = "mobile_phone1")
    private String mobilePhoneOne;

    @DatabaseField(canBeNull = true, dataType = DataType.STRING, columnName = "mobile_phone2")
    private String mobilePhoneTwo;

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getTimecard() {
        return timecard;
    }

    public void setTimecard(String timecard) {
        this.timecard = timecard;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getMobilePhoneOne() {
        return mobilePhoneOne;
    }

    public void setMobilePhoneOne(String mobilePhoneOne) {
        this.mobilePhoneOne = mobilePhoneOne;
    }

    public String getMobilePhoneTwo() {
        return mobilePhoneTwo;
    }

    public void setMobilePhoneTwo(String mobilePhoneTwo) {
        this.mobilePhoneTwo = mobilePhoneTwo;
    }

    @Override
    public String toString() {
        return surname + ' ' + name + ' ' + patronymic;
    }
}
