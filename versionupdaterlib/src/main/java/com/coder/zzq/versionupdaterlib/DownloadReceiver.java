package com.coder.zzq.versionupdaterlib;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.coder.zzq.smartshow.toast.SmartToast;

import java.io.File;
import java.io.FileNotFoundException;


/**
 * Created by pig on 2018/1/24.
 */

public class DownloadReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SmartToast.plainToast(context);
        switch (intent.getAction()){
            case DownloadManager.ACTION_DOWNLOAD_COMPLETE:
                SmartToast.show("下载完成！");
                long reference = intent.getExtras().getLong(DownloadManager.EXTRA_DOWNLOAD_ID);
                DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(reference);
                Cursor cursor = downloadManager.query(query);
                try {
                    if (cursor.moveToFirst()){
                        String uriStr = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                        Intent i = new Intent();
                        i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        i.setAction(Intent.ACTION_VIEW);
                        Uri uri = FileProvider.getUriForFile(context,BuildConfig.APPLICATION_ID + ".file_provider",new File(Uri.parse(uriStr).getEncodedPath()));
                        i.setDataAndType(uri, "application/vnd.android.package-archive");
                        context.startActivity(i);
                    }
                }finally {
                    if (cursor != null){
                        cursor.close();
                    }
                }

                break;
        }
    }
}
