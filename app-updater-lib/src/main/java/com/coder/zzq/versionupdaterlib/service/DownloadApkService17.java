package com.coder.zzq.versionupdaterlib.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.coder.zzq.toolkit.Toolkit;
import com.coder.zzq.versionupdaterlib.bean.RemoteVersion;
import com.coder.zzq.versionupdaterlib.bean.download_event.DownloadRequestDuplicate;
import com.coder.zzq.versionupdaterlib.communication.DownloadEventNotifier;
import com.coder.zzq.versionupdaterlib.communication.DownloadVersionInfoCache;
import com.coder.zzq.versionupdaterlib.tasks.TaskScheduler;
import com.coder.zzq.versionupdaterlib.util.UpdateUtil;

/**
 * Created by 朱志强 on 2018/1/27.
 */

public class DownloadApkService17 extends Service {
    private int mStartCount = 1;

    public DownloadApkService17() {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mStartCount != 1) {
            DownloadEventNotifier.get().notifyEvent(new DownloadRequestDuplicate());
            return START_NOT_STICKY;
        }
        RemoteVersion remoteVersion = RemoteVersion.fromJson(intent.getExtras().getString("remote_version"));
        long downloadId = intent.getLongExtra("cached_download_id", -1);
        if (downloadId == -1) {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(remoteVersion.getApkUrl()))
                    .setDestinationInExternalFilesDir(Toolkit.getContext(), "apk", UpdateUtil.getPackageName() + "_" + remoteVersion.getVersionCode() + ".apk")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI)
                    .setTitle(UpdateUtil.getAppName() + ": " + remoteVersion.getVersionName());
            downloadId = UpdateUtil.getDownloadManager().enqueue(request);
        }
        DownloadVersionInfoCache.storeDownloadVersionInfoIntoCache(remoteVersion.getVersionCode(), downloadId, DownloadEventNotifier.get().isFilteringIntermediateProgress());
        TaskScheduler.queryDownloadProgress17(downloadId, remoteVersion.createReadableOnlyVersionInfo());
        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mStartCount = 1;
    }
}
