package com.coder.zzq.versionupdaterlib.bean.download_event;

import com.coder.zzq.versionupdaterlib.bean.ReadableVersionInfo;
import com.coder.zzq.versionupdaterlib.bean.download_trigger.DownloadTrigger;

public class DetectNewVersion extends DownloadEvent {
    private final ReadableVersionInfo mNewVersionInfo;
    private final DownloadTrigger mDownloadTrigger;

    public DetectNewVersion(ReadableVersionInfo newVersionInfo, DownloadTrigger downloadTrigger) {
        mNewVersionInfo = newVersionInfo;
        mDownloadTrigger = downloadTrigger;
    }

    public ReadableVersionInfo getNewVersionInfo() {
        return mNewVersionInfo;
    }

    public DownloadTrigger getDownloadTrigger() {
        return mDownloadTrigger;
    }
}