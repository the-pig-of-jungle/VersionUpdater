package com.coder.zzq.versionupdaterlib.service;

import android.app.DownloadManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.coder.zzq.toolkit.Toolkit;
import com.coder.zzq.versionupdaterlib.bean.DownloadTaskInfo;
import com.coder.zzq.versionupdaterlib.communication.DownloadVersionInfoCache;
import com.coder.zzq.versionupdaterlib.tasks.TaskScheduler;
import com.coder.zzq.versionupdaterlib.util.UpdateUtil;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class DownloadApkJobService extends JobService {

    public DownloadApkJobService() {

    }

    @Override
    public boolean onStartJob(JobParameters params) {
        DownloadTaskInfo downloadTaskInfo = DownloadTaskInfo.fromJson(params.getExtras().getString("download_task_info"));
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadTaskInfo.getRemoteApkUrl()))
                .setDestinationInExternalFilesDir(Toolkit.getContext(), "apk", UpdateUtil.getPackageName() + "_" + downloadTaskInfo.getRemoteVersionCode() + ".apk")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI)
                .setTitle(UpdateUtil.getAppName() + ": " + downloadTaskInfo.getRemoteVersionName());
        long downloadId = UpdateUtil.getDownloadManager().enqueue(request);
        DownloadVersionInfoCache.storeDownloadVersionInfoIntoCache(downloadTaskInfo.getRemoteVersionCode(), downloadId);
        TaskScheduler.queryDownloadProgress(params.getJobId(), downloadTaskInfo, downloadId);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}
