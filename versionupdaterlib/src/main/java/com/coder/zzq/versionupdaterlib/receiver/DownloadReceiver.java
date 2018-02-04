package com.coder.zzq.versionupdaterlib.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.coder.zzq.versionupdaterlib.MessageSender;
import com.coder.zzq.versionupdaterlib.bean.DownloadEvent;
import com.coder.zzq.versionupdaterlib.bean.DownloadFileInfo;
import com.coder.zzq.versionupdaterlib.bean.LastDownloadInfo;
import com.coder.zzq.versionupdaterlib.util.Utils;

/**
 * Created by 朱志强 on 2018/1/24.
 */

public class DownloadReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SmartToast.plainToast(context);
        switch (intent.getAction()) {
            case DownloadManager.ACTION_DOWNLOAD_COMPLETE:
                long downloadId = intent.getExtras().getLong(DownloadManager.EXTRA_DOWNLOAD_ID);

                if (downloadId == LastDownloadInfo.fetch(context).getDownloadId()) {
                    DownloadFileInfo info = Utils.getInfoOfDownloadFile(context, downloadId);
                    if (info.getDownloadStatus() == DownloadManager.STATUS_SUCCESSFUL && info.getUri() != null) {
                        MessageSender.sendMsg(new DownloadEvent(DownloadEvent.DOWNLOAD_COMPLETE, info.getUri()));
                    }
                }
                break;
        }
    }
}
