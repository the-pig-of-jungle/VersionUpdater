package com.coder.zzq.versionupdaterlib.tasks.download_apk;

import android.content.Intent;

import com.coder.zzq.toolkit.Toolkit;
import com.coder.zzq.versionupdaterlib.bean.DownloadTaskInfo;
import com.coder.zzq.versionupdaterlib.bean.download_trigger.DownloadTrigger;
import com.coder.zzq.versionupdaterlib.bean.download_trigger.DownloadTrigger17;
import com.coder.zzq.versionupdaterlib.service.DownloadApkService17;

public class DownloadApkTask17 extends DownloadApkTask {
    public DownloadApkTask17(DownloadTaskInfo downloadTaskInfo) {
        super(downloadTaskInfo);
    }

    @Override
    protected DownloadTrigger createDownloadTrigger() {
        Intent serviceIntent = new Intent(Toolkit.getContext(), DownloadApkService17.class);
        serviceIntent.putExtra("download_task_info", mDownloadTaskInfo.toJson());
        return new DownloadTrigger17(Toolkit.getContext(), serviceIntent);
    }
}
