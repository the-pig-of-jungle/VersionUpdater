package com.coder.zzq.versionupdaterlib.bean.download_trigger;

import android.app.job.JobInfo;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.coder.zzq.versionupdaterlib.communication.DownloadEventNotifier;
import com.coder.zzq.versionupdaterlib.util.UpdateUtil;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class DownloadTrigger21 extends AbstractDownloadTrigger implements DownloadTrigger {
    private final JobInfo mJobInfo;

    public DownloadTrigger21(JobInfo jobInfo) {
        super(jobInfo.getId());
        mJobInfo = jobInfo;
    }

    @Override
    public void downloadInForeground() {
        DownloadEventNotifier.get().filteringIntermediateProgress(false);
        if (UpdateUtil.isJobRunning(mJobInfo.getId())) {
            return;
        }
        UpdateUtil.getJobScheduler().schedule(mJobInfo);
    }

    @Override
    public void downloadInBackground() {
        DownloadEventNotifier.get().filteringIntermediateProgress(true);
        if (UpdateUtil.isJobRunning(mJobInfo.getId())) {
            return;
        }
        UpdateUtil.getJobScheduler().schedule(mJobInfo);
    }
}
