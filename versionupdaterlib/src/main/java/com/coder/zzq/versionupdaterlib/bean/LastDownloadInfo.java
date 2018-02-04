package com.coder.zzq.versionupdaterlib.bean;

import android.content.Context;
import android.content.SharedPreferences;

import com.coder.zzq.versionupdaterlib.service.DownloadService;
import com.coder.zzq.versionupdaterlib.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static android.R.attr.mode;

/**
 * Created by 朱志强 on 2018/1/27.
 */

public class LastDownloadInfo {

    public static final String DOWNLOAD_INFO_PREF = "download_info";
    public static final String DOWNLOAD_ID = "download_id";
    public static final String VERSION_CODE = "version_code";
    public static final String DELAY_UPDATE = "delay_update";


    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private long mDownloadId;
    private int mVersionCode;
    private boolean mDelayUpdate;


    private LastDownloadInfo(Context context) {

        if (context == null) {
            throw new IllegalArgumentException("context 不可为null！");
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences(DOWNLOAD_INFO_PREF,Context.MODE_PRIVATE);

        mEditor = context.getSharedPreferences(DOWNLOAD_INFO_PREF, Context.MODE_PRIVATE).edit();

    }

    private LastDownloadInfo() {

    }





    public static LastDownloadInfo update(Context context) {
        return new LastDownloadInfo(context);
    }

    public LastDownloadInfo downloadId(long downloadId) {
        mEditor.putLong(DOWNLOAD_ID, downloadId);
        return this;
    }

    public LastDownloadInfo versionCode(int versionCode) {
        mEditor.putInt(VERSION_CODE, versionCode);
        return this;
    }

    public LastDownloadInfo delayUpdate(boolean delayUpdate) {
        mEditor.putBoolean(DELAY_UPDATE, delayUpdate);
        return this;
    }

    public void store() {
        mEditor.commit();
        mEditor = null;
    }

    public static LastDownloadInfo fetch(Context context) {
        LastDownloadInfo lastDownloadInfo = null;
        SharedPreferences sharedPreferences = context.getSharedPreferences(DOWNLOAD_INFO_PREF, Context.MODE_PRIVATE);
        int versionCode = sharedPreferences.getInt(VERSION_CODE, -1);
        if (versionCode != -1) {
            lastDownloadInfo = new LastDownloadInfo();
            lastDownloadInfo.mDownloadId = sharedPreferences.getLong(DOWNLOAD_ID,0);
            lastDownloadInfo.mVersionCode = versionCode;
            lastDownloadInfo.mDelayUpdate = sharedPreferences.getBoolean(DELAY_UPDATE,false);
        }

        return lastDownloadInfo;
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

    public static void clear(Context context) {
        context.getSharedPreferences(DOWNLOAD_INFO_PREF, Context.MODE_PRIVATE).edit().clear().commit();
    }
}
