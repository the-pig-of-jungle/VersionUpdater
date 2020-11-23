package com.coder.zzq.versionupdaterlib.bean.download.event;

import android.net.Uri;

import com.coder.zzq.versionupdaterlib.bean.ApkInstaller;

public class DownloadComplete extends DownloadEvent {
    private final Uri mApkUri;
    private final ApkInstaller mApkInstaller;

    public DownloadComplete(Uri apkUri, ApkInstaller apkInstaller) {
        mApkUri = apkUri;
        mApkInstaller = apkInstaller;
    }

    public ApkInstaller getApkInstaller() {
        return mApkInstaller;
    }


    public Uri getApkUri() {
        return mApkUri;
    }


}
