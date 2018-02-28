package com.coder.zzq.versionupdaterlib.service;

import android.app.DownloadManager;
import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.Nullable;

import com.coder.zzq.versionupdaterlib.MessageSender;
import com.coder.zzq.versionupdaterlib.bean.DownloadEvent;
import com.coder.zzq.versionupdaterlib.bean.DownloadedFileInfo;
import com.coder.zzq.versionupdaterlib.bean.LocalDownloadInfo;
import com.coder.zzq.versionupdaterlib.bean.UpdaterSetting;
import com.coder.zzq.versionupdaterlib.util.Utils;

import java.util.Date;

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
        LocalDownloadInfo localDownloadInfo = Utils.getLocalDownloadInfo(this);

        if (localDownloadInfo == null || localDownloadInfo.getVersionCode() != updaterSetting.getRemoteVersionCode()) {
            download(updaterSetting, localDownloadInfo);
            return;
        }

        DownloadedFileInfo downloadedFileInfo = Utils.getInfoOfDownloadFile(this, localDownloadInfo.getDownloadId());



    }

    private void processAutoDetectUpdate(UpdaterSetting updaterSetting,
                                         DownloadedFileInfo downloadedFileInfo,LocalDownloadInfo localDownloadInfo) {
        switch (downloadedFileInfo.getDownloadStatus()) {
            case DownloadManager.STATUS_FAILED:
                switch (downloadedFileInfo.getReason()) {
                    case DownloadManager.ERROR_DEVICE_NOT_FOUND:
                    case DownloadManager.ERROR_INSUFFICIENT_SPACE:
                        MessageSender.sendMsg(new DownloadEvent(DownloadEvent.DOWNLOAD_FAILED, downloadedFileInfo.getReason()));
                        break;
                    case DownloadManager.ERROR_FILE_ALREADY_EXISTS:
                        updaterSetting.setSavedApkName(updaterSetting.getBaseApkName() + new Date().toString());
                        download(updaterSetting, localDownloadInfo);
                        break;
                    default:
                        download(updaterSetting, localDownloadInfo);
                        break;
                }
                break;
            case DownloadManager.STATUS_SUCCESSFUL:

                break;
        }
    }

    //    @Override
//    protected void onHandleIntent(@Nullable Intent intent) {
//
//        UpdaterSetting updaterSetting = intent.getParcelableExtra(UPDATER_SETTING);
//
//        LastDownloadInfo lastDownloadInfo = LastDownloadInfo.fetch(this);
//
//        if (lastDownloadInfo == null || updaterSetting.getRemoteVersionCode() != lastDownloadInfo.getVersionCode()) {
//            LastDownloadInfo.clear(this);
//            download(updaterSetting,lastDownloadInfo);
//            return;
//        }
//
//        DownloadFileInfo downloadFileInfo = Utils.getInfoOfDownloadFile(this,lastDownloadInfo.getDownloadId());
//
//        switch (updaterSetting.getDetectMode()) {
//            case UpdaterSetting.DETECT_MODE_AUTO:
//                processAutoDetect(updaterSetting, downloadFileInfo);
//                break;
//            case UpdaterSetting.DETECT_MODE_MANUAL:
//
//                break;
//        }
//
//        if (lastDownloadInfo != null && lastDownloadInfo.getVersionCode() == updaterSetting.getRemoteVersionCode()) {
//
//            switch (downloadFileInfo.getDownloadStatus()) {
//                case DownloadManager.STATUS_PENDING:
//                case DownloadManager.STATUS_RUNNING:
//                    MessageSender.sendMsg(new DownloadEvent(DownloadEvent.DOWNLOAD_IN_PROGRESS));
//                    break;
//                case DownloadManager.STATUS_PAUSED:
//                    MessageSender.sendMsg(new DownloadEvent(DownloadEvent.DOWNLOAD_PAUSED, downloadFileInfo.getReason()));
//                    break;
//                case DownloadManager.STATUS_FAILED:
//                    switch (downloadFileInfo.getReason()) {
//                        case DownloadManager.ERROR_DEVICE_NOT_FOUND:
//                        case DownloadManager.ERROR_INSUFFICIENT_SPACE:
//                            MessageSender.sendMsg(new DownloadEvent(DownloadEvent.DOWNLOAD_FAILED, downloadFileInfo.getReason()));
//                            break;
//                        case DownloadManager.ERROR_FILE_ALREADY_EXISTS:
//                            updaterSetting.setSavedApkName(updaterSetting.getBaseApkName() + new Date().toString());
//                            download(updaterSetting, lastDownloadInfo);
//                            break;
//                        default:
//                            download(updaterSetting, lastDownloadInfo);
//                            break;
//                    }
//                case DownloadManager.STATUS_SUCCESSFUL:
//
//                    File file = new File(downloadFileInfo.getUri().getPath());
//
//                    if (file.exists() && file.length() == downloadFileInfo.getFileSizeBytes()) {
//
//                        Utils.installApk(this, downloadFileInfo.getUri());
//                    } else {
//                        download(updaterSetting, lastDownloadInfo);
//                    }
//                    break;
//                case DownloadFileInfo.STATUS_NO_EXISTS:
//                    download(updaterSetting, lastDownloadInfo);
//                    break;
//            }
//
//        } else {
//            download(updaterSetting, lastDownloadInfo);
//        }
//    }
//
//    private void processAutoDetect(UpdaterSetting updaterSetting, DownloadFileInfo downloadFileInfo) {
//
//    }
//
    private void download(UpdaterSetting updaterSetting, LocalDownloadInfo localDownloadInfo) {

        if (localDownloadInfo != null) {
            Utils.getDownloadManager(this).remove(localDownloadInfo.getDownloadId());
            Utils.clearLocalDownloadInfo(this);
        }else {
            localDownloadInfo = new LocalDownloadInfo();
        }

        DownloadManager.Request request = new DownloadManager.Request(updaterSetting.getRemoteApkUri())
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, updaterSetting.getSavedApkName())
                .setNotificationVisibility(updaterSetting.getNotificationVisibilityMode())
                .setTitle(updaterSetting.getNotificationTitle());
        final long downloadId = Utils.getDownloadManager(this).enqueue(request);
        localDownloadInfo.setDownloadId(downloadId).setVersionCode(updaterSetting.getRemoteVersionCode());
        
        Utils.storeDownloadInfoIntoLocal(this, localDownloadInfo);

        MessageSender.sendMsg(new DownloadEvent(DownloadEvent.AFTER_DOWNLOAD_HAS_STARTED, updaterSetting));

    }
//
//
//    public static void start(Context context, UpdaterSetting updaterSetting) {
//        Intent intent = new Intent(context, DownloadService.class);
//        intent.putExtra(UPDATER_SETTING, updaterSetting);
//        context.startService(intent);
//    }
}
