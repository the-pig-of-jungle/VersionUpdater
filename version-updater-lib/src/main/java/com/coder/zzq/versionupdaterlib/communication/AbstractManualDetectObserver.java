package com.coder.zzq.versionupdaterlib.communication;

import android.content.Context;

import com.coder.zzq.versionupdaterlib.bean.download_event.DownloadEvent;
import com.coder.zzq.versionupdaterlib.bean.download_event.DownloadRequestDuplicate;
import com.coder.zzq.versionupdaterlib.bean.download_event.LocalVersionIsUpToDate;

public abstract class AbstractManualDetectObserver extends HoldActivityContextObserver {

    public AbstractManualDetectObserver(Context activityContext) {
        super(activityContext);
    }

    @Override
    public final void onChanged(DownloadEvent downloadEvent) {
        if (downloadEvent instanceof LocalVersionIsUpToDate) {
            onLocalVersionIsUpToDate(getActivityContext());
        } else if (downloadEvent instanceof DownloadRequestDuplicate) {
            onDownloadRequestDuplicate(getActivityContext());
        }
    }

    protected abstract void onLocalVersionIsUpToDate(Context activityContext);

    protected abstract void onDownloadRequestDuplicate(Context activityContext);


}
