package com.coder.zzq.versionupdaterlib.bean;

import androidx.annotation.IntDef;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class DownloadTaskInfo {

    //自动检测模式
    public static final int DETECT_MODE_AUTO = 0;
    //手动检测模式
    public static final int DETECT_MODE_MANUAL = 1;

    @Retention(RetentionPolicy.CLASS)
    @IntDef({DETECT_MODE_AUTO, DETECT_MODE_MANUAL})
    public @interface DetectMode {
    }


    private int mRemoteVersionCode;
    private String mRemoteVersionName;
    private String mRemoteVersionDesc;
    private boolean mForceUpdate;
    private String mRemoteApkUrl;
    private boolean mNotificationVisibility;
    @DetectMode
    private int mDetectMode;

    public DownloadTaskInfo() {

    }

    public static DownloadTaskInfo fromJson(String json) {
        DownloadTaskInfo downloadTaskInfo = new DownloadTaskInfo();
        try {
            JSONObject jsonObject = new JSONObject(json);
            downloadTaskInfo.setRemoteVersionCode(jsonObject.getInt("mRemoteVersionCode"));
            downloadTaskInfo.setRemoteVersionName(jsonObject.getString("mRemoteVersionName"));
            downloadTaskInfo.setRemoteVersionDesc(jsonObject.getString("mRemoteVersionDesc"));
            downloadTaskInfo.setForceUpdate(jsonObject.getBoolean("mForceUpdate"));
            downloadTaskInfo.setRemoteApkUrl(jsonObject.getString("mRemoteApkUrl"));
            downloadTaskInfo.setNotificationVisibility(jsonObject.getBoolean("mNotificationVisibility"));
            downloadTaskInfo.setDetectMode(jsonObject.getInt("mDetectMode"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return downloadTaskInfo;
    }

    public int getRemoteVersionCode() {
        return mRemoteVersionCode;
    }

    public void setRemoteVersionCode(int remoteVersionCode) {
        mRemoteVersionCode = remoteVersionCode;
    }

    public String getRemoteVersionName() {
        return mRemoteVersionName;
    }

    public void setRemoteVersionName(String remoteVersionName) {
        mRemoteVersionName = remoteVersionName;
    }

    public String getRemoteApkUrl() {
        return mRemoteApkUrl;
    }

    public void setRemoteApkUrl(String remoteApkUrl) {
        mRemoteApkUrl = remoteApkUrl;
    }

    public String getRemoteVersionDesc() {
        return mRemoteVersionDesc;
    }

    public void setRemoteVersionDesc(String remoteVersionDesc) {
        mRemoteVersionDesc = remoteVersionDesc;
    }

    public boolean isForceUpdate() {
        return mForceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        mForceUpdate = forceUpdate;
    }

    public boolean isNotificationVisibility() {
        return mNotificationVisibility;
    }

    public void setNotificationVisibility(boolean notificationVisibility) {
        mNotificationVisibility = notificationVisibility;
    }

    public int getDetectMode() {
        return mDetectMode;
    }

    public void setDetectMode(int detectMode) {
        mDetectMode = detectMode;
    }

    public String toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mRemoteVersionCode", mRemoteVersionCode)
                    .put("mRemoteVersionName", mRemoteVersionName)
                    .put("mRemoteVersionDesc", mRemoteVersionDesc)
                    .put("mForceUpdate", mForceUpdate)
                    .put("mRemoteApkUrl", mRemoteApkUrl)
                    .put("mNotificationVisibility", mNotificationVisibility)
                    .put("mDetectMode", mDetectMode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public VersionInfo fetchBaseVersionInfo() {
        return new VersionInfo(mRemoteVersionCode, mRemoteVersionName, mRemoteVersionDesc, mForceUpdate);
    }
}
