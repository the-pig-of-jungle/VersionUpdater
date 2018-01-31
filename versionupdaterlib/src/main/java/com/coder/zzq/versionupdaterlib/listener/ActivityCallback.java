package com.coder.zzq.versionupdaterlib.listener;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.coder.zzq.versionupdaterlib.MessageSender;

/**
 * Created by 喜欢、陪你看风景 on 2018/1/31.
 */

public class ActivityCallback implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (!MessageSender.isRegister(activity)) {
            MessageSender.register(activity);
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (MessageSender.isRegister(activity)) {
            MessageSender.unregister(activity);
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
