package com.coder.zzq.versionupdaterlib;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.content.FileProvider;

/**
 * Created by pig on 2018/1/26.
 */

public class DownloadFileInfo implements Parcelable {

    private Uri mUri;
    private int mFileSizeBytes;
    private int mDownloadStatus;

    public DownloadFileInfo() {

    }

    public DownloadFileInfo(Parcel in) {
        mUri = in.readParcelable(Uri.class.getClassLoader());
        mFileSizeBytes = in.readInt();
        mDownloadStatus = in.readInt();
    }

    public static final Creator<DownloadFileInfo> CREATOR = new Creator<DownloadFileInfo>() {
        @Override
        public DownloadFileInfo createFromParcel(Parcel in) {
            return new DownloadFileInfo(in);
        }

        @Override
        public DownloadFileInfo[] newArray(int size) {
            return new DownloadFileInfo[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeParcelable(mUri, flags);
        dest.writeInt(mFileSizeBytes);
        dest.writeInt(mDownloadStatus);
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
}
