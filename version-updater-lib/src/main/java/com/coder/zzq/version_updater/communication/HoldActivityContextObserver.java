package com.coder.zzq.version_updater.communication;

import android.content.Context;

import androidx.lifecycle.Observer;

import com.coder.zzq.version_updater.bean.update_event.VersionUpdateEvent;

import java.lang.ref.WeakReference;

public abstract class HoldActivityContextObserver implements Observer<VersionUpdateEvent> {
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
