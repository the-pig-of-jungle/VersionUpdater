package com.coder.zzq.versionupdaterlib;

import android.content.Context;
import android.net.Uri;

/**
 * Created by pig on 2018/1/27.
 */

public class DownloadEvent {

    public static final int HAS_NEW_VERSION = 1;
    public static final int DOWNLOAD_COMPLETE = 2;

    private int mEvent;

    private VersionUpdater mVersionUpdater;
    private Uri mUri;

    public DownloadEvent(int event, VersionUpdater versionUpdater) {
        mEvent = event;
        mVersionUpdater = versionUpdater;
    }

    public DownloadEvent(int event, Uri uri) {
        mEvent = event;
        mUri = uri;
    }

    public int getEvent() {
        return mEvent;
    }

    public void setEvent(int event) {
        mEvent = event;
    }


    public void startDownload() {
        if (mVersionUpdater != null) {
            mVersionUpdater.startDownload();
        }
        mVersionUpdater = null;
    }

    public void installApk(Context context) {
        Utils.installApk(context, mUri);
        mUri = null;
    }
}
