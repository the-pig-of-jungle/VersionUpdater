package com.coder.zzq.versionupdaterlib.communication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.coder.zzq.versionupdaterlib.bean.ApkInstaller;
import com.coder.zzq.versionupdaterlib.bean.DownloadProgress;
import com.coder.zzq.versionupdaterlib.bean.VersionInfo;
import com.coder.zzq.versionupdaterlib.bean.download_event.DetectNewVersion;
import com.coder.zzq.versionupdaterlib.bean.download_event.DownloadEvent;
import com.coder.zzq.versionupdaterlib.bean.download_event.DownloadFailed;
import com.coder.zzq.versionupdaterlib.bean.download_event.DownloadInProgress;
import com.coder.zzq.versionupdaterlib.bean.download_event.NewVersionApkExists;
import com.coder.zzq.versionupdaterlib.bean.download_trigger.DownloadTrigger;

import java.lang.ref.WeakReference;

public abstract class AbstractDownloadObserver implements Observer<DownloadEvent> {

    private final WeakReference<AppCompatActivity> mAppCompatActivity;

    public AbstractDownloadObserver(AppCompatActivity appCompatActivity) {

        mAppCompatActivity = new WeakReference<>(appCompatActivity);
    }

    @Override
    public final void onChanged(DownloadEvent downloadEvent) {
        if (downloadEvent instanceof DetectNewVersion) {
            onDetectNewVersion(mAppCompatActivity.get(), ((DetectNewVersion) downloadEvent).getNewVersionInfo(), ((DetectNewVersion) downloadEvent).getDownloadTrigger());
        } else if (downloadEvent instanceof DownloadInProgress) {
            onDownloadProgressChanged(mAppCompatActivity.get(), ((DownloadInProgress) downloadEvent).getDownloadProgress(), ((DownloadInProgress) downloadEvent).getApkInstaller());
        } else if (downloadEvent instanceof DownloadFailed) {
            onDownloadFailed(((DownloadFailed) downloadEvent).getFailedReason());
        } else if (downloadEvent instanceof NewVersionApkExists) {
            onNewVersionApkExists(mAppCompatActivity.get(), ((NewVersionApkExists) downloadEvent).getVersionInfo(), ((NewVersionApkExists) downloadEvent).getApkInstaller());
        }
    }


    protected abstract void onDetectNewVersion(AppCompatActivity activity, VersionInfo newVersionInfo, DownloadTrigger downloadTrigger);

    protected abstract void onDownloadProgressChanged(AppCompatActivity activity, DownloadProgress downloadProgress, ApkInstaller apkInstaller);

    protected abstract void onNewVersionApkExists(AppCompatActivity activity, VersionInfo versionInfo, ApkInstaller apkInstaller);

    protected void onDownloadFailed(@DownloadFailed.Reason int failedReason) {

    }

}
