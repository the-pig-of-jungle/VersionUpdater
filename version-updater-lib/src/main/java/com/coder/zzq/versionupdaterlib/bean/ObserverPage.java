package com.coder.zzq.versionupdaterlib.bean;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStoreOwner;

public class ObserverPage {
    private Context mActivityContext;
    private ViewModelStoreOwner mViewModelStoreOwner;
    private LifecycleOwner mLifecycleOwner;

    public Context getActivityContext() {
        return mActivityContext;
    }

    public ObserverPage setActivityContext(Context activityContext) {
        mActivityContext = activityContext;
        return this;
    }

    public ViewModelStoreOwner getViewModelStoreOwner() {
        return mViewModelStoreOwner;
    }

    public ObserverPage setViewModelStoreOwner(ViewModelStoreOwner viewModelStoreOwner) {
        mViewModelStoreOwner = viewModelStoreOwner;
        return this;
    }

    public LifecycleOwner getLifecycleOwner() {
        return mLifecycleOwner;
    }

    public ObserverPage setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        mLifecycleOwner = lifecycleOwner;
        return this;
    }

    public void release() {
        mActivityContext = null;
        mLifecycleOwner = null;
        mViewModelStoreOwner = null;
    }
}
