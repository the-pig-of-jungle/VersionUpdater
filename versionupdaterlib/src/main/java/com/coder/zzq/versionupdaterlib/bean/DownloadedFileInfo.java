package com.coder.zzq.versionupdaterlib.bean;

import android.net.Uri;

/**
 * Created by 喜欢、陪你看风景 on 2018/2/14.
 */

public class DownloadedFileInfo {

    public static final int STATUS_NO_EXISTS = 0;

    private Uri mUri;
    private int mFileSizeBytes;
    private int mDownloadStatus;
    private int mReason;

    public DownloadedFileInfo() {

    }



    public Uri getUri() {
        return mUri;
    }

    public void setUri(Uri uri) {
        mUri = uri;
    }

    public int getFileSizeBytes() {
        return mFileSizeBytes;
    }

    public void setFileSizeBytes(int fileSizeBytes) {
        mFileSizeBytes = fileSizeBytes;
    }

    public int getDownloadStatus() {
        return mDownloadStatus;
    }

    public void setDownloadStatus(int downloadStatus) {
        mDownloadStatus = downloadStatus;
    }

    public int getReason() {
        return mReason;
    }

    public void setReason(int reason) {
        mReason = reason;
    }
}
