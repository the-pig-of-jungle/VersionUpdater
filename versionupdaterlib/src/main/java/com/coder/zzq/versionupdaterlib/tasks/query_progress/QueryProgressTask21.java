package com.coder.zzq.versionupdaterlib.tasks.query_progress;

import android.app.job.JobScheduler;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.coder.zzq.versionupdaterlib.util.UpdateUtil;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class QueryProgressTask21 extends QueryProgressTask {
    private final int mJobId;
    private final JobScheduler mJobScheduler;

    public QueryProgressTask21(int jobId, long downloadId) {
        super(downloadId);
        mJobId = jobId;
        mJobScheduler = UpdateUtil.getJobScheduler();
    }


    @Override
    protected void cancelDownloadService() {
        mJobScheduler.cancel(mJobId);
    }
}
