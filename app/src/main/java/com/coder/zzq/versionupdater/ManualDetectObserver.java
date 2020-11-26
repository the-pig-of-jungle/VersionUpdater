package com.coder.zzq.versionupdater;

import android.content.Context;
import android.widget.Toast;

import com.coder.zzq.toolkit.Toolkit;
import com.coder.zzq.versionupdater.annotations.ManualCheck;
import com.coder.zzq.versionupdaterlib.communication.AbstractManualDetectObserver;

@ManualCheck
public class ManualDetectObserver extends AbstractManualDetectObserver {

    public ManualDetectObserver(Context activityContext) {
        super(activityContext);
    }

    @Override
    protected void onLocalVersionIsUpToDate(Context activityContext) {
        Toast.makeText(Toolkit.getContext(), "当前已是最新版本", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDownloadRequestDuplicate(Context activityContext) {
        Toast.makeText(Toolkit.getContext(), "已在后台下载中...", Toast.LENGTH_LONG).show();
    }
}
