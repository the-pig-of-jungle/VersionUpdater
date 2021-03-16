package com.coder.zzq.version_updater.bean.download_event;

import com.coder.zzq.version_updater.bean.ApkInstaller;
import com.coder.zzq.version_updater.bean.ReadableVersionInfo;

public class NewVersionApkExists extends DownloadEvent {
    private final ReadableVersionInfo mVersionInfo;
    private final ApkInstaller mApkInstaller;

    public NewVersionApkExists(ReadableVersionInfo newVersionInfo, ApkInstaller apkInstaller) {
        mVersionInfo = newVersionInfo;
        mApkInstaller = apkInstaller;
    }

    public ApkInstaller getApkInstaller() {
        return mApkInstaller;
    }

    public ReadableVersionInfo getNewVersionInfo() {
        return mVersionInfo;
    }
}
