package com.coder.zzq.versionupdater;

import android.app.Application;

import com.coder.zzq.version_updater.VersionUpdaterInitializer;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        VersionUpdaterInitializer.initialize(this);
    }
}
