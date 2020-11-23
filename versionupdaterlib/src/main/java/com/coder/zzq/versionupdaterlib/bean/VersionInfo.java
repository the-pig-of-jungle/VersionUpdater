package com.coder.zzq.versionupdaterlib.bean;

public class VersionInfo {
    private final int mVersionCode;
    private final String mVersionName;
    private final String mVersionDesc;
    private final boolean mForceUpdate;

    public VersionInfo(int versionCode, String versionName, String versionDesc, boolean forceUpdate) {
        mVersionCode = versionCode;
        mVersionName = versionName;
        mVersionDesc = versionDesc;
        mForceUpdate = forceUpdate;
    }

    public int getVersionCode() {
        return mVersionCode;
    }


    public String getVersionName() {
        return mVersionName;
    }


    public String getVersionDesc() {
        return mVersionDesc;
    }

    public boolean isForceUpdate() {
        return mForceUpdate;
    }

}
