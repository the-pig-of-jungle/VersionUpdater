package com.coder.zzq.versionupdaterlib;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.coder.zzq.smartshow.toast.SmartToast;


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

                break;
        }
    }
}
