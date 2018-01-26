package com.coder.zzq.versionupdaterlib;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

/**
 * Created by pig on 2018/1/24.
 */

public class Utils {

    public static DownloadFileInfo getInfoOfDownloadFile(Context context, long downloadId) {

        DownloadManager downloadManager = (DownloadManager) context.getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);

        DownloadFileInfo downloadFileInfo = new DownloadFileInfo();
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

            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }


        return downloadFileInfo;
    }


    public static void installApk(Context context,Uri uri){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW)
                .setDataAndType(uri,"application/vnd.android.package-archive")
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(intent);
    }

}
