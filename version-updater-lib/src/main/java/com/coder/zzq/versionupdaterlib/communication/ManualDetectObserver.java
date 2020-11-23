package com.coder.zzq.versionupdaterlib.communication;

import androidx.lifecycle.Observer;

import com.coder.zzq.versionupdaterlib.bean.download_event.DownloadEvent;
import com.coder.zzq.versionupdaterlib.bean.download_event.DownloadRequestDuplicate;
import com.coder.zzq.versionupdaterlib.bean.download_event.LocalVersionIsUpToDate;

public abstract class ManualDetectObserver implements Observer<DownloadEvent> {

    public ManualDetectObserver() {

    }

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
