package com.coder.zzq.versionupdaterlib;

import com.coder.zzq.versionupdaterlib.bean.DownloadEvent;

/**
 * Created by pig on 2018/1/27.
 */

public interface DownloadEventProcessor {
    void onReceiveDownloadEvent(DownloadEvent event);
}
