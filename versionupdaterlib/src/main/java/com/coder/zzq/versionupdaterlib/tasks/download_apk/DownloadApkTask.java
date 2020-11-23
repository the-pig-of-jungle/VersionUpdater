package com.coder.zzq.versionupdaterlib.tasks.download_apk;

import android.app.DownloadManager;
import android.database.Cursor;
import android.net.Uri;

import com.coder.zzq.versionupdaterlib.bean.DownloadTaskInfo;
import com.coder.zzq.versionupdaterlib.bean.download_event.DetectNewVersion;
import com.coder.zzq.versionupdaterlib.bean.download_event.DownloadRequestDuplicate;
import com.coder.zzq.versionupdaterlib.bean.download_event.NewVersionApkExists;
import com.coder.zzq.versionupdaterlib.bean.download_trigger.DownloadTrigger;
import com.coder.zzq.versionupdaterlib.communication.DownloadEventNotifier;
import com.coder.zzq.versionupdaterlib.communication.DownloadVersionInfoCache;
import com.coder.zzq.versionupdaterlib.util.UpdateUtil;

import java.io.File;


public abstract class DownloadApkTask implements Runnable {
    protected final DownloadTaskInfo mDownloadTaskInfo;

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
                }
            }
        }


        DownloadEventNotifier.get().notifyEvent(new DetectNewVersion(
                mDownloadTaskInfo.fetchBaseVersionInfo(),
                createDownloadTrigger())
        );
    }

    protected abstract DownloadTrigger createDownloadTrigger();
}
