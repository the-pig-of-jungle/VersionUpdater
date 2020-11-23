package com.coder.zzq.versionupdaterlib.tasks;

import android.app.DownloadManager;
import android.app.job.JobInfo;
import android.content.ComponentName;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.PersistableBundle;

import androidx.annotation.RequiresApi;

import com.coder.zzq.toolkit.Toolkit;
import com.coder.zzq.versionupdaterlib.bean.DownloadTaskInfo;
import com.coder.zzq.versionupdaterlib.bean.DownloadTrigger;
import com.coder.zzq.versionupdaterlib.bean.VersionInfo;
import com.coder.zzq.versionupdaterlib.bean.download.event.DetectNewVersion;
import com.coder.zzq.versionupdaterlib.bean.download.event.DownloadRequestDuplicate;
import com.coder.zzq.versionupdaterlib.bean.download.event.NewVersionApkExists;
import com.coder.zzq.versionupdaterlib.communication.DownloadEventNotifier;
import com.coder.zzq.versionupdaterlib.communication.DownloadVersionInfoCache;
import com.coder.zzq.versionupdaterlib.service.DownloadApkJobService;
import com.coder.zzq.versionupdaterlib.util.UpdateUtil;

import java.io.File;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class DownloadApkTask implements Runnable {
    private final DownloadTaskInfo mDownloadTaskInfo;

    public DownloadApkTask(DownloadTaskInfo downloadTaskInfo) {
        mDownloadTaskInfo = downloadTaskInfo;
    }

    @Override
    public void run() {
        int cachedVersionCode = DownloadVersionInfoCache.getDownloadVersionCodeFromCache();
        long cachedDownloadId = DownloadVersionInfoCache.getDownloadIdFromCache();
        if (cachedVersionCode == mDownloadTaskInfo.getRemoteVersionCode()) {
            DownloadManager downloadManager = UpdateUtil.getDownloadManager();
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(cachedDownloadId);
            try (Cursor cursor = downloadManager.query(query)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    switch (status) {
                        case DownloadManager.STATUS_SUCCESSFUL:
                            String uriStr = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                            File file = UpdateUtil.getApkFileByVersionCode(cachedVersionCode);
                            if (file.exists()) {
                                DownloadEventNotifier.get().notifyEvent(new NewVersionApkExists(mDownloadTaskInfo.fetchBaseVersionInfo(), UpdateUtil.createApkInstaller(Uri.parse(uriStr), true)));
                                return;
                            }
                            break;
                        case DownloadManager.STATUS_PENDING:
                        case DownloadManager.STATUS_RUNNING:
                        case DownloadManager.STATUS_PAUSED:
                            DownloadEventNotifier.get()
                                    .notifyEvent(new DownloadRequestDuplicate());
                            return;
                    }

                    if (UpdateUtil.isJobRunning(mDownloadTaskInfo.getRemoteVersionCode())) {
                        return;
                    }
                }
            }
        }



        PersistableBundle extras = new PersistableBundle();
        extras.putString("download_task_info", mDownloadTaskInfo.toJson());
        JobInfo jobInfo = new JobInfo.Builder(mDownloadTaskInfo.getRemoteVersionCode(), new ComponentName(Toolkit.getContext(), DownloadApkJobService.class))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setExtras(extras)
                .setPersisted(true)
                .build();

        DownloadEventNotifier.get().notifyEvent(new DetectNewVersion(
                new VersionInfo(mDownloadTaskInfo.getRemoteVersionCode(),
                        mDownloadTaskInfo.getRemoteVersionName(),
                        mDownloadTaskInfo.getRemoteVersionName(),
                        mDownloadTaskInfo.isForceUpdate()),
                new DownloadTrigger(jobInfo))
        );
    }
}
