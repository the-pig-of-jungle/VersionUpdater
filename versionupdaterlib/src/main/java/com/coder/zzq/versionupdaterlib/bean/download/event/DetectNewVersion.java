package com.coder.zzq.versionupdaterlib.bean.download.event;

import com.coder.zzq.versionupdaterlib.bean.DownloadTrigger;
import com.coder.zzq.versionupdaterlib.bean.VersionInfo;

public class DetectNewVersion extends DownloadEvent {
    private final VersionInfo mNewVersionInfo;
    private final DownloadTrigger mDownloadTrigger;

    public DetectNewVersion(VersionInfo newVersionInfo, DownloadTrigger downloadTrigger) {
        mNewVersionInfo = newVersionInfo;
        mDownloadTrigger = downloadTrigger;
    }

    public VersionInfo getNewVersionInfo() {
        return mNewVersionInfo;
    }

    public DownloadTrigger getDownloadTrigger() {
        return mDownloadTrigger;
    }
}