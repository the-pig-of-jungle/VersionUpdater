package com.coder.zzq.versionupdaterlib.tasks.query_progress;

import android.content.Intent;

import com.coder.zzq.toolkit.Toolkit;
import com.coder.zzq.versionupdaterlib.service.DownloadApkService17;

public class QueryProgressTask17 extends QueryProgressTask {
    public QueryProgressTask17(long downloadId) {
        super(downloadId);
    }

    @Override
    protected void cancelDownloadService() {
        Toolkit.getContext().stopService(new Intent(Toolkit.getContext(), DownloadApkService17.class));
    }
}
