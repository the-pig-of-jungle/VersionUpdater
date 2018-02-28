package com.coder.zzq.versionupdaterlib;

/**
 * Created by 朱志强 on 2018/1/31.
 */

public interface UpdaterBuilder {

    UpdaterBuilder remoteVersionCode(int versionCode);

    UpdaterBuilder remoteVersionName(String versionName);

    UpdaterBuilder remoteApkUrl(String apkUrl);

    UpdaterBuilder updateDesc(String desc);

    UpdaterBuilder isForceUpdate(boolean forceUpdate);

    UpdaterBuilder notificationVisibility(int visibilityMode);

    UpdaterBuilder notificationTitle(String title);

    UpdaterBuilder savedApkName(String apkName);

    UpdaterBuilder detectMode(int detectMode);

    IVersionUpdater build();


}
