package com.coder.zzq.versionupdaterlib.bean;

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
