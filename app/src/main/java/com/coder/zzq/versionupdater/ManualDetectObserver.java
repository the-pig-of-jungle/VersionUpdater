package com.coder.zzq.versionupdater;

import android.content.Context;

import com.coder.zzq.versionupdater.annotations.ManualCheck;
import com.coder.zzq.versionupdaterlib.communication.AbstractManualDetectObserver;

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
