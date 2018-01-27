package com.coder.zzq.versionupdaterlib;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by pig on 2018/1/27.
 */

public interface DownloadEventProcessor {
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    void onReceiveDownloadEvent(DownloadEvent event);
}
