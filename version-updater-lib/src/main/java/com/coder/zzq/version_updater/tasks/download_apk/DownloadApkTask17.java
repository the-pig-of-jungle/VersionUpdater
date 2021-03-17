package com.coder.zzq.version_updater.tasks.download_apk;

import android.content.Intent;

import com.coder.zzq.toolkit.Toolkit;
import com.coder.zzq.version_updater.bean.RemoteVersion;
import com.coder.zzq.version_updater.bean.download_trigger.DownloadTrigger;
import com.coder.zzq.version_updater.bean.download_trigger.DownloadTrigger17;
import com.coder.zzq.version_updater.service.DownloadApkService17;

public class DownloadApkTask17 extends DownloadApkTask {
    public DownloadApkTask17(RemoteVersion remoteVersion) {
        super(remoteVersion);
    }

    @Override
    protected DownloadTrigger createDownloadTrigger(long cachedDownloadId) {
        Intent serviceIntent = new Intent(Toolkit.getContext(), DownloadApkService17.class);
        serviceIntent.putExtra("remote_version", mRemoteVersion.toJson());
        serviceIntent.putExtra("cached_download_id", cachedDownloadId);
        return new DownloadTrigger17(Toolkit.getContext(), serviceIntent, mRemoteVersion.getVersionCode(), cachedDownloadId, mRemoteVersion.getIgnorePeriod());
    }
}
