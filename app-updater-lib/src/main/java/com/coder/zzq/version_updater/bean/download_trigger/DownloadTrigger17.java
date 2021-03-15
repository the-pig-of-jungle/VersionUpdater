package com.coder.zzq.version_updater.bean.download_trigger;

import android.content.Context;
import android.content.Intent;

import com.coder.zzq.version_updater.communication.DownloadEventNotifier;

public class DownloadTrigger17 extends AbstractDownloadTrigger implements DownloadTrigger {
    private final Context mApplicationContext;
    private final Intent mServiceIntent;

    public DownloadTrigger17(Context applicationContext, Intent serviceIntent, int versionCode, long cachedDownloadId, long ignorePeriod) {
        super(versionCode, cachedDownloadId,ignorePeriod);
        mApplicationContext = applicationContext;
        mServiceIntent = serviceIntent;
    }

    @Override
    public void downloadInForeground() {
        DownloadEventNotifier.get().filteringIntermediateProgress(false);
        mApplicationContext.startService(mServiceIntent);
    }

    @Override
    public void downloadInBackground() {
        DownloadEventNotifier.get().filteringIntermediateProgress(true);
        mApplicationContext.startService(mServiceIntent);
    }
}
