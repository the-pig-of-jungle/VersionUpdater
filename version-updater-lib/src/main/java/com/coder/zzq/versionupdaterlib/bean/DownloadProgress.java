package com.coder.zzq.versionupdaterlib.bean;

public class DownloadProgress {
    private final boolean mComplete;
    private final long mCurrentSize;
    private final long mTotalSize;
    private final String mPercentage;

    public DownloadProgress(long currentSize, long totalSize, String percentage, boolean complete) {
        mCurrentSize = currentSize;
        mTotalSize = totalSize;
        mPercentage = percentage;
        mComplete = complete;
    }

    public long getCurrentSize() {
        return mCurrentSize;
    }


    public long getTotalSize() {
        return mTotalSize;
    }


    public String getPercentage() {
        return mPercentage;
    }


    public boolean isComplete() {
        return mComplete;
    }
}
