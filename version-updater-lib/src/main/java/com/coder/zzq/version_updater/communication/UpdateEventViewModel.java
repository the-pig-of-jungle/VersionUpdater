package com.coder.zzq.version_updater.communication;

import androidx.lifecycle.ViewModel;

public class UpdateEventViewModel extends ViewModel {
    private UpdateEventLiveData mDownloadEventData;

    public UpdateEventViewModel() {
        UpdateEventNotifier.get().registerReceiver(this);
    }

    public UpdateEventLiveData updateEvent() {
        if (mDownloadEventData == null) {
            mDownloadEventData = new UpdateEventLiveData();
        }
        return mDownloadEventData;
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        UpdateEventNotifier.get().unregisterReceiver(this);
    }


}
