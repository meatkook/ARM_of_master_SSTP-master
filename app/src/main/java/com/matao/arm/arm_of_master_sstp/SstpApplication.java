package com.matao.arm.arm_of_master_sstp;

import android.app.Application;

import com.matao.arm.arm_of_master_sstp.DB.HelperFactory;

public class SstpApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HelperFactory.setHelper(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        HelperFactory.releaseHelper();
        super.onTerminate();
    }
}