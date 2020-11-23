package com.coder.zzq.versionupdaterlib.tasks;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.coder.zzq.versionupdaterlib.bean.DownloadTaskInfo;
import com.coder.zzq.versionupdaterlib.tasks.download_apk.DownloadApkTask17;
import com.coder.zzq.versionupdaterlib.tasks.download_apk.DownloadApkTask21;
import com.coder.zzq.versionupdaterlib.tasks.query_progress.QueryProgressTask;
import com.coder.zzq.versionupdaterlib.tasks.query_progress.QueryProgressTask17;
import com.coder.zzq.versionupdaterlib.tasks.query_progress.QueryProgressTask21;
import com.coder.zzq.versionupdaterlib.util.ThreadPool;

public class TaskScheduler {
    public static void cleanApkFile() {
        ThreadPool.submit(new CleanApkFileTask());
    }

    public static void downloadApk(DownloadTaskInfo downloadTaskInfo) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            downloadApk21(downloadTaskInfo);
        } else {
            downloadApk17(downloadTaskInfo);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static void downloadApk21(DownloadTaskInfo downloadTaskInfo) {
        ThreadPool.submit(new DownloadApkTask21(downloadTaskInfo));
    }

    private static void downloadApk17(DownloadTaskInfo downloadTaskInfo) {
        ThreadPool.submit(new DownloadApkTask17(downloadTaskInfo));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void queryDownloadProgress21(int jobId, long downloadId) {
        ThreadPool.submit(new QueryProgressTask21(jobId, downloadId));
    }

    public static void queryDownloadProgress17(long downloadId) {
        ThreadPool.submit(new QueryProgressTask17(downloadId));
    }

    public static void queryDownloadProgressDelay(QueryProgressTask queryProgressTask) {
        ThreadPool.submitDelay(queryProgressTask, 1500);
    }


}
