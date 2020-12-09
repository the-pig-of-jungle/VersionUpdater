package com.coder.zzq.versionupdaterlib.service;

import android.app.DownloadManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.coder.zzq.toolkit.Toolkit;
import com.coder.zzq.versionupdaterlib.bean.RemoteVersion;
import com.coder.zzq.versionupdaterlib.communication.DownloadEventNotifier;
import com.coder.zzq.versionupdaterlib.communication.DownloadVersionInfoCache;
import com.coder.zzq.versionupdaterlib.tasks.TaskScheduler;
import com.coder.zzq.versionupdaterlib.util.UpdateUtil;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class DownloadApkService21 extends JobService {

    public DownloadApkService21() {

    }

    @Override
    public boolean onStartJob(JobParameters params) {
        RemoteVersion remoteVersion = RemoteVersion.fromJson(params.getExtras().getString("remote_version"));
        long downloadId = params.getExtras().getLong("cached_download_id");
        if (downloadId == -1) {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(remoteVersion.getApkUrl()))
                    .setDestinationInExternalFilesDir(Toolkit.getContext(), "apk", UpdateUtil.getPackageName() + "_" + remoteVersion.getVersionCode() + ".apk")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI)
                    .setTitle(UpdateUtil.getAppName() + ": " + remoteVersion.getVersionName());
            downloadId = UpdateUtil.getDownloadManager().enqueue(request);
        }
        DownloadVersionInfoCache.storeDownloadVersionInfoIntoCache(remoteVersion.getVersionCode(), downloadId, DownloadEventNotifier.get().isFilteringIntermediateProgress());
        TaskScheduler.queryDownloadProgress21(params.getJobId(), downloadId, remoteVersion.createReadableOnlyVersionInfo());
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}
