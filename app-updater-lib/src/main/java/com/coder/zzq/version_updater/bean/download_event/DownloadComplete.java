package com.coder.zzq.version_updater.bean.download_event;

import android.net.Uri;

import com.coder.zzq.version_updater.bean.ApkInstaller;

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
