package com.coder.zzq.versionupdater;

import android.app.Application;

import com.coder.zzq.version_updater.AppUpdaterInitializer;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppUpdaterInitializer.initialize(this);
    }
}
