package com.coder.zzq.version_updater.tasks.query_progress;

import android.content.Intent;

import com.coder.zzq.version_updater.bean.ReadableVersionInfo;
import com.coder.zzq.version_updater.service.DownloadApkService17;
import com.coder.zzq.toolkit.Toolkit;

public class QueryProgressTask17 extends QueryProgressTask {
    public QueryProgressTask17(long downloadId, ReadableVersionInfo newVersionInfo) {
        super(downloadId, newVersionInfo);
    }

    @Override
    protected void cancelDownloadService() {
        Toolkit.getContext().stopService(new Intent(Toolkit.getContext(), DownloadApkService17.class));
    }
}
