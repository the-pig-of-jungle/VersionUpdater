package com.coder.zzq.versionupdaterlib.communication;

import android.content.Context;
import android.content.SharedPreferences;

import com.coder.zzq.toolkit.Toolkit;

public class DownloadVersionInfoCache {
    public static final String DOWNLOAD_VERSION_INFO = "download_version_info";
    public static final String ITEM_VERSION_CODE = "version_code";
    public static final String ITEM_DOWNLOAD_ID = "download_id";

    private static SharedPreferences getDownloadVersionInfoPref() {
        return Toolkit.getContext().getSharedPreferences(DOWNLOAD_VERSION_INFO, Context.MODE_PRIVATE);
    }

    public static int getDownloadVersionCodeFromCache() {
        return getDownloadVersionInfoPref().getInt(ITEM_VERSION_CODE, 0);
    }

    public static long getDownloadIdFromCache() {
        return getDownloadVersionInfoPref().getLong(ITEM_DOWNLOAD_ID, -1);
    }

    public static void storeDownloadVersionInfoIntoCache(int versionCode, long downloadId) {
        getDownloadVersionInfoPref().edit()
                .putInt(ITEM_VERSION_CODE, versionCode)
                .putLong(ITEM_DOWNLOAD_ID, downloadId)
                .apply();
    }


    public static final String IGNORED_VERSION_PREF = "ignored_version";

    private static SharedPreferences getIgnoredVersionPref() {
        return Toolkit.getContext().getSharedPreferences(IGNORED_VERSION_PREF, Context.MODE_PRIVATE);
    }

    public static boolean isVersionIgnored(int versionCode) {
        return getIgnoredVersionPref().getInt(ITEM_VERSION_CODE, 0) == versionCode;
    }

    public static void setVersionIgnored(int versionCode) {
        getIgnoredVersionPref().edit()
                .putInt(ITEM_VERSION_CODE, versionCode)
                .apply();
    }
}
