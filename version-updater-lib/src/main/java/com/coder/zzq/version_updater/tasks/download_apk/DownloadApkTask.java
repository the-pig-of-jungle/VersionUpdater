package com.coder.zzq.version_updater.tasks.download_apk;

import android.app.DownloadManager;
import android.database.Cursor;
import android.net.Uri;

import com.coder.zzq.version_updater.bean.RemoteVersion;
import com.coder.zzq.version_updater.bean.download_trigger.DownloadTrigger;
import com.coder.zzq.version_updater.bean.update_event.DetectNewVersion;
import com.coder.zzq.version_updater.bean.update_event.NewVersionApkExists;
import com.coder.zzq.version_updater.communication.DownloadVersionInfoCache;
import com.coder.zzq.version_updater.communication.UpdateEventNotifier;
import com.coder.zzq.version_updater.util.UpdateUtil;

import java.io.File;


public abstract class DownloadApkTask implements Runnable {
    protected final RemoteVersion mRemoteVersion;

    public DownloadApkTask(RemoteVersion remoteVersion) {
        mRemoteVersion = remoteVersion;
    }

    @Override
    public void run() {
        int cachedVersionCode = DownloadVersionInfoCache.getDownloadVersionCodeFromCache();
        long cachedDownloadId = DownloadVersionInfoCache.getDownloadIdFromCache();
        boolean downloadInBackground = DownloadVersionInfoCache.isDownloadInBackground();
        if (cachedVersionCode == mRemoteVersion.getVersionCode()) {
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
                                UpdateEventNotifier.get().notifyEvent(new NewVersionApkExists(mRemoteVersion.createReadableOnlyVersionInfo(), UpdateUtil.createApkInstaller(Uri.parse(uriStr), true)));
                                return;
                            }
                            break;
                        case DownloadManager.STATUS_PENDING:
                        case DownloadManager.STATUS_RUNNING:
                        case DownloadManager.STATUS_PAUSED:
                            if (downloadInBackground) {
                                createDownloadTrigger(cachedDownloadId).downloadInBackground();
                            } else {
                                createDownloadTrigger(cachedDownloadId).downloadInForeground();
                            }
                            return;
                    }
                }
            }
        }


        UpdateEventNotifier.get().notifyEvent(new DetectNewVersion(
                mRemoteVersion.createReadableOnlyVersionInfo(),
                createDownloadTrigger(-1))
        );
    }

    protected abstract DownloadTrigger createDownloadTrigger(long cachedDownloadId);
}
