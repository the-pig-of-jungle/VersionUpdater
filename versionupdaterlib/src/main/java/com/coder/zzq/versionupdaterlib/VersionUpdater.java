package com.coder.zzq.versionupdaterlib;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;

import com.coder.zzq.versionupdaterlib.bean.DownloadEvent;
import com.coder.zzq.versionupdaterlib.bean.LastDownloadInfo;
import com.coder.zzq.versionupdaterlib.bean.UpdaterSetting;
import com.coder.zzq.versionupdaterlib.listener.ActivityCallback;
import com.coder.zzq.versionupdaterlib.util.Utils;

import static com.coder.zzq.versionupdaterlib.bean.UpdaterSetting.DETECT_MODE_AUTO;
import static com.coder.zzq.versionupdaterlib.bean.UpdaterSetting.DETECT_MODE_MANUAL;


/**
 * Created by 喜欢、陪你看风景 on 2018/1/31.
 */

public class VersionUpdater implements UpdaterBuilder, IVersionUpdater {

    private static boolean sHasInitMsgSender;

    private Context mAppContext;
    private UpdaterSetting mUpdaterSetting;


    private VersionUpdater(Activity activity) {
        mAppContext = activity.getApplicationContext();
        initMessageSenderIfNotExists(activity);
        mUpdaterSetting = new UpdaterSetting();
        mUpdaterSetting.setLocalVersionCode(Utils.localVersionCode(activity));
        mUpdaterSetting.setNotificationVisibilityMode(DownloadManager.Request.VISIBILITY_VISIBLE);
    }


    private void initMessageSenderIfNotExists(Activity activity) {

        if (!sHasInitMsgSender) {
            if (!MessageSender.isRegister(activity)) {
                MessageSender.register(activity);
            }
            activity.getApplication().registerActivityLifecycleCallbacks(new ActivityCallback());
            sHasInitMsgSender = true;
        }

    }


    public static UpdaterBuilder builder(Activity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("参数activity不可为null！");
        }

        return new VersionUpdater(activity);
    }


    @Override
    public void check() {
        mUpdaterSetting.settingCheck();

        if (mUpdaterSetting.judgeIfLocalVersionUpToDate()) {
            MessageSender.sendMsg(new DownloadEvent(DownloadEvent.LOCAL_VERSION_UP_TO_DATE));
        } else {
            LastDownloadInfo lastDownloadInfo = LastDownloadInfo.fetchLastDownloadInfo(mAppContext);

            if (lastDownloadInfo == null || !lastDownloadInfo.isDelayUpdate()) {
                MessageSender.sendMsg(new DownloadEvent(DownloadEvent.BEFORE_NEW_VERSION_DOWNLOAD, mUpdaterSetting));
            }
        }
    }


    @Override
    public UpdaterBuilder remoteVersionCode(int versionCode) {
        if (versionCode < 1) {
            new IllegalArgumentException("版本号不可小于1");
        }
        mUpdaterSetting.setRemoteVersionCode(versionCode);
        return this;
    }

    @Override
    public UpdaterBuilder remoteApkUrl(String apkUrl) {
        mUpdaterSetting.setRemoteApkUri(Uri.parse(Utils.checkNullOrEmpty(apkUrl)));
        return this;
    }

    @Override
    public UpdaterBuilder isForceUpdate(boolean forceUpdate) {
        mUpdaterSetting.setForceUpdate(forceUpdate);
        return this;
    }

    @Override
    public UpdaterBuilder notificationVisibility(int visibilityMode) {
        switch (visibilityMode) {

            case DownloadManager.Request.VISIBILITY_VISIBLE:
            case DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED:
            case DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION:
            case DownloadManager.Request.VISIBILITY_HIDDEN:
                mUpdaterSetting.setNotificationVisibilityMode(visibilityMode);
                break;
            default:
                mUpdaterSetting.setNotificationVisibilityMode(visibilityMode);
                break;

        }

        return this;
    }

    @Override
    public UpdaterBuilder notificationTitle(String title) {
        mUpdaterSetting.setNotificationTitle(Utils.checkNullOrEmpty(title));
        return this;
    }

    @Override
    public UpdaterBuilder needNotifiedProgress(boolean need) {
        mUpdaterSetting.setNeedNotifiedProgress(need);
        return this;
    }

    @Override
    public UpdaterBuilder savedApkName(String apkName) {
        mUpdaterSetting.setSavedApkName(Utils.checkNullOrEmpty(apkName));
        return this;
    }

    @Override
    public UpdaterBuilder detectMode(int detectMode) {
        switch (detectMode) {
            case DETECT_MODE_AUTO:
                mUpdaterSetting.setDetectMode(DETECT_MODE_AUTO);
                break;
            case DETECT_MODE_MANUAL:
                mUpdaterSetting.setDetectMode(DETECT_MODE_MANUAL);
                break;
            default:
                mUpdaterSetting.setDetectMode(DETECT_MODE_MANUAL);
        }
        return this;
    }

    @Override
    public IVersionUpdater build() {
        return this;
    }

}
