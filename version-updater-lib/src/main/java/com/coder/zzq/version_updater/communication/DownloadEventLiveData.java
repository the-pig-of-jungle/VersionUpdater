package com.coder.zzq.version_updater.communication;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.coder.zzq.version_updater.bean.download_event.DownloadEvent;

public class DownloadEventLiveData extends MutableLiveData<DownloadEvent> {
    @Override
    public void removeObserver(@NonNull Observer<? super DownloadEvent> observer) {
        super.removeObserver(observer);
        if (observer instanceof HoldActivityContextObserver) {
            ((HoldActivityContextObserver) observer).releaseContext();
        }
    }
}
