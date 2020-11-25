package com.coder.zzq.versionupdaterlib.util;

import android.app.DownloadManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.coder.zzq.toolkit.Toolkit;
import com.coder.zzq.versionupdaterlib.VersionUpdaterFileProvider;
import com.coder.zzq.versionupdaterlib.bean.ApkInstaller;

import java.io.File;

public class UpdateUtil {

    public static int getVersionCode() {
        try {
            PackageManager packageManager = Toolkit.getContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(Toolkit.getContext().getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static synchronized String getAppName() {
        try {
            PackageManager packageManager = Toolkit.getContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(Toolkit.getContext().getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return Toolkit.getContext().getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "App";
    }

    public static String getPackageName() {
        return Toolkit.getContext().getPackageName();
    }


    public static boolean isJobRunning(int jobId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            JobScheduler jobScheduler = (JobScheduler) Toolkit.getContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return jobScheduler.getPendingJob(jobId) != null;
            } else {
                for (JobInfo jobInfo : jobScheduler.getAllPendingJobs()) {
                    if (jobInfo.getId() == jobId) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static File getApkFileByVersionCode(int versionCode) {
        return new File(Toolkit.getContext().getExternalFilesDir("apk"), UpdateUtil.getPackageName() + "_" + versionCode + ".apk");
    }

    public static ApkInstaller createApkInstaller(Uri uri, boolean needTransfer) {
        if (needTransfer && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = VersionUpdaterFileProvider.getUriForFile(Toolkit.getContext(), "com.coder.zzq.version_updater.file_provider", new File(uri.getPath()));
        }
        return new ApkInstaller(
                new Intent()
                        .setAction(Intent.ACTION_VIEW)
                        .setDataAndType(uri, "application/vnd.android.package-archive")
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION)
        );
    }


    public static DownloadManager getDownloadManager() {
        return (DownloadManager) Toolkit.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static JobScheduler getJobScheduler() {
        return (JobScheduler) Toolkit.getContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
    }
}
