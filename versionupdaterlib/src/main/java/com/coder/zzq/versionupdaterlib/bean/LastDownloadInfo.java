package com.coder.zzq.versionupdaterlib.bean;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.coder.zzq.versionupdaterlib.service.DownloadService;
import com.coder.zzq.versionupdaterlib.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static android.R.attr.configChanges;
import static android.R.attr.mode;

/**
 * Created by 朱志强 on 2018/1/27.
 */

public class LastDownloadInfo {

    public static final String DOWNLOAD_INFO_PREF = "download_info_pref";
    public static final String DOWNLOAD_INFO = "download_info";

    public static final String DOWNLOAD_ID = "download_id";
    public static final String VERSION_CODE = "version_code";
    public static final String DELAY_UPDATE = "delay_update";

    private long mDownloadId;
    private int mVersionCode;
    private boolean mDelayUpdate;

    public LastDownloadInfo() {

    }


    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(DOWNLOAD_ID, mDownloadId)
                    .put(VERSION_CODE, mVersionCode)
                    .put(DELAY_UPDATE, mDelayUpdate);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }


    public static void store(Context context, LastDownloadInfo lastDownloadInfo) {
        downloadInfoPref(context).edit().putString(DOWNLOAD_INFO, lastDownloadInfo.toString()).commit();
    }

    public static LastDownloadInfo fetch(Context context) {
        LastDownloadInfo lastDownloadInfo = null;
        String jsonStr = downloadInfoPref(context).getString(DOWNLOAD_INFO, null);
        if (jsonStr != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                lastDownloadInfo = new LastDownloadInfo();
                lastDownloadInfo.mDownloadId = jsonObject.getLong(DOWNLOAD_ID);
                lastDownloadInfo.mVersionCode = jsonObject.getInt(VERSION_CODE);
                lastDownloadInfo.mDelayUpdate = jsonObject.getBoolean(DELAY_UPDATE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return lastDownloadInfo;
    }

    public static void clear(Context context) {
        downloadInfoPref(context).edit().putString(DOWNLOAD_INFO, null).commit();
    }

    private static SharedPreferences downloadInfoPref(Context context) {
        return context.getSharedPreferences(DOWNLOAD_INFO_PREF, Context.MODE_PRIVATE);
    }

    public long getDownloadId() {
        return mDownloadId;
    }

    public int getVersionCode() {
        return mVersionCode;
    }

    public boolean isDelayUpdate() {
        return mDelayUpdate;
    }

    public LastDownloadInfo setDownloadId(long downloadId) {
        mDownloadId = downloadId;
        return this;
    }

    public LastDownloadInfo setVersionCode(int versionCode) {
        mVersionCode = versionCode;
        return this;
    }

    public LastDownloadInfo setDelayUpdate(boolean delayUpdate) {
        mDelayUpdate = delayUpdate;
        return this;
    }

    public void reset() {
        mDownloadId = 0;
        mVersionCode = 0;
        mDelayUpdate = false;
    }
}
