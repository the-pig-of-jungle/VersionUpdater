package com.coder.zzq.version_updater.bean.download_event;

import com.coder.zzq.version_updater.bean.ApkInstaller;
import com.coder.zzq.version_updater.bean.DownloadProgress;
import com.coder.zzq.version_updater.bean.ReadableVersionInfo;

public class DownloadInProgress extends DownloadEvent {
    private final DownloadProgress mDownloadProgress;
    private final ReadableVersionInfo mNewVersionInfo;
    private final ApkInstaller mApkInstaller;

    public DownloadInProgress(DownloadProgress downloadProgress, ReadableVersionInfo newVersionInfo, ApkInstaller apkInstaller) {
        mDownloadProgress = downloadProgress;
        mNewVersionInfo = newVersionInfo;
        mApkInstaller = apkInstaller;
    }

    public DownloadProgress getDownloadProgress() {
        return mDownloadProgress;
    }

    public ApkInstaller getApkInstaller() {
        return mApkInstaller;
    }

    public ReadableVersionInfo getNewVersionInfo() {
        return mNewVersionInfo;
    }
}