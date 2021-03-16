package com.coder.zzq.version_updater.communication;

import androidx.lifecycle.ViewModel;

public class DownloadEventViewModel extends ViewModel {
    private DownloadEventLiveData mDownloadEventData;

    public DownloadEventViewModel() {
        DownloadEventNotifier.get().registerReceiver(this);
    }

    public DownloadEventLiveData downloadEvent() {
        if (mDownloadEventData == null) {
            mDownloadEventData = new DownloadEventLiveData();
        }
        return mDownloadEventData;
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        DownloadEventNotifier.get().unregisterReceiver(this);
    }


}
