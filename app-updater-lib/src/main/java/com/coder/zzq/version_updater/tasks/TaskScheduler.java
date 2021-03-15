package com.coder.zzq.version_updater.tasks;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.coder.zzq.version_updater.bean.ReadableVersionInfo;
import com.coder.zzq.version_updater.bean.RemoteVersion;
import com.coder.zzq.version_updater.tasks.download_apk.DownloadApkTask17;
import com.coder.zzq.version_updater.tasks.download_apk.DownloadApkTask21;
import com.coder.zzq.version_updater.tasks.query_progress.QueryProgressTask;
import com.coder.zzq.version_updater.tasks.query_progress.QueryProgressTask17;
import com.coder.zzq.version_updater.tasks.query_progress.QueryProgressTask21;
import com.coder.zzq.version_updater.util.ThreadPool;

public class TaskScheduler {
    public static void cleanApkFile() {
        ThreadPool.submit(new CleanApkFileTask());
    }

    public static void downloadApk(RemoteVersion remoteVersion) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            downloadApk21(remoteVersion);
        } else {
            downloadApk17(remoteVersion);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static void downloadApk21(RemoteVersion remoteVersion) {
        ThreadPool.submit(new DownloadApkTask21(remoteVersion));
    }

    private static void downloadApk17(RemoteVersion remoteVersion) {
        ThreadPool.submit(new DownloadApkTask17(remoteVersion));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void queryDownloadProgress21(int jobId, long downloadId, ReadableVersionInfo newVersionInfo) {
        ThreadPool.submit(new QueryProgressTask21(jobId, downloadId, newVersionInfo));
    }

    public static void queryDownloadProgress17(long downloadId, ReadableVersionInfo newVersionInfo) {
        ThreadPool.submit(new QueryProgressTask17(downloadId, newVersionInfo));
    }

    public static void queryDownloadProgressDelay(QueryProgressTask queryProgressTask) {
        ThreadPool.submitDelay(queryProgressTask, 1500);
    }


}
