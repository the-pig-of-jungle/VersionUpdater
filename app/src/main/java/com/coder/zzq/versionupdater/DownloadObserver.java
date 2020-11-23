package com.coder.zzq.versionupdater;

import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.coder.zzq.versionupdaterlib.bean.ApkInstaller;
import com.coder.zzq.versionupdaterlib.bean.DownloadProgress;
import com.coder.zzq.versionupdaterlib.bean.DownloadTrigger;
import com.coder.zzq.versionupdaterlib.bean.VersionInfo;
import com.coder.zzq.versionupdaterlib.communication.AbstractDownloadObserver;

public class DownloadObserver extends AbstractDownloadObserver {
    public DownloadObserver(AppCompatActivity appCompatActivity) {
        super(appCompatActivity);
    }

    @Override
    protected void onDetectNewVersion(AppCompatActivity activity, VersionInfo newVersionInfo, DownloadTrigger downloadTrigger) {
        if (newVersionInfo.isForceUpdate()) {

        } else {
            new AlertDialog.Builder(activity)
                    .setMessage("发现新版本")
                    .setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            downloadTrigger.downloadInForeground();
                        }
                    }).setNegativeButton("暂不更细", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();
        }

    }

    @Override
    protected void onDownloadProgressChanged(AppCompatActivity activity, DownloadProgress downloadProgress, ApkInstaller apkInstaller) {
        Log.d("zzq", downloadProgress.getPercentage());
        if (downloadProgress.isComplete()) {
            apkInstaller.install();
        }
    }

    @Override
    protected void onNewVersionApkExists(AppCompatActivity activity, VersionInfo versionInfo, ApkInstaller apkInstaller) {


        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setMessage("新版本已准备好，请安装")
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        apkInstaller.install();
                    }
                })
                .setNegativeButton(versionInfo.isForceUpdate() ? null : "取消", new DialogInterface.OnClickListener() {
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
