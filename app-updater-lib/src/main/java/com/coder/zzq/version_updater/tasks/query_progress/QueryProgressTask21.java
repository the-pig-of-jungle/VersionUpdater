package com.coder.zzq.version_updater.tasks.query_progress;

import android.app.job.JobScheduler;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.coder.zzq.version_updater.bean.ReadableVersionInfo;
import com.coder.zzq.version_updater.util.UpdateUtil;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class QueryProgressTask21 extends QueryProgressTask {
    private final int mJobId;
    private final JobScheduler mJobScheduler;

    public QueryProgressTask21(int jobId, long downloadId, ReadableVersionInfo newVersionInfo) {
        super(downloadId, newVersionInfo);
        mJobId = jobId;
        mJobScheduler = UpdateUtil.getJobScheduler();
    }


    @Override
    protected void cancelDownloadService() {
        mJobScheduler.cancel(mJobId);
    }
}
