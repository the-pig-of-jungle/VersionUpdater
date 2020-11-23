package com.coder.zzq.versionupdaterlib.bean.download_event;

import com.coder.zzq.versionupdaterlib.bean.ApkInstaller;
import com.coder.zzq.versionupdaterlib.bean.DownloadProgress;

public class DownloadInProgress extends DownloadEvent {
    private final DownloadProgress mDownloadProgress;
    private final ApkInstaller mApkInstaller;

    public DownloadInProgress(DownloadProgress downloadProgress, ApkInstaller apkInstaller) {
        mDownloadProgress = downloadProgress;
        mApkInstaller = apkInstaller;
    }

    public DownloadProgress getDownloadProgress() {
        return mDownloadProgress;
    }

    public ApkInstaller getApkInstaller() {
        return mApkInstaller;
    }
}