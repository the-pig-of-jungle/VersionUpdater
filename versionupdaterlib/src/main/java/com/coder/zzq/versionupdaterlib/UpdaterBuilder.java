package com.coder.zzq.versionupdaterlib;

/**
 * Created by 朱志强 on 2018/1/31.
 */

public interface UpdaterBuilder {

    UpdaterBuilder remoteVersionCode(int versionCode);

    UpdaterBuilder remoteApkUrl(String apkUrl);

    UpdaterBuilder isForceUpdate(boolean forceUpdate);

    UpdaterBuilder notificationVisibility(int visibilityMode);

    UpdaterBuilder notificationTitle(String title);

    UpdaterBuilder needNotifiedProgress(boolean need);

    UpdaterBuilder savedApkName(String apkName);

    UpdaterBuilder detectMode(int detectMode);

    IVersionUpdater build();
}
