package com.coder.zzq.versionupdaterlib.bean.download.event;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class DownloadFailed extends DownloadEvent {
    public static final int FAILED_REASON_INSUFFICIENT_SPACE = 0;
    public static final int FAILED_REASON_SD_CARD_NOT_FOUND = 1;

    @Retention(RetentionPolicy.CLASS)
    @IntDef({FAILED_REASON_INSUFFICIENT_SPACE, FAILED_REASON_SD_CARD_NOT_FOUND})
    public @interface Reason {
    }

    public DownloadFailed(int failedReason) {
        mFailedReason = failedReason;
    }

    @Reason
    private int mFailedReason;

    @Reason
    public int getFailedReason() {
        return mFailedReason;
    }

}
