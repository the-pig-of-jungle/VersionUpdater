package com.coder.zzq.version_updater.communication;

import android.content.Context;

import com.coder.zzq.version_updater.bean.ApkInstaller;
import com.coder.zzq.version_updater.bean.DownloadProgress;
import com.coder.zzq.version_updater.bean.ReadableVersionInfo;
import com.coder.zzq.version_updater.bean.update_event.DetectNewVersion;
import com.coder.zzq.version_updater.bean.update_event.VersionUpdateEvent;
import com.coder.zzq.version_updater.bean.update_event.DownloadFailed;
import com.coder.zzq.version_updater.bean.update_event.DownloadInProgress;
import com.coder.zzq.version_updater.bean.update_event.DownloadRequestDuplicate;
import com.coder.zzq.version_updater.bean.update_event.LocalVersionIsUpToDate;
import com.coder.zzq.version_updater.bean.update_event.NewVersionApkExists;
import com.coder.zzq.version_updater.bean.download_trigger.DownloadTrigger;

public abstract class AbstractVersionUpdateCallback extends HoldActivityContextObserver {
    public AbstractVersionUpdateCallback(Context activityContext) {
        super(activityContext);
    }

    @Override
    public final void onChanged(VersionUpdateEvent downloadEvent) {
        if (downloadEvent instanceof DetectNewVersion) {
            onDetectNewVersion(getActivityContext(), ((DetectNewVersion) downloadEvent).getNewVersionInfo(), ((DetectNewVersion) downloadEvent).getDownloadTrigger());
        } else if (downloadEvent instanceof LocalVersionIsUpToDate) {
            onLocalVersionIsUpToDate(getActivityContext());
        } else if (downloadEvent instanceof DownloadRequestDuplicate) {
            onDownloadRequestDuplicate(getActivityContext());
        } else if (downloadEvent instanceof DownloadInProgress) {
            onDownloadProgressChanged(getActivityContext(), ((DownloadInProgress) downloadEvent).getDownloadProgress(), ((DownloadInProgress) downloadEvent).getNewVersionInfo(), ((DownloadInProgress) downloadEvent).getApkInstaller());
        } else if (downloadEvent instanceof DownloadFailed) {
            onDownloadFailed(getActivityContext(), (DownloadFailed) downloadEvent);
        } else if (downloadEvent instanceof NewVersionApkExists) {
            onNewVersionApkExists(getActivityContext(), ((NewVersionApkExists) downloadEvent).getNewVersionInfo(), ((NewVersionApkExists) downloadEvent).getApkInstaller());
        }
    }

    protected abstract void onDetectNewVersion(Context activityContext, ReadableVersionInfo newVersionInfo, DownloadTrigger downloadTrigger);

    protected abstract void onLocalVersionIsUpToDate(Context activityContext);

    protected abstract void onDownloadRequestDuplicate(Context activityContext);

    protected abstract void onDownloadProgressChanged(Context activityContext, DownloadProgress downloadProgress, ReadableVersionInfo newVersionINfo, ApkInstaller apkInstaller);

    protected abstract void onNewVersionApkExists(Context activityContext, ReadableVersionInfo newVersionInfo, ApkInstaller apkInstaller);

    protected abstract void onDownloadFailed(Context activityContext, DownloadFailed downloadFailed);
}
