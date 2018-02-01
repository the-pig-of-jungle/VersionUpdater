package com.coder.zzq.versionupdaterlib.bean;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 朱志强 on 2018/1/27.
 */

public class LastDownloadInfo {


    public static final String DOWNLOAD_ID = "download_id";
    public static final String VERSION_CODE = "version_code";
    public static final String FORCE_UPDATE = "force_update";

    private long mDownloadId;
    private int mVersionCode;
    private boolean mIsForceUpdate;

    public LastDownloadInfo() {

    }

    public LastDownloadInfo(long downloadId, int versionCode) {
        mDownloadId = downloadId;
        mVersionCode = versionCode;
    }

    public LastDownloadInfo(String jsonStr) {
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            mDownloadId = jsonObject.getLong(DOWNLOAD_ID);
            mVersionCode = jsonObject.getInt(VERSION_CODE);
            mIsForceUpdate = jsonObject.getBoolean(FORCE_UPDATE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(DOWNLOAD_ID, mDownloadId)
                    .put(VERSION_CODE, mVersionCode)
                    .put(FORCE_UPDATE, mIsForceUpdate);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }

    public long getDownloadId() {
        return mDownloadId;
    }

    public void setDownloadId(long downloadId) {
        mDownloadId = downloadId;
    }

    public int getVersionCode() {
        return mVersionCode;
    }

    public void setVersionCode(int versionCode) {
        mVersionCode = versionCode;
    }

    public boolean isForceUpdate() {
        return mIsForceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        mIsForceUpdate = forceUpdate;
    }


    public void reset() {
        mDownloadId = 0;
        mVersionCode = 0;
        mIsForceUpdate = false;
    }


    public static final String LAST_VERSION_INFO_PREF = "last_version_info";
    public static final String VERSION_INFO = "version_info";


    private static SharedPreferences lastVersionInfoPref(Context context) {
        return context.getSharedPreferences(LAST_VERSION_INFO_PREF, Context.MODE_PRIVATE);
    }

    public static void storeLastDownloadInfo(Context context, LastDownloadInfo lastDownloadInfo) {
        lastVersionInfoPref(context).edit().putString(VERSION_INFO, lastDownloadInfo.toString()).commit();
    }

    public static LastDownloadInfo fetchLastDownloadInfo(Context context) {
        String jsonStr = lastVersionInfoPref(context).getString(VERSION_INFO, null);
        return jsonStr == null ? null : new LastDownloadInfo(jsonStr);
    }

    public static void clearStoredOldDownloadInfo(Context context) {
        lastVersionInfoPref(context).edit().putString(VERSION_INFO, null).commit();
    }
}
