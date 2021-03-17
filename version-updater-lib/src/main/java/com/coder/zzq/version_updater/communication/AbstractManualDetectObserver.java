package com.coder.zzq.version_updater.communication;

import android.content.Context;

import com.coder.zzq.version_updater.bean.update_event.VersionUpdateEvent;
import com.coder.zzq.version_updater.bean.update_event.DownloadRequestDuplicate;
import com.coder.zzq.version_updater.bean.update_event.LocalVersionIsUpToDate;

public abstract class AbstractManualDetectObserver extends HoldActivityContextObserver {

    public AbstractManualDetectObserver(Context activityContext) {
        super(activityContext);
    }

    @Override
    public final void onChanged(VersionUpdateEvent downloadEvent) {
        if (downloadEvent instanceof LocalVersionIsUpToDate) {
            onLocalVersionIsUpToDate(getActivityContext());
        } else if (downloadEvent instanceof DownloadRequestDuplicate) {
            onDownloadRequestDuplicate(getActivityContext());
        }
    }

    protected abstract void onLocalVersionIsUpToDate(Context activityContext);

    protected abstract void onDownloadRequestDuplicate(Context activityContext);


}
