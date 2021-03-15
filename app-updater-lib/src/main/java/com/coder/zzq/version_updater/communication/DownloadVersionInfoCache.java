package com.coder.zzq.version_updater.communication;

import android.content.Context;
import android.content.SharedPreferences;

import com.coder.zzq.version_updater.bean.IgnorePeriod;
import com.coder.zzq.toolkit.Toolkit;
import java.util.Date;

import static com.coder.zzq.version_updater.Constants.NO_DEFINE;


public class DownloadVersionInfoCache {
    public static final int NO_CACHE_VERSION = 0;
    public static final long NO_DOWNLOAD_ID = -1;
    public static final int NO_IGNORED_VERSION = 0;

    public static final String DOWNLOAD_VERSION_INFO = "download_version_info";
    public static final String ITEM_VERSION_CODE = "version_code";
    public static final String ITEM_DOWNLOAD_ID = "download_id";
    public static final String ITEM_DOWNLOAD_IN_BACKGROUND = "download_in_background";
    public static final String ITEM_IGNORED_PERIOD = "ignored_period";
    public static final String ITEM_IGNORED_TRIGGERED_MOMENT = "ignore_triggered_moment";

    private static SharedPreferences getDownloadVersionInfoPref() {
        return Toolkit.getContext().getSharedPreferences(DOWNLOAD_VERSION_INFO, Context.MODE_PRIVATE);
    }

    public static int getDownloadVersionCodeFromCache() {
        return getDownloadVersionInfoPref().getInt(ITEM_VERSION_CODE, NO_CACHE_VERSION);
    }

    public static boolean existsCachedDownloadVersion() {
        return getDownloadVersionCodeFromCache() != NO_CACHE_VERSION;
    }

    public static long getDownloadIdFromCache() {
        return getDownloadVersionInfoPref().getLong(ITEM_DOWNLOAD_ID, NO_DOWNLOAD_ID);
    }

    public static boolean isDownloadInBackground() {
        return getDownloadVersionInfoPref().getBoolean(ITEM_DOWNLOAD_IN_BACKGROUND, true);
    }

    public static void storeDownloadVersionInfoIntoCache(int versionCode, long downloadId, boolean downloadInBackground) {
        getDownloadVersionInfoPref().edit()
                .putInt(ITEM_VERSION_CODE, versionCode)
                .putLong(ITEM_DOWNLOAD_ID, downloadId)
                .putBoolean(ITEM_DOWNLOAD_IN_BACKGROUND, downloadInBackground)
                .apply();
    }


    public static final String IGNORED_VERSION_PREF = "ignored_version";

    private static SharedPreferences getIgnoredVersionPref() {
        return Toolkit.getContext().getSharedPreferences(IGNORED_VERSION_PREF, Context.MODE_PRIVATE);
    }

    public static boolean isVersionIgnored(int versionCode, long ignorePeriod) {

        return getIgnoredVersionPref().getInt(ITEM_VERSION_CODE, NO_IGNORED_VERSION) == versionCode
                && !ignoredPeriodExpire(ignorePeriod);
    }

    private static boolean ignoredPeriodExpire(long ignorePeriod) {
        if (ignorePeriod == NO_DEFINE) {
            ignorePeriod = getIgnoredVersionPref().getLong(ITEM_IGNORED_PERIOD, IgnorePeriod.never());
        } else {
            getIgnoredVersionPref().edit().putLong(ITEM_IGNORED_PERIOD, ignorePeriod).apply();
        }

        if (ignorePeriod == IgnorePeriod.always()) {
            return true;
        }

        if (ignorePeriod == IgnorePeriod.never()) {
            return false;
        }


        long ignoreMoment = getIgnoredVersionPref().getLong(ITEM_IGNORED_TRIGGERED_MOMENT, NO_DEFINE);
        return new Date().getTime() - ignoreMoment > ignorePeriod;
    }

    public static void ignoreVersion(int versionCode, long ignorePeriod) {
        getIgnoredVersionPref().edit()
                .putInt(ITEM_VERSION_CODE, versionCode)
                .putLong(ITEM_IGNORED_TRIGGERED_MOMENT, new Date().getTime())
                .putLong(ITEM_IGNORED_PERIOD, ignorePeriod)
                .apply();
    }

    public static void clearIgnoreVersion() {
        getDownloadVersionInfoPref().edit().clear().apply();
    }


}
