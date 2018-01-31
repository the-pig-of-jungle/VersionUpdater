package com.coder.zzq.versionupdaterlib.service;

import android.app.DownloadManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.Nullable;

import com.coder.zzq.versionupdaterlib.MessageSender;
import com.coder.zzq.versionupdaterlib.bean.DownloadEvent;
import com.coder.zzq.versionupdaterlib.bean.DownloadFileInfo;
import com.coder.zzq.versionupdaterlib.bean.OldDownloadInfo;
import com.coder.zzq.versionupdaterlib.bean.UpdaterSetting;
import com.coder.zzq.versionupdaterlib.util.Utils;

import java.io.File;

/**
 * Created by 朱志强 on 2018/1/27.
 */

public class DownloadService extends IntentService {
    public static final String UPDATER_SETTING = "updater_setting";


    public DownloadService() {
        super("DownloadApkService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        UpdaterSetting updaterSetting = intent.getParcelableExtra(UPDATER_SETTING);

        OldDownloadInfo oldDownloadInfo = Utils.fetchOldDownloadInfo(this);

        if (oldDownloadInfo != null && oldDownloadInfo.getVersionCode() == updaterSetting.getRemoteVersionCode()) {
            DownloadFileInfo downloadFileInfo = Utils.getInfoOfDownloadFile(this, oldDownloadInfo.getDownloadId());
            switch (downloadFileInfo.getDownloadStatus()) {
                case DownloadManager.STATUS_PENDING:
                case DownloadManager.STATUS_RUNNING:
                    MessageSender.sendMsg(new DownloadEvent(DownloadEvent.DOWNLOAD_IN_PROGRESS));
                    break;
                case DownloadManager.STATUS_PAUSED:
                    MessageSender.sendMsg(new DownloadEvent(DownloadEvent.DOWNLOAD_PAUSED, downloadFileInfo.getReason()));
                    break;
                case DownloadManager.STATUS_FAILED:
                    switch (downloadFileInfo.getReason()) {
                        case DownloadManager.ERROR_DEVICE_NOT_FOUND:
                        case DownloadManager.ERROR_INSUFFICIENT_SPACE:
                            MessageSender.sendMsg(new DownloadEvent(DownloadEvent.DOWNLOAD_FAILED, downloadFileInfo.getReason()));
                            Utils.getDownloadManager(this).remove(oldDownloadInfo.getDownloadId());
                            Utils.clearStoredOldDownloadInfo(this);
                            break;
                        case DownloadManager.ERROR_FILE_ALREADY_EXISTS:
                            File deletedFile = new File(downloadFileInfo.getUri().getEncodedPath());
                            deletedFile.delete();
                            clearAndDownloadAgain(updaterSetting, oldDownloadInfo);
                            break;
                        default:
                            clearAndDownloadAgain(updaterSetting, oldDownloadInfo);
                            break;
                    }
                case DownloadManager.STATUS_SUCCESSFUL:
                    File file = new File(downloadFileInfo.getUri().getEncodedPath());
                    if (file.exists() && file.length() == downloadFileInfo.getFileSizeBytes()) {
                        MessageSender.sendMsg(new DownloadEvent(DownloadEvent.APK_HAS_EXISTS, downloadFileInfo.getUri()));
                    } else {
                        clearAndDownloadAgain(updaterSetting, oldDownloadInfo);
                    }
                    break;
                case DownloadFileInfo.STATUS_NO_EXISTS:
                    clearAndDownloadAgain(updaterSetting, oldDownloadInfo);
                    break;
            }

        } else {
            clearAndDownloadAgain(updaterSetting, null);
        }
    }

    private void clearAndDownloadAgain(UpdaterSetting updaterSetting, OldDownloadInfo oldDownloadInfo) {

        if (oldDownloadInfo != null) {
            Utils.getDownloadManager(this).remove(oldDownloadInfo.getDownloadId());
            oldDownloadInfo.reset();
        } else {
            oldDownloadInfo = new OldDownloadInfo();
        }


        Utils.clearStoredOldDownloadInfo(this);


        DownloadManager.Request request = new DownloadManager.Request(updaterSetting.getRemoteApkUri())
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, updaterSetting.getSavedApkName())
                .setNotificationVisibility(updaterSetting.getNotificationVisibilityMode())
                .setTitle(updaterSetting.getNotificationTitle());
        long downloadId = Utils.getDownloadManager(this).enqueue(request);

        oldDownloadInfo.setDownloadId(downloadId);
        oldDownloadInfo.setVersionCode(updaterSetting.getRemoteVersionCode());
        oldDownloadInfo.setForceUpdate(updaterSetting.isForceUpdate());

        Utils.storeOldDownloadInfo(this, oldDownloadInfo);
    }


    public static void start(Context context, UpdaterSetting updaterSetting) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.putExtra(UPDATER_SETTING, updaterSetting);
        context.startService(intent);
    }
}
