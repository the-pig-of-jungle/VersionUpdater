package com.coder.zzq.versionupdaterlib;

import androidx.annotation.IntDef;

import com.coder.zzq.versionupdaterlib.bean.ObserverPage;
import com.coder.zzq.versionupdaterlib.bean.RemoteVersion;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class CheckConfig {
    //自动检测模式
    public static final int DETECT_MODE_AUTO = 0;
    //手动检测模式
    public static final int DETECT_MODE_MANUAL = 1;

    @Retention(RetentionPolicy.CLASS)
    @IntDef({DETECT_MODE_AUTO, DETECT_MODE_MANUAL})
    public @interface DetectMode {
    }

    private RemoteVersion mRemoteVersion;
    private ObserverPage mObserverPage;
    @DetectMode
    private int mDetectMode = DETECT_MODE_AUTO;

    private boolean mNotificationVisibility;


    public CheckConfig() {
        mRemoteVersion = new RemoteVersion();
        mObserverPage = new ObserverPage();
    }

    public RemoteVersion getRemoteVersion() {
        return mRemoteVersion;
    }

    public CheckConfig setRemoteVersion(RemoteVersion remoteVersion) {
        mRemoteVersion = remoteVersion;
        return this;
    }

    public int getDetectMode() {
        return mDetectMode;
    }

    public CheckConfig setDetectMode(int detectMode) {
        mDetectMode = detectMode;
        return this;
    }

    public boolean isNotificationVisibility() {
        return mNotificationVisibility;
    }

    public CheckConfig setNotificationVisibility(boolean notificationVisibility) {
        mNotificationVisibility = notificationVisibility;
        return this;
    }

    public ObserverPage getObserverPage() {
        return mObserverPage;
    }

    public CheckConfig setObserverPage(ObserverPage observerPage) {
        mObserverPage = observerPage;
        return this;
    }
}
