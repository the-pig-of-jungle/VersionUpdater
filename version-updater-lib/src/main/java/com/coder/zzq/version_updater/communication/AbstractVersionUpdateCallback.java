package com.coder.zzq.version_updater.communication;

import android.content.Context;

import com.coder.zzq.version_updater.bean.ApkInstaller;
import com.coder.zzq.version_updater.bean.DownloadProgress;
import com.coder.zzq.version_updater.bean.ReadableVersionInfo;
import com.coder.zzq.version_updater.bean.download_trigger.DownloadTrigger;
import com.coder.zzq.version_updater.bean.update_event.ClearInActiveObserverData;
import com.coder.zzq.version_updater.bean.update_event.DetectNewVersion;
import com.coder.zzq.version_updater.bean.update_event.DownloadFailed;
import com.coder.zzq.version_updater.bean.update_event.DownloadInProgress;
import com.coder.zzq.version_updater.bean.update_event.DownloadRequestDuplicate;
import com.coder.zzq.version_updater.bean.update_event.LocalVersionIsUpToDate;
import com.coder.zzq.version_updater.bean.update_event.NewVersionApkExists;
import com.coder.zzq.version_updater.bean.update_event.VersionUpdateEvent;

public abstract class AbstractVersionUpdateCallback extends HoldActivityContextObserver {
    public AbstractVersionUpdateCallback(Context activityContext) {
        super(activityContext);
    }

    @Override
    public final void onChanged(VersionUpdateEvent updateEvent) {
        if (updateEvent instanceof DetectNewVersion) {
            onDetectNewVersion(getActivityContext(), ((DetectNewVersion) updateEvent).getNewVersionInfo(), ((DetectNewVersion) updateEvent).getDownloadTrigger());
            UpdateEventNotifier.get().notifyEvent(new ClearInActiveObserverData());
        } else if (updateEvent instanceof LocalVersionIsUpToDate) {
            onLocalVersionIsUpToDate(getActivityContext());
            UpdateEventNotifier.get().notifyEvent(new ClearInActiveObserverData());
        } else if (updateEvent instanceof DownloadRequestDuplicate) {
            onDownloadRequestDuplicate(getActivityContext());
            UpdateEventNotifier.get().notifyEvent(new ClearInActiveObserverData());
        } else if (updateEvent instanceof DownloadInProgress) {
            onDownloadProgressChanged(getActivityContext(), ((DownloadInProgress) updateEvent).getDownloadProgress(), ((DownloadInProgress) updateEvent).getNewVersionInfo(), ((DownloadInProgress) updateEvent).getApkInstaller());
            UpdateEventNotifier.get().notifyEvent(new ClearInActiveObserverData());
        } else if (updateEvent instanceof DownloadFailed) {
            onDownloadFailed(getActivityContext(), (DownloadFailed) updateEvent);
            UpdateEventNotifier.get().notifyEvent(new ClearInActiveObserverData());
        } else if (updateEvent instanceof NewVersionApkExists) {
            onNewVersionApkExists(getActivityContext(), ((NewVersionApkExists) updateEvent).getNewVersionInfo(), ((NewVersionApkExists) updateEvent).getApkInstaller());
            UpdateEventNotifier.get().notifyEvent(new ClearInActiveObserverData());
        }
    }

    protected abstract void onDetectNewVersion(Context activityContext, ReadableVersionInfo newVersionInfo, DownloadTrigger downloadTrigger);

    protected abstract void onLocalVersionIsUpToDate(Context activityContext);

    protected abstract void onDownloadRequestDuplicate(Context activityContext);

    protected abstract void onDownloadProgressChanged(Context activityContext, DownloadProgress downloadProgress, ReadableVersionInfo newVersionINfo, ApkInstaller apkInstaller);

    protected abstract void onNewVersionApkExists(Context activityContext, ReadableVersionInfo newVersionInfo, ApkInstaller apkInstaller);

    protected abstract void onDownloadFailed(Context activityContext, DownloadFailed downloadFailed);
}
