package com.coder.zzq.version_updater.communication;

import com.coder.zzq.version_updater.CheckConfig;
import com.coder.zzq.version_updater.bean.update_event.DownloadRequestDuplicate;
import com.coder.zzq.version_updater.bean.update_event.LocalVersionIsUpToDate;
import com.coder.zzq.version_updater.bean.update_event.VersionUpdateEvent;

import java.util.LinkedList;
import java.util.List;

public class UpdateEventNotifier {
    private static final UpdateEventNotifier UPDATE_EVENT_NOTIFIER = new UpdateEventNotifier();
    private final List<UpdateEventViewModel> mUpdateEventViewModels = new LinkedList<>();
    private boolean mFilteringIntermediateProgress = true;

    private UpdateEventNotifier() {

    }

    public static UpdateEventNotifier get() {
        return UPDATE_EVENT_NOTIFIER;
    }

    public synchronized void filteringIntermediateProgress(boolean filteringIntermediateProgress) {
        mFilteringIntermediateProgress = filteringIntermediateProgress;
    }

    public synchronized boolean isFilteringIntermediateProgress() {
        return mFilteringIntermediateProgress;
    }

    public synchronized void notifyEvent(VersionUpdateEvent updateEvent) {
        for (UpdateEventViewModel viewModel : mUpdateEventViewModels) {
            if ((updateEvent.getClass() == LocalVersionIsUpToDate.class || updateEvent.getClass() == DownloadRequestDuplicate.class)
                    && viewModel.updateEvent().getDetectMode() == CheckConfig.DETECT_MODE_AUTO) {
                continue;
            }
            viewModel.updateEvent().postValue(updateEvent);
        }
    }

    public synchronized void registerReceiver(UpdateEventViewModel updateEventViewModel) {
        mUpdateEventViewModels.add(updateEventViewModel);
    }

    public synchronized void unregisterReceiver(UpdateEventViewModel updateEventViewModel) {
        mUpdateEventViewModels.remove(updateEventViewModel);
    }
}
