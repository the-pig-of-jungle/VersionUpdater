package com.coder.zzq.versionupdaterlib.communication;

import android.content.Context;

import androidx.lifecycle.Observer;

import com.coder.zzq.versionupdaterlib.bean.download_event.DownloadEvent;

import java.lang.ref.WeakReference;

public abstract class HoldActivityContextObserver implements Observer<DownloadEvent> {
    private WeakReference<Context> mActivityContext;

    public HoldActivityContextObserver(Context activityContext) {
        mActivityContext = new WeakReference<>(activityContext);
    }


    public Context getActivityContext() {
        return mActivityContext.get();
    }

    public void releaseContext() {
        mActivityContext.clear();
        mActivityContext = null;
    }
}
