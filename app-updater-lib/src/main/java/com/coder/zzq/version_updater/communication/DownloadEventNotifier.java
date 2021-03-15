package com.coder.zzq.version_updater.communication;

import com.coder.zzq.version_updater.bean.download_event.DownloadEvent;

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

    public synchronized void notifyEvent(DownloadEvent downloadEvent) {
        for (DownloadEventViewModel viewModel : mDownloadEventViewModels) {
            viewModel.downloadEvent().postValue(downloadEvent);
        }
    }

    public synchronized void registerReceiver(DownloadEventViewModel downloadEventViewModel) {
        mDownloadEventViewModels.add(downloadEventViewModel);
    }

    public synchronized void unregisterReceiver(DownloadEventViewModel downloadEventViewModel) {
        mDownloadEventViewModels.remove(downloadEventViewModel);
    }
}
