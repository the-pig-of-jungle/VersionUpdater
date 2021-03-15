package com.coder.zzq.version_updater;

import androidx.annotation.IntDef;

import com.coder.zzq.version_updater.bean.ObserverPage;
import com.coder.zzq.version_updater.bean.RemoteVersion;
import com.coder.zzq.version_updater.condition.DefaultUpdateCondition;
import com.coder.zzq.version_updater.condition.UpdateCondition;

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

    private UpdateCondition mUpdateCondition = new DefaultUpdateCondition();

    private long mIgnorePeriod = Constants.NO_DEFINE;


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

    public UpdateCondition getUpdateCondition() {
        return mUpdateCondition;
    }

    public void setUpdateCondition(UpdateCondition updateCondition) {
        mUpdateCondition = updateCondition;
    }

    public long getIgnorePeriod() {
        return mIgnorePeriod;
    }

    public void setIgnorePeriod(long ignorePeriod) {
        mIgnorePeriod = ignorePeriod;
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
