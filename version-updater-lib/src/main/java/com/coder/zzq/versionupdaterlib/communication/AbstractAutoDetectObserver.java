package com.coder.zzq.versionupdaterlib.communication;

import android.content.Context;

import com.coder.zzq.versionupdaterlib.bean.ApkInstaller;
import com.coder.zzq.versionupdaterlib.bean.DownloadProgress;
import com.coder.zzq.versionupdaterlib.bean.ReadableVersionInfo;
import com.coder.zzq.versionupdaterlib.bean.download_event.DetectNewVersion;
import com.coder.zzq.versionupdaterlib.bean.download_event.DownloadEvent;
import com.coder.zzq.versionupdaterlib.bean.download_event.DownloadFailed;
import com.coder.zzq.versionupdaterlib.bean.download_event.DownloadInProgress;
import com.coder.zzq.versionupdaterlib.bean.download_event.NewVersionApkExists;
import com.coder.zzq.versionupdaterlib.bean.download_trigger.DownloadTrigger;

public abstract class AbstractAutoDetectObserver extends HoldActivityContextObserver {
    public AbstractAutoDetectObserver(Context activityContext) {
        super(activityContext);
    }

    @Override
    public final void onChanged(DownloadEvent downloadEvent) {
        if (downloadEvent instanceof DetectNewVersion) {
            onDetectNewVersion(getActivityContext(), ((DetectNewVersion) downloadEvent).getNewVersionInfo(), ((DetectNewVersion) downloadEvent).getDownloadTrigger());
        } else if (downloadEvent instanceof DownloadInProgress) {
            onDownloadProgressChanged(getActivityContext(), ((DownloadInProgress) downloadEvent).getDownloadProgress(), ((DownloadInProgress) downloadEvent).getApkInstaller());
        } else if (downloadEvent instanceof DownloadFailed) {
            onDownloadFailed(getActivityContext(), ((DownloadFailed) downloadEvent).getFailedReason());
        } else if (downloadEvent instanceof NewVersionApkExists) {
            onNewVersionApkExists(getActivityContext(), ((NewVersionApkExists) downloadEvent).getNewVersionInfo(), ((NewVersionApkExists) downloadEvent).getApkInstaller());
        }
    }


    protected abstract void onDetectNewVersion(Context activityContext, ReadableVersionInfo newVersionInfo, DownloadTrigger downloadTrigger);

    protected abstract void onDownloadProgressChanged(Context activityContext, DownloadProgress downloadProgress, ApkInstaller apkInstaller);

    protected abstract void onNewVersionApkExists(Context activityContext, ReadableVersionInfo newVersionInfo, ApkInstaller apkInstaller);

    protected void onDownloadFailed(Context activityContext, @DownloadFailed.Reason int failedReason) {

    }
}
