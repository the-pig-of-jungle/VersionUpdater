package com.coder.zzq.version_updater.communication;

import androidx.lifecycle.Observer;

import com.coder.zzq.version_updater.bean.download_event.DownloadEvent;
import com.coder.zzq.version_updater.bean.download_event.DownloadRequestDuplicate;
import com.coder.zzq.version_updater.bean.download_event.LocalVersionIsUpToDate;

public abstract class AdditionalManualDetectObserver implements Observer<DownloadEvent> {

    @Override
    public final void onChanged(DownloadEvent downloadEvent) {
        if (downloadEvent instanceof LocalVersionIsUpToDate) {
            onLocalVersionIsUpToDate();
        } else if (downloadEvent instanceof DownloadRequestDuplicate) {
            onDownloadRequestDuplicate();
        }
    }

    protected abstract void onLocalVersionIsUpToDate();

    protected abstract void onDownloadRequestDuplicate();

}
