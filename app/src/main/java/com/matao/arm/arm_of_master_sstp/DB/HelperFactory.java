package com.matao.arm.arm_of_master_sstp.DB;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.text.SimpleDateFormat;

public class HelperFactory {

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static DatabaseHelper databaseHelper;


    public static DatabaseHelper getHelper() {
        return databaseHelper;
    }

    public static void setHelper(Context context) {
        databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
    }

    public static void releaseHelper() {
        OpenHelperManager.releaseHelper();
        databaseHelper = null;
    }
}