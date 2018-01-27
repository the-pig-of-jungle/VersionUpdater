package com.coder.zzq.versionupdaterlib;

/**
 * Created by pig on 2018/1/27.
 */

public interface UpdaterSetting {
    UpdaterSetting remoteVersionCode(int versionCode);
    UpdaterSetting remoteApkUrl(String apkUrl);
    UpdaterSetting notificationVisibility(int visibilityMode);
    UpdaterSetting notificationTitle(String title);
    UpdaterSetting needNotifiedProgress(boolean need);
    UpdaterSetting savedApkName(String apkName);
    void check();
}
