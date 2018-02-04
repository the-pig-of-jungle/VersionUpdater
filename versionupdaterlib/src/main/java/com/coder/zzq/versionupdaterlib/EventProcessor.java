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


    private EventProcessor(Activity activity,DownloadEvent event){
        mActivity = activity;
        mDownloadEvent = event;
    }

    public static EventProcessor create(Activity activity,DownloadEvent event){
        return new EventProcessor(activity,event);
    }

    public void process(Callback callback){

        switch (mDownloadEvent.getEvent()){
            case DownloadEvent.LOCAL_VERSION_UP_TO_DATE:
                callback.localVersionUpToDate(mActivity,mDownloadEvent);
                break;
            case DownloadEvent.BEFORE_NEW_VERSION_DOWNLOAD:
                callback.beforeNewVersionDownload(mActivity,mDownloadEvent);
                break;
            case DownloadEvent.DOWNLOAD_IN_PROGRESS:
                callback.downloadInProgress(mActivity,mDownloadEvent);
                break;
            case DownloadEvent.DOWNLOAD_PAUSED:
                callback.downloadPaused(mActivity,mDownloadEvent);
                break;
            case DownloadEvent.DOWNLOAD_FAILED:
                callback.downloadFailed(mActivity,mDownloadEvent);
                break;
            case DownloadEvent.DOWNLOAD_COMPLETE:
                callback.downloadComplete(mActivity,mDownloadEvent);
                break;
        }

        MessageSender.removeDownloadEvent(mDownloadEvent);
        mActivity = null;
        mDownloadEvent = null;
    }

    public interface Callback{
        public void localVersionUpToDate(Activity activity,DownloadEvent downloadEvent);

        public void beforeNewVersionDownload(Activity activity,DownloadEvent event);

        public void downloadInProgress(Activity activity,DownloadEvent event);

        public void downloadPaused(Activity activity,DownloadEvent event);

        public void downloadFailed(Activity activity,DownloadEvent event);

        public void downloadComplete(Activity activity,DownloadEvent event);
    }


}
