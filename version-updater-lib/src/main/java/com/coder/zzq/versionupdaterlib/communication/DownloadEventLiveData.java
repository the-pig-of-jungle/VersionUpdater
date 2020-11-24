package com.coder.zzq.versionupdaterlib.communication;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.coder.zzq.versionupdaterlib.bean.download_event.DownloadEvent;

public class DownloadEventLiveData extends MutableLiveData<DownloadEvent> {
    @Override
    public void removeObserver(@NonNull Observer<? super DownloadEvent> observer) {
        super.removeObserver(observer);
        if (observer instanceof AbstractDownloadObserver) {
            ((AbstractDownloadObserver) observer).releaseContext();
        }
    }
}
