package com.coder.zzq.versionupdaterlib.bean;

import android.content.Context;
import android.net.Uri;

import com.coder.zzq.versionupdaterlib.MessageSender;
import com.coder.zzq.versionupdaterlib.service.DownloadService;
import com.coder.zzq.versionupdaterlib.util.Utils;


/**
 * Created by pig on 2018/1/27.
 */

public class DownloadEvent {

    public static final int BEFORE_NEW_VERSION_DOWNLOAD = 1;
    public static final int LOCAL_VERSION_UP_TO_DATE = 2;
    public static final int DOWNLOAD_IN_PROGRESS = 3;
    public static final int DOWNLOAD_PAUSED = 4;
    public static final int DOWNLOAD_FAILED = 5;
    public static final int DOWNLOAD_COMPLETE = 6;

    private int mEvent;

    private UpdaterSetting mUpdaterSetting;
    private Uri mLocalApkFileUri;
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

    public int getEvent() {
        return mEvent;
    }

    public void setEvent(int event) {
        mEvent = event;
    }


    public void delayUpdate(Context context) {
        if (mUpdaterSetting != null && !mUpdaterSetting.isForceUpdate()
                && mUpdaterSetting.getDetectMode() == UpdaterSetting.DETECT_MODE_AUTO) {
            LastDownloadInfo.update(context).delayUpdate(true).store();
        }
    }

    public void updateImmediately(Context context) {
        if (context != null && mUpdaterSetting != null) {
            DownloadService.start(context, mUpdaterSetting);
            mUpdaterSetting = null;
        }
    }


    public void installAfterDownloadComplete(Context context) {
        if (context != null && mLocalApkFileUri != null) {
            Utils.installApk(context, mLocalApkFileUri);
            mLocalApkFileUri = null;
        }
    }


}
