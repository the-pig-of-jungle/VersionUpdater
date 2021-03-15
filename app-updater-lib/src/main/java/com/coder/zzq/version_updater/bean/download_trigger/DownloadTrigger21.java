package com.coder.zzq.version_updater.bean.download_trigger;

import android.app.job.JobInfo;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.coder.zzq.version_updater.bean.download_event.DownloadRequestDuplicate;
import com.coder.zzq.version_updater.communication.DownloadEventNotifier;
import com.coder.zzq.version_updater.util.UpdateUtil;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class DownloadTrigger21 extends AbstractDownloadTrigger implements DownloadTrigger {
    private final JobInfo mJobInfo;

    public DownloadTrigger21(JobInfo jobInfo, long cachedDownloadId, long ignorePeriod) {
        super(jobInfo.getId(), cachedDownloadId, ignorePeriod);
        mJobInfo = jobInfo;
    }

    @Override
    public void downloadInForeground() {
        downloadHelper(false);
    }

    @Override
    public void downloadInBackground() {
        downloadHelper(true);
    }

    private void downloadHelper(boolean background) {
        DownloadEventNotifier.get().filteringIntermediateProgress(background);
        if (UpdateUtil.isJobRunning(mJobInfo.getId())) {
            DownloadEventNotifier.get().notifyEvent(new DownloadRequestDuplicate());
            return;
        }
        UpdateUtil.getJobScheduler().schedule(mJobInfo);
    }
}
