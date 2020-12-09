package com.coder.zzq.versionupdater;

import android.app.Application;

import com.coder.zzq.versionupdaterlib.AppUpdaterInitializer;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppUpdaterInitializer.initialize(this);
    }
}
