package com.coder.zzq.version_updater.communication;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.coder.zzq.version_updater.CheckConfig;
import com.coder.zzq.version_updater.bean.update_event.VersionUpdateEvent;

public class UpdateEventLiveData extends MutableLiveData<VersionUpdateEvent> {
    @CheckConfig.DetectMode
    private int mDetectMode;

    @Override
    public void removeObserver(@NonNull Observer<? super VersionUpdateEvent> observer) {
        super.removeObserver(observer);
        if (observer instanceof HoldActivityContextObserver) {
            ((HoldActivityContextObserver) observer).releaseContext();
        }
    }

    public void setDetectMode(int detectMode) {
        mDetectMode = detectMode;
    }

    public int getDetectMode() {
        return mDetectMode;
    }
}
