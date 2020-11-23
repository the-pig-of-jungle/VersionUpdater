package com.coder.zzq.versionupdaterlib.bean.download.event;

import com.coder.zzq.versionupdaterlib.bean.ApkInstaller;
import com.coder.zzq.versionupdaterlib.bean.VersionInfo;

public class NewVersionApkExists extends DownloadEvent {
    private final VersionInfo mVersionInfo;
    private final ApkInstaller mApkInstaller;

    public NewVersionApkExists(VersionInfo versionInfo, ApkInstaller apkInstaller) {
        mVersionInfo = versionInfo;
        mApkInstaller = apkInstaller;
    }

    public ApkInstaller getApkInstaller() {
        return mApkInstaller;
    }

    public VersionInfo getVersionInfo() {
        return mVersionInfo;
    }
}
