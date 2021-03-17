package com.coder.zzq.version_updater.communication;

import com.coder.zzq.version_updater.bean.update_event.ClearInActiveObserverData;
import com.coder.zzq.version_updater.bean.update_event.VersionUpdateEvent;

import java.util.LinkedList;
import java.util.List;

public class DownloadEventNotifier {
    private static final DownloadEventNotifier sDownloadEventNotifier = new DownloadEventNotifier();
    private final List<DownloadEventViewModel> mDownloadEventViewModels = new LinkedList<>();
    private boolean mFilteringIntermediateProgress = true;

    private DownloadEventNotifier() {

    }

    public static DownloadEventNotifier get() {
        return sDownloadEventNotifier;
    }

    public synchronized void filteringIntermediateProgress(boolean filteringIntermediateProgress) {
        mFilteringIntermediateProgress = filteringIntermediateProgress;
    }

    public synchronized boolean isFilteringIntermediateProgress() {
        return mFilteringIntermediateProgress;
    }

    public synchronized void notifyEvent(VersionUpdateEvent updateEvent) {
        for (DownloadEventViewModel viewModel : mDownloadEventViewModels) {
            if (viewModel.downloadEvent().hasActiveObservers()) {
                if (!(updateEvent instanceof ClearInActiveObserverData)) {
                    viewModel.downloadEvent().postValue(updateEvent);
                }
            } else {
                viewModel.downloadEvent().postValue(new ClearInActiveObserverData());
            }
        }
    }

    public synchronized void registerReceiver(DownloadEventViewModel downloadEventViewModel) {
        mDownloadEventViewModels.add(downloadEventViewModel);
    }

    public synchronized void unregisterReceiver(DownloadEventViewModel downloadEventViewModel) {
        mDownloadEventViewModels.remove(downloadEventViewModel);
    }
}
