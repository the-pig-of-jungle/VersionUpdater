package com.coder.zzq.versionupdaterlib.tasks.download_apk;

import android.app.job.JobInfo;
import android.content.ComponentName;
import android.os.Build;
import android.os.PersistableBundle;

import androidx.annotation.RequiresApi;

import com.coder.zzq.toolkit.Toolkit;
import com.coder.zzq.versionupdaterlib.bean.RemoteVersion;
import com.coder.zzq.versionupdaterlib.bean.download_trigger.DownloadTrigger;
import com.coder.zzq.versionupdaterlib.bean.download_trigger.DownloadTrigger21;
import com.coder.zzq.versionupdaterlib.service.DownloadApkService21;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class DownloadApkTask21 extends DownloadApkTask {
    public DownloadApkTask21(RemoteVersion remoteVersion) {
        super(remoteVersion);
    }

    @Override
    protected DownloadTrigger createDownloadTrigger() {
        PersistableBundle extras = new PersistableBundle();
        extras.putString("remote_version", mRemoteVersion.toJson());
        JobInfo jobInfo = new JobInfo.Builder(mRemoteVersion.getVersionCode(), new ComponentName(Toolkit.getContext(), DownloadApkService21.class))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setExtras(extras)
                .build();
        return new DownloadTrigger21(jobInfo);
    }
}
