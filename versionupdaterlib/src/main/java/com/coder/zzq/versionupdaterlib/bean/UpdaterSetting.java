package com.coder.zzq.versionupdaterlib.bean;

import android.content.Context;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.coder.zzq.versionupdaterlib.util.Utils;

/**
 * Created by 喜欢、陪你看风景 on 2018/1/31.
 */

public class UpdaterSetting implements Parcelable {
    //手动检测模式
    public static final int DETECT_MODE_MANUAL = 0;
    //自动检测模式
    public static final int DETECT_MODE_AUTO = 1;


    private int mRemoteVersionCode;
    private int mLocalVersionCode;
    private Uri mRemoteApkUri;
    private boolean mIsForceUpdate;
    private int mNotificationVisibilityMode;
    private String mNotificationTitle;
    private boolean mNeedNotifiedProgress;
    private String mSavedApkName;
    private int mDetectMode;

    public UpdaterSetting() {

    }

    protected UpdaterSetting(Parcel in) {
        mRemoteVersionCode = in.readInt();
        mLocalVersionCode = in.readInt();
        mRemoteApkUri = in.readParcelable(Uri.class.getClassLoader());
        mIsForceUpdate = in.readByte() != 0;
        mNotificationVisibilityMode = in.readInt();
        mNotificationTitle = in.readString();
        mNeedNotifiedProgress = in.readByte() != 0;
        mSavedApkName = in.readString();
        mDetectMode = in.readInt();
    }

    public static final Creator<UpdaterSetting> CREATOR = new Creator<UpdaterSetting>() {
        @Override
        public UpdaterSetting createFromParcel(Parcel in) {
            return new UpdaterSetting(in);
        }

        @Override
        public UpdaterSetting[] newArray(int size) {
            return new UpdaterSetting[size];
        }
    };

    public int getRemoteVersionCode() {
        return mRemoteVersionCode;
    }

    public void setRemoteVersionCode(int remoteVersionCode) {
        mRemoteVersionCode = remoteVersionCode;
    }

    public int getLocalVersionCode() {
        return mLocalVersionCode;
    }

    public void setLocalVersionCode(int localVersionCode) {
        mLocalVersionCode = localVersionCode;
    }

    public Uri getRemoteApkUri() {
        return mRemoteApkUri;
    }

    public void setRemoteApkUri(Uri remoteApkUri) {
        mRemoteApkUri = remoteApkUri;
    }

    public boolean isForceUpdate() {
        return mIsForceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        mIsForceUpdate = forceUpdate;
    }

    public int getNotificationVisibilityMode() {
        return mNotificationVisibilityMode;
    }

    public void setNotificationVisibilityMode(int notificationVisibilityMode) {
        mNotificationVisibilityMode = notificationVisibilityMode;
    }

    public String getNotificationTitle() {
        return mNotificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        mNotificationTitle = notificationTitle;
    }

    public boolean isNeedNotifiedProgress() {
        return mNeedNotifiedProgress;
    }

    public void setNeedNotifiedProgress(boolean needNotifiedProgress) {
        mNeedNotifiedProgress = needNotifiedProgress;
    }

    public String getSavedApkName() {
        return mSavedApkName;
    }

    public void setSavedApkName(String savedApkName) {
        mSavedApkName = savedApkName;
        if (!mSavedApkName.endsWith(".apk")) {
            mSavedApkName = mSavedApkName + ".apk";
        }
    }

    public String getBaseApkName(){
        if (mSavedApkName == null || mSavedApkName.trim().length() == 0){
            return "";
        }

        int index  = mSavedApkName.lastIndexOf(".apk");

        return mSavedApkName.substring(0,index);

    }

    public int getDetectMode() {
        return mDetectMode;
    }

    public void setDetectMode(int detectMode) {
        mDetectMode = detectMode;
    }


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mRemoteVersionCode);
        dest.writeInt(mLocalVersionCode);
        dest.writeParcelable(mRemoteApkUri, flags);
        dest.writeByte((byte) (mIsForceUpdate ? 1 : 0));
        dest.writeInt(mNotificationVisibilityMode);
        dest.writeString(mNotificationTitle);
        dest.writeByte((byte) (mNeedNotifiedProgress ? 1 : 0));
        dest.writeString(mSavedApkName);
        dest.writeInt(mDetectMode);
    }

    public void settingCheck() {

        if (mRemoteVersionCode == 0) {

            throw new IllegalStateException("尚未设置远程apk的版本号！");
        }

        if (mRemoteApkUri == null) {
            throw new IllegalStateException("尚未设置远程apk的下载地址！");
        }

        if (mSavedApkName == null) {
            setSavedApkName(mRemoteApkUri.getLastPathSegment());
        }

    }

    public boolean isLocalVersionUpToDate() {
        return mLocalVersionCode == mRemoteVersionCode;
    }
}
