package com.coder.zzq.version_updater.bean;

import android.content.Intent;

import com.coder.zzq.toolkit.Toolkit;

public class ApkInstaller {
    private final Intent mInstallApkIntent;

    public ApkInstaller(Intent installApkIntent) {
        mInstallApkIntent = installApkIntent;
    }


    public void install() {
        Toolkit.getContext().startActivity(mInstallApkIntent);
    }
}
