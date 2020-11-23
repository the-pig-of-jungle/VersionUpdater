package com.coder.zzq.versionupdaterlib.communication;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.coder.zzq.versionupdaterlib.bean.download_event.DownloadEvent;

public class DownloadEventViewModel extends ViewModel {
    MutableLiveData<DownloadEvent> mDownloadEventData;

    public DownloadEventViewModel() {
        DownloadEventNotifier.get().registerReceiver(this);
    }

    public MutableLiveData<DownloadEvent> downloadEvent() {
        if (mDownloadEventData == null) {
            mDownloadEventData = new MutableLiveData<>();
        }
        return mDownloadEventData;
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        DownloadEventNotifier.get().unregisterReceiver(this);
    }


}
