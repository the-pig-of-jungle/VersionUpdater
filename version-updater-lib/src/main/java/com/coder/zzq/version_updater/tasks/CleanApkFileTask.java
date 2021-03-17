package com.coder.zzq.version_updater.tasks;

import com.coder.zzq.toolkit.Toolkit;
import com.coder.zzq.version_updater.communication.DownloadVersionInfoCache;
import com.coder.zzq.version_updater.util.ThreadPool;

import java.io.File;

public class CleanApkFileTask implements Runnable {
    public CleanApkFileTask() {

    }

    @Override
    public void run() {
        File rootFolder = Toolkit.getContext().getExternalFilesDir("apk");
        File[] apkFiles = rootFolder.listFiles();
        if (apkFiles != null) {
            for (File file : apkFiles) {
                file.delete();
            }
        }
        DownloadVersionInfoCache.storeDownloadVersionInfoIntoCache(0, -1, false);
        ThreadPool.shutdown();
    }
}
