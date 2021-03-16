package com.coder.zzq.version_updater.bean.download_trigger;

public interface DownloadTrigger {
    void downloadInForeground();

    void downloadInBackground();

    void cancelUpdate();
}
