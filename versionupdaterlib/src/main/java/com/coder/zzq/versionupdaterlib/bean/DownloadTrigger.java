package com.coder.zzq.versionupdaterlib.bean;

import android.app.job.JobInfo;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.coder.zzq.versionupdaterlib.communication.DownloadEventNotifier;
import com.coder.zzq.versionupdaterlib.util.UpdateUtil;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class DownloadTrigger {
    private final JobInfo mJobInfo;

    public DownloadTrigger(JobInfo jobInfo) {
        mJobInfo = jobInfo;
    }


    public void downloadInForeground() {
        DownloadEventNotifier.get().filteringIntermediateProgress(false);
        UpdateUtil.getJobScheduler().schedule(mJobInfo);
    }

    public void downloadInBackground() {
        DownloadEventNotifier.get().filteringIntermediateProgress(true);
        UpdateUtil.getJobScheduler().schedule(mJobInfo);
    }
}
