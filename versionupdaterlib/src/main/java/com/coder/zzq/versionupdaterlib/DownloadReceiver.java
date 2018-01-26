package com.coder.zzq.versionupdaterlib;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.coder.zzq.smartshow.toast.SmartToast;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static android.support.v4.content.FileProvider.getUriForFile;
import static com.coder.zzq.versionupdaterlib.Utils.getInfoOfDownloadFile;


/**
 * Created by pig on 2018/1/24.
 */

public class DownloadReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SmartToast.plainToast(context);
        switch (intent.getAction()) {
            case DownloadManager.ACTION_DOWNLOAD_COMPLETE:
                SmartToast.show("下载完成！");
                long downloadId = intent.getExtras().getLong(DownloadManager.EXTRA_DOWNLOAD_ID);
                DownloadFileInfo info = Utils.getInfoOfDownloadFile(context, downloadId);
                if (info.getDownloadStatus() == DownloadManager.STATUS_SUCCESSFUL && info.getUri() != null) {
                    Uri uri = info.getUri();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                        uri = FileProvider.getUriForFile(context,BuildConfig.FILE_PROVIDER_AUTHORITIES,new File(info.getUri().getEncodedPath()));
                    }

                    Utils.installApk(context,uri);
                }

                break;
        }
    }
}
