package com.coder.zzq.versionupdater;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.coder.zzq.toolkit.log.EasyLogger;
import com.coder.zzq.versionupdater.annotations.AutoCheck;
import com.coder.zzq.versionupdaterlib.bean.ApkInstaller;
import com.coder.zzq.versionupdaterlib.bean.DownloadProgress;
import com.coder.zzq.versionupdaterlib.bean.ReadableVersionInfo;
import com.coder.zzq.versionupdaterlib.bean.download_trigger.DownloadTrigger;
import com.coder.zzq.versionupdaterlib.communication.AbstractAutoDetectObserver;
@AutoCheck
public class autoDetectObserver extends AbstractAutoDetectObserver {
    public autoDetectObserver(Context activityContext) {
        super(activityContext);
    }


    @Override
    protected void onDetectNewVersion(Context activityContext, ReadableVersionInfo newReadableVersionInfo, DownloadTrigger downloadTrigger) {
        if (newReadableVersionInfo.isForceUpdate()) {

        } else {
            new AlertDialog.Builder(activityContext)
                    .setMessage("发现新版本")
                    .setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            downloadTrigger.downloadInForeground();
                        }
                    }).setNegativeButton("暂不更细", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    downloadTrigger.ignoreThisVersion();
                    dialog.dismiss();
                }
            }).create().show();
        }
    }

    @Override
    protected void onDownloadProgressChanged(Context activityContext, DownloadProgress downloadProgress, ApkInstaller apkInstaller) {
        Log.d("zzq", downloadProgress.getPercentage());
        if (downloadProgress.isComplete()) {
            EasyLogger.d("download complete");
            apkInstaller.install();
        }
    }

    @Override
    protected void onNewVersionApkExists(Context activityContext, ReadableVersionInfo readableVersionInfo, ApkInstaller apkInstaller) {
        AlertDialog dialog = new AlertDialog.Builder(activityContext)
                .setMessage("新版本已准备好，请安装")
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        apkInstaller.install();
                    }
                })
                .setNegativeButton(readableVersionInfo.isForceUpdate() ? null : "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}
