package com.coder.zzq.versionupdaterlib.util;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.coder.zzq.versionupdaterlib.BuildConfig;
import com.coder.zzq.versionupdaterlib.bean.DownloadedFileInfo;
import com.coder.zzq.versionupdaterlib.bean.LocalDownloadInfo;

import java.io.File;

/**
 * Created by pig on 2018/1/24.
 */

public class Utils {

    public static DownloadManager getDownloadManager(Context context) {
        return (DownloadManager) context.getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
    }

    public static DownloadedFileInfo getInfoOfDownloadFile(Context context, long downloadId) {

        DownloadManager downloadManager = getDownloadManager(context);

        DownloadedFileInfo downloadFileInfo = new DownloadedFileInfo();
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        Cursor cursor = downloadManager.query(query);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                downloadFileInfo.setDownloadStatus(status);
                String uriStr = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                if (!TextUtils.isEmpty(uriStr)) {
                    downloadFileInfo.setUri(Uri.parse(uriStr));
                }
                int sizeBytes = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                downloadFileInfo.setFileSizeBytes(sizeBytes);

                int reason = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_REASON));

                downloadFileInfo.setReason(reason);

            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }


        return downloadFileInfo;
    }


    public static void installApk(Context context, Uri uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, "", new File(uri.getPath()));
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW)
                .setDataAndType(uri, "application/vnd.android.package-archive")
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(intent);
    }


    public static String checkNullOrEmpty(String str) {

        if (str == null || str.trim().length() == 0) {
            throw new IllegalArgumentException("参数字符串不可为null或者空白！");
        }

        return str;
    }

    public static <T> T checkNull(T obj, String errorTip) {
        if (obj == null) {
            throw new IllegalArgumentException(errorTip);
        }

        return obj;
    }


    public static int localVersionCode(Context context) {
        int versionCode = 1;

        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }


    public static final String DOWNLOAD_INFO_PREF = "download_info_pref";
    public static final String ITEM_DOWNLOAD_INFO = "download_info";

    private static SharedPreferences downloadInfoPref(Context context) {
        return context.getSharedPreferences(DOWNLOAD_INFO_PREF, Context.MODE_PRIVATE);
    }

    public static LocalDownloadInfo getLocalDownloadInfo(Context context) {
        String jsonStr = downloadInfoPref(context).getString(ITEM_DOWNLOAD_INFO, null);
        return jsonStr == null ? null : new LocalDownloadInfo(jsonStr);
    }

    public static void storeDownloadInfoIntoLocal(Context context, LocalDownloadInfo localDownloadInfo) {
        downloadInfoPref(context).edit().putString(ITEM_DOWNLOAD_INFO,
                localDownloadInfo == null ? null : localDownloadInfo.toString()).commit();
    }

    public static void clearLocalDownloadInfo(Context context) {
        storeDownloadInfoIntoLocal(context, null);
    }
}
