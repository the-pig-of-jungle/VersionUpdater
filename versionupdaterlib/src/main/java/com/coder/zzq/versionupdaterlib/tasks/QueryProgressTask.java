package com.coder.zzq.versionupdaterlib.tasks;

import android.app.DownloadManager;
import android.app.job.JobScheduler;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.coder.zzq.versionupdaterlib.bean.DownloadProgress;
import com.coder.zzq.versionupdaterlib.bean.DownloadTaskInfo;
import com.coder.zzq.versionupdaterlib.bean.download.event.DownloadFailed;
import com.coder.zzq.versionupdaterlib.bean.download.event.DownloadInProgress;
import com.coder.zzq.versionupdaterlib.communication.DownloadEventNotifier;
import com.coder.zzq.versionupdaterlib.util.UpdateUtil;

import java.util.TimerTask;

import static android.app.DownloadManager.STATUS_FAILED;
import static android.app.DownloadManager.STATUS_RUNNING;
import static android.app.DownloadManager.STATUS_SUCCESSFUL;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class QueryProgressTask extends TimerTask {

    private final DownloadTaskInfo mDownloadTaskInfo;
    private final long mDownloadId;
    private final int mJobId;
    private final DownloadManager mDownloadManager;
    private final JobScheduler mJobScheduler;


    public QueryProgressTask(int jobId, DownloadTaskInfo downloadTaskInfo, long downloadId) {
        mDownloadTaskInfo = downloadTaskInfo;
        mJobId = jobId;
        mDownloadId = downloadId;
        mDownloadManager = UpdateUtil.getDownloadManager();
        mJobScheduler = UpdateUtil.getJobScheduler();
    }


    @Override
    public void run() {
        if (mDownloadId != -1) {
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(mDownloadId);
            try (Cursor cursor = mDownloadManager.query(query)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    String uriStr = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    long sizeSoFar = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    long totalSize = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                    long percentage = (sizeSoFar * 100 / totalSize);

                    switch (status) {
                        case STATUS_RUNNING:
                            if (!DownloadEventNotifier.get().isFilteringIntermediateProgress()) {
                                DownloadEventNotifier.get().notifyEvent(new DownloadInProgress(new DownloadProgress(sizeSoFar, totalSize, percentage + "%", false), null));
                            }
                            break;
                        case STATUS_FAILED:
                            int reason = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_REASON));
                            switch (reason) {
                                case DownloadManager.ERROR_DEVICE_NOT_FOUND:
                                    DownloadEventNotifier.get().notifyEvent(new DownloadFailed(DownloadFailed.FAILED_REASON_SD_CARD_NOT_FOUND));
                                    break;
                                case DownloadManager.ERROR_INSUFFICIENT_SPACE:
                                    DownloadEventNotifier.get().notifyEvent(new DownloadFailed(DownloadFailed.FAILED_REASON_INSUFFICIENT_SPACE));
                                    break;
                            }
                            mJobScheduler.cancel(mJobId);
                            mDownloadManager.remove(mDownloadId);
                            break;
                        case STATUS_SUCCESSFUL:
                            DownloadEventNotifier.get().notifyEvent(
                                    new DownloadInProgress(
                                            new DownloadProgress(sizeSoFar, totalSize, percentage + "%", true),
                                            UpdateUtil.createApkInstaller(Uri.parse(uriStr), true)
                                    )
                            );
                            mJobScheduler.cancel(mJobId);
                            break;
                    }

                    if (status != STATUS_SUCCESSFUL && status != STATUS_FAILED) {
                        TaskScheduler.queryDownloadProgressDelay(this);
                    }
                }
            }
        }

    }
}
