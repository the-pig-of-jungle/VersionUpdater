package com.coder.zzq.versionupdaterlib.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class RemoteVersion {
    private int mVersionCode;
    private String mVersionName;
    private String mVersionDesc;
    private boolean mForceUpdate;
    private String mApkUrl;

    public int getVersionCode() {
        return mVersionCode;
    }

    public RemoteVersion setVersionCode(int versionCode) {
        mVersionCode = versionCode;
        return this;
    }

    public String getVersionName() {
        return mVersionName;
    }

    public RemoteVersion setVersionName(String versionName) {
        mVersionName = versionName;
        return this;
    }

    public String getVersionDesc() {
        return mVersionDesc;
    }

    public RemoteVersion setVersionDesc(String versionDesc) {
        mVersionDesc = versionDesc;
        return this;
    }

    public boolean isForceUpdate() {
        return mForceUpdate;
    }

    public RemoteVersion setForceUpdate(boolean forceUpdate) {
        mForceUpdate = forceUpdate;
        return this;
    }

    public String getApkUrl() {
        return mApkUrl;
    }

    public RemoteVersion setApkUrl(String apkUrl) {
        mApkUrl = apkUrl;
        return this;
    }

    public ReadableVersionInfo createReadableOnlyVersionInfo() {
        return new ReadableVersionInfo(mVersionCode, mVersionName, mVersionDesc, mForceUpdate);
    }

    public String toJson() {
        JSONObject remoteVersion = new JSONObject();
        try {
            remoteVersion.put("mVersionCode", mVersionCode)
                    .put("mVersionName", mVersionName)
                    .put("mVersionDesc", mVersionDesc)
                    .put("mApkUrl", mApkUrl)
                    .put("mForceUpdate", mForceUpdate);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return remoteVersion.toString();
    }

    public static RemoteVersion fromJson(String json) {
        RemoteVersion remoteVersion = new RemoteVersion();
        try {
            JSONObject jsonObject = new JSONObject(json);
            remoteVersion.setVersionCode(jsonObject.getInt("mVersionCode"))
                    .setVersionName(jsonObject.getString("mVersionName"))
                    .setVersionDesc(jsonObject.getString("mVersionDesc"))
                    .setForceUpdate(jsonObject.getBoolean("mForceUpdate"))
                    .setApkUrl(jsonObject.getString("mApkUrl"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return remoteVersion;
    }
}
