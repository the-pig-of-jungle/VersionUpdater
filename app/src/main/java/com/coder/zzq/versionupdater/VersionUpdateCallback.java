package com.coder.zzq.versionupdater;

import android.app.Activity;
import android.content.Context;

import com.coder.zzq.smartshow.dialog.DialogBtnClickListener;
import com.coder.zzq.smartshow.dialog.EnsureDialog;
import com.coder.zzq.smartshow.dialog.NotificationDialog;
import com.coder.zzq.smartshow.dialog.SmartDialog;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.coder.zzq.toolkit.Utils;
import com.coder.zzq.version_updater.bean.ApkInstaller;
import com.coder.zzq.version_updater.bean.DownloadProgress;
import com.coder.zzq.version_updater.bean.ReadableVersionInfo;
import com.coder.zzq.version_updater.bean.download_trigger.DownloadTrigger;
import com.coder.zzq.version_updater.bean.update_event.DownloadFailed;
import com.coder.zzq.version_updater.communication.AbstractVersionUpdateCallback;
import com.coder.zzq.versionupdater.annotations.ProcessResponder;

@ProcessResponder
public class VersionUpdateCallback extends AbstractVersionUpdateCallback {
    public VersionUpdateCallback(Context activityContext) {
        super(activityContext);
    }

    private NotificationDialog mNotificationDialog = new NotificationDialog();
    private EnsureDialog mEnsureDialog = new EnsureDialog();

    @Override
    protected void onDetectNewVersion(Context context, ReadableVersionInfo readableVersionInfo, DownloadTrigger downloadTrigger) {
        if (!readableVersionInfo.isForceUpdate()) {
            mEnsureDialog.title("发现新版本:" + readableVersionInfo.getVersionName())
                    .message(Utils.isEmpty(readableVersionInfo.getVersionDesc()) ? "1.修复已知问题" : readableVersionInfo.getVersionDesc())
                    .cancelableOnTouchOutside(false)
                    .confirmBtn("立即更新", new DialogBtnClickListener() {
                        @Override
                        public void onBtnClick(SmartDialog smartDialog, int i, Object o) {
                            if (readableVersionInfo.isForceUpdate()) {
                                downloadTrigger.downloadInForeground();
                            } else {
                                downloadTrigger.downloadInBackground();
                                SmartToast.showInCenter("开始后台更新");
                            }
                            smartDialog.dismiss();
                        }
                    })
                    .cancelBtn("暂不更新", new DialogBtnClickListener() {
                        @Override
                        public void onBtnClick(SmartDialog smartDialog, int i, Object o) {
                            smartDialog.dismiss();
                            downloadTrigger.cancelUpdate();
                        }
                    }).showInActivity((Activity) context);
            return;
        }
        mNotificationDialog.title("发现新版本:" + readableVersionInfo.getVersionName())
                .cancelable(!readableVersionInfo.isForceUpdate())
                .message(Utils.isEmpty(readableVersionInfo.getVersionDesc()) ? "1.修复已知问题" : readableVersionInfo.getVersionDesc())
                .confirmBtn("立即更新", new DialogBtnClickListener() {
                    @Override
                    public void onBtnClick(SmartDialog smartDialog, int i, Object o) {
                        if (readableVersionInfo.isForceUpdate()) {
                            downloadTrigger.downloadInForeground();
                        } else {
                            downloadTrigger.downloadInBackground();
                        }
                    }
                }).showInActivity((Activity) context);
    }

    @Override
    protected void onLocalVersionIsUpToDate(Context activityContext) {
        SmartToast.showInCenter("当前已是最新版本");
    }

    @Override
    protected void onDownloadRequestDuplicate(Context activityContext) {
        SmartToast.waiting("已在后台下载...");
    }

    @Override
    protected void onDownloadProgressChanged(Context context, DownloadProgress downloadProgress, ReadableVersionInfo readableVersionInfo, ApkInstaller apkInstaller) {
        if (mNotificationDialog == null) {
            mNotificationDialog = new NotificationDialog();
        }
        mNotificationDialog.title(downloadProgress.isComplete() ? "下载完成" : "正在下载")
                .cancelable(!readableVersionInfo.isForceUpdate())
                .cancelableOnTouchOutside(false)
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
                .cancelableOnTouchOutside(false)
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
    protected void onDownloadFailed(Context activityContext, DownloadFailed downloadFailed) {
        SmartToast.error(downloadFailed.description());
    }


    @Override
    public void releaseContext() {
        super.releaseContext();
        mNotificationDialog = null;
    }

}
