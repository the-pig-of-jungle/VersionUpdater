package com.coder.zzq.version_updater.bean.update_event;

import com.coder.zzq.version_updater.bean.ReadableVersionInfo;
import com.coder.zzq.version_updater.bean.download_trigger.DownloadTrigger;

public class DetectNewVersion extends VersionUpdateEvent {
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