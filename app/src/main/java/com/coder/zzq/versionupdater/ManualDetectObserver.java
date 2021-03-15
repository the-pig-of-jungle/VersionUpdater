package com.coder.zzq.versionupdater;

import android.content.Context;

import com.coder.zzq.version_updater.communication.AbstractManualDetectObserver;
import com.coder.zzq.versionupdater.annotations.ManualCheck;

@ManualCheck
public class ManualDetectObserver extends AbstractManualDetectObserver {
    public ManualDetectObserver(Context activityContext) {
        super(activityContext);
    }

    @Override
    protected void onLocalVersionIsUpToDate(Context context) {

    }

    @Override
    protected void onDownloadRequestDuplicate(Context context) {

    }
}
