package com.coder.zzq.versionupdater;

import android.app.Activity;
import android.content.Context;


import com.coder.zzq.version_updater.bean.ApkInstaller;
import com.coder.zzq.version_updater.bean.DownloadProgress;
import com.coder.zzq.version_updater.bean.ReadableVersionInfo;
import com.coder.zzq.version_updater.bean.download_trigger.DownloadTrigger;
import com.coder.zzq.version_updater.communication.AbstractAutoDetectObserver;
import com.coder.zzq.smartshow.dialog.DialogBtnClickListener;
import com.coder.zzq.smartshow.dialog.NotificationDialog;
import com.coder.zzq.smartshow.dialog.SmartDialog;
import com.coder.zzq.toolkit.Utils;
import com.coder.zzq.versionupdater.annotations.AutoCheck;


@AutoCheck
public class AutoDetectObserver extends AbstractAutoDetectObserver {
    public AutoDetectObserver(Context activityContext) {
        super(activityContext);
    }

    private NotificationDialog mNotificationDialog = new NotificationDialog();

    @Override
    protected void onDetectNewVersion(Context context, ReadableVersionInfo readableVersionInfo, DownloadTrigger downloadTrigger) {
        mNotificationDialog.title("发现新版本:" + readableVersionInfo.getVersionName())
                .cancelable(!readableVersionInfo.isForceUpdate())
                .message(Utils.isEmpty(readableVersionInfo.getVersionDesc()) ? "1.修复已知问题" : readableVersionInfo.getVersionDesc())
                .confirmBtn("立即下载", new DialogBtnClickListener() {
                    @Override
                    public void onBtnClick(SmartDialog smartDialog, int i, Object o) {
                        if (readableVersionInfo.isForceUpdate()) {
                            downloadTrigger.downloadInForeground();
                        } else {
                            downloadTrigger.downloadInBackground();
                            downloadTrigger.cancelUpdate();
                        }
                    }
                }).showInActivity((Activity) context);
    }

    @Override
    protected void onDownloadProgressChanged(Context context, DownloadProgress downloadProgress, ReadableVersionInfo readableVersionInfo, ApkInstaller apkInstaller) {
        if (mNotificationDialog == null) {
            mNotificationDialog = new NotificationDialog();
        }
        mNotificationDialog.title(downloadProgress.isComplete() ? "下载完成" : "正在下载")
                .cancelable(!readableVersionInfo.isForceUpdate())
                .message("当前进度：" + downloadProgress.getPercentage())
                .confirmBtn(downloadProgress.isComplete() ? "立即安装" : "请等待...", downloadProgress.isComplete() ? new DialogBtnClickListener() {
                    @Override
                    public void onBtnClick(SmartDialog smartDialog, int i, Object o) {
                        apkInstaller.install();
                    }
                } : new DialogBtnClickListener() {
                    @Override
                    public void onBtnClick(SmartDialog smartDialog, int i, Object o) {

                    }
                }).showInActivity((Activity) context);

    }

    @Override
    protected void onNewVersionApkExists(Context context, ReadableVersionInfo readableVersionInfo, ApkInstaller apkInstaller) {
        if (mNotificationDialog == null) {
            mNotificationDialog = new NotificationDialog();
        }
        mNotificationDialog.title("")
                .cancelable(!readableVersionInfo.isForceUpdate())
                .message("新版本已准备好:" + readableVersionInfo.getVersionName())
                .confirmBtn("立即安装", new DialogBtnClickListener() {
                    @Override
                    public void onBtnClick(SmartDialog smartDialog, int i, Object o) {
                        apkInstaller.install();
                    }
                }).showInActivity((Activity) context);
    }

    @Override
    public void releaseContext() {
        super.releaseContext();
        mNotificationDialog = null;
    }
}
