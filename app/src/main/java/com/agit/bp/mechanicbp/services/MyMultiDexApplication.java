package com.agit.bp.mechanicbp.services;

import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by NiyatiR on 8/24/2016.
 */
public class MyMultiDexApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
    }
}
