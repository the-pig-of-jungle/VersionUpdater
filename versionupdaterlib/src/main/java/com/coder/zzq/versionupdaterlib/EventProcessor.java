package com.coder.zzq.versionupdaterlib;

import android.app.Activity;
import android.content.Context;

import com.coder.zzq.versionupdaterlib.bean.DownloadEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by pig on 2018/2/4.
 */

public class EventProcessor {

    private Activity mActivity;
    private DownloadEvent mDownloadEvent;


    private EventProcessor(Activity activity, DownloadEvent downloadEvent) {
        mActivity = activity;
        mDownloadEvent = downloadEvent;
    }

    public static EventProcessor create(Activity activity, DownloadEvent downloadEvent) {
        return new EventProcessor(activity, downloadEvent);
    }

    public void process(Callback callback) {

        switch (mDownloadEvent.getEventType()) {
            case DownloadEvent.LOCAL_VERSION_UP_TO_DATE:
                callback.localVersionUpToDate(mActivity, mDownloadEvent);
                break;
            case DownloadEvent.AFTER_DOWNLOAD_HAS_STARTED:
                callback.afterDownloadHasStarted(mActivity,mDownloadEvent);
                break;
            case DownloadEvent.BEFORE_NEW_VERSION_DOWNLOAD:
                callback.beforeNewVersionDownload(mActivity, mDownloadEvent);
                break;
            case DownloadEvent.DOWNLOAD_IN_PROGRESS:
                callback.downloadInProgress(mActivity, mDownloadEvent);
                break;
            case DownloadEvent.DOWNLOAD_PAUSED:
                callback.downloadPaused(mActivity, mDownloadEvent);
                break;
            case DownloadEvent.DOWNLOAD_FAILED:
                callback.downloadFailed(mActivity, mDownloadEvent);
                break;
            case DownloadEvent.DOWNLOAD_COMPLETE:
                callback.downloadComplete(mActivity, mDownloadEvent);
                break;
        }

        MessageSender.removeDownloadEvent(mDownloadEvent);
        mActivity = null;
        mDownloadEvent = null;
    }

    public interface Callback {
        void localVersionUpToDate(final Activity activity, DownloadEvent downloadEvent);

        void beforeNewVersionDownload(final Activity activity, DownloadEvent downloadEvent);

        void afterDownloadHasStarted(final Activity activity,DownloadEvent downloadEvent);

        void downloadComplete(final Activity activity, DownloadEvent downloadEvent);

        void downloadInProgress(final Activity activity, DownloadEvent downloadEvent);

        void downloadPaused(final Activity activity, DownloadEvent downloadEvent);

        void downloadFailed(final Activity activity, DownloadEvent downloadEvent);


    }


}
