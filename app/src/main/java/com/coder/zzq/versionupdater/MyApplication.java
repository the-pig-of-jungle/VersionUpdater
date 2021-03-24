package com.coder.zzq.versionupdater;

import android.app.Application;

import com.coder.zzq.smartshow.core.SmartShow;
import com.coder.zzq.version_updater.VersionUpdaterInitializer;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SmartShow.init(this);
        VersionUpdaterInitializer.initialize(this);
    }
}
