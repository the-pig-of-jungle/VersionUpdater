package com.coder.zzq.versionupdaterlib.tasks;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.coder.zzq.versionupdaterlib.bean.DownloadTaskInfo;
import com.coder.zzq.versionupdaterlib.util.ThreadPool;

public class TaskScheduler {
    public static void cleanApkFile() {
        ThreadPool.submit(new CleanApkFileTask());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void downloadApk(DownloadTaskInfo downloadTaskInfo) {
        ThreadPool.submit(new DownloadApkTask(downloadTaskInfo));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void queryDownloadProgress(int jobId, DownloadTaskInfo downloadTaskInfo, long downloadId) {
        ThreadPool.submit(new QueryProgressTask(jobId, downloadTaskInfo, downloadId));
    }

    public static void queryDownloadProgressDelay(QueryProgressTask queryProgressTask) {
        ThreadPool.submitDelay(queryProgressTask, 1500);
    }


}
