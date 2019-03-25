package com.coder.zzq.versionupdaterlib.bean;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.coder.zzq.versionupdaterlib.service.DownloadService;
import com.coder.zzq.versionupdaterlib.util.Utils;

import static com.coder.zzq.versionupdaterlib.service.DownloadService.UPDATER_SETTING;


/**
 * Created by pig on 2018/1/27.
 */

public class DownloadEvent {

    public static final int BEFORE_NEW_VERSION_DOWNLOAD = 1;
    public static final int AFTER_DOWNLOAD_HAS_STARTED = 2;
    public static final int LOCAL_VERSION_UP_TO_DATE = 3;
    public static final int DOWNLOAD_IN_PROGRESS = 4;
    public static final int DOWNLOAD_PAUSED = 5;
    public static final int DOWNLOAD_FAILED = 6;
    public static final int DOWNLOAD_COMPLETE = 7;


    private int mEvent;

    private UpdaterSetting mUpdaterSetting;
    private Uri mLocalApkFileUri;
    private DownloadedFileInfo mDownloadFileInfo;
    private int mDownloadPausedReason;
    private int mDownloadFailedReason;

    private int mDownloadId;

    public DownloadEvent(int event) {
        mEvent = event;
    }

    public DownloadEvent(int event, UpdaterSetting updaterSetting) {
        this(event);
        mUpdaterSetting = updaterSetting;
    }

    public DownloadEvent(int event, Uri uri) {
        this(event);
        mLocalApkFileUri = uri;
    }

    public DownloadEvent(int event, int reason) {
        this(event);
        switch (event) {
            case DOWNLOAD_PAUSED:
                mDownloadPausedReason = reason;
                break;
            case DOWNLOAD_FAILED:
                mDownloadFailedReason = reason;
                break;
        }
    }


    public int getEventType() {
        return mEvent;
    }

    public void setEventType(int event) {
        mEvent = event;
    }



    public void updateImmediately(Context context) {
        if (context != null && mUpdaterSetting != null) {
            Intent intent = new Intent(context,DownloadService.class);
            intent.putExtra(UPDATER_SETTING,mUpdaterSetting);
            context.startService(intent);
            mUpdaterSetting = null;
        }
    }


    public void installAfterDownloadComplete(Context context) {
        if (context != null && mLocalApkFileUri != null) {
            Utils.installApk(context, mLocalApkFileUri);
            mLocalApkFileUri = null;
        }
    }

    public String getNewVersionName() {
        return mUpdaterSetting == null ? "" : mUpdaterSetting.getRemoteVersionName();
    }

    public boolean isForceUpdate() {
        return mUpdaterSetting == null ? false : mUpdaterSetting.isForceUpdate();
    }

    public String getUpdateDesc() {
        return mUpdaterSetting == null ? "" : mUpdaterSetting.getUpdateDesc();
    }

    public Uri getLocalApkFileUri() {
        return mLocalApkFileUri;
    }
}
