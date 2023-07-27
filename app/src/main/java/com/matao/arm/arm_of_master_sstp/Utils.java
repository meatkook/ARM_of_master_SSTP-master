package com.matao.arm.arm_of_master_sstp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Utils {

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static Calendar buildCalendar(int year, int month, int dayOfMonth) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return calendar;
    }
}
