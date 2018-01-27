package com.coder.zzq.versionupdaterlib;

import android.app.Activity;
import android.app.Application;
import android.app.DownloadManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.coder.zzq.versionupdaterlib.bean.DownloadEvent;

import static com.coder.zzq.versionupdaterlib.Utils.checkNullOrEmpty;

/**
 * Created by 朱志强 on 2018/1/23.
 */

public class VersionUpdater {

    private static boolean sHasInitMsgSender;

    public static UpdaterSetting get(Activity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("参数activity不可为null！");
        }
        UpdaterSetting updaterSetting = new UpdaterSetting(activity);
        initMessageSenderIfNotExists(activity, updaterSetting);
        return updaterSetting;
    }


    private static void initMessageSenderIfNotExists(Activity activity, UpdaterSetting updaterSetting) {

        if (!sHasInitMsgSender) {
            if (!MessageSender.isRegister(activity)) {
                MessageSender.register(activity);
            }
            activity.getApplication().registerActivityLifecycleCallbacks(updaterSetting);
            sHasInitMsgSender = true;
        }

    }


    public static class UpdaterSetting implements Application.ActivityLifecycleCallbacks, Parcelable {

        private int mRemoteVersionCode;
        private int mLocalVersionCode;
        private Uri mRemoteApkUri;
        private int mNotificationVisibilityMode;
        private String mNotificationTitle;
        private boolean mNeedNotifiedProgress;
        private String mSavedApkName;

        public UpdaterSetting(Activity activity) {
            mLocalVersionCode = Utils.localVersionCode(activity);
            mNotificationVisibilityMode = DownloadManager.Request.VISIBILITY_VISIBLE;
        }


        protected UpdaterSetting(Parcel in) {
            mRemoteVersionCode = in.readInt();
            mLocalVersionCode = in.readInt();
            mRemoteApkUri = in.readParcelable(Uri.class.getClassLoader());
            mNotificationVisibilityMode = in.readInt();
            mNotificationTitle = in.readString();
            mNeedNotifiedProgress = in.readByte() != 0;
            mSavedApkName = in.readString();
        }


        public UpdaterSetting remoteVersionCode(int versionCode) {
            if (versionCode < 1) {
                new IllegalArgumentException("版本号不可小于1");
            }
            mRemoteVersionCode = versionCode;
            return this;
        }


        public UpdaterSetting remoteApkUrl(String apkUrl) {
            mRemoteApkUri = Uri.parse(Utils.checkNullOrEmpty(apkUrl));
            return this;
        }


        public UpdaterSetting notificationVisibility(int visibilityMode) {

            switch (visibilityMode) {

                case DownloadManager.Request.VISIBILITY_VISIBLE:
                case DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED:
                case DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION:
                case DownloadManager.Request.VISIBILITY_HIDDEN:
                    mNotificationVisibilityMode = visibilityMode;
                    break;
                default:
                    mNotificationVisibilityMode = DownloadManager.Request.VISIBILITY_VISIBLE;
                    break;

            }

            return this;
        }


        public UpdaterSetting notificationTitle(String title) {
            mNotificationTitle = checkNullOrEmpty(title);
            return this;
        }


        public UpdaterSetting needNotifiedProgress(boolean need) {
            mNeedNotifiedProgress = need;
            return this;
        }


        public UpdaterSetting savedApkName(String apkName) {
            mSavedApkName = checkNullOrEmpty(apkName);
            if (!mSavedApkName.endsWith(".apk")) {
                mSavedApkName = mSavedApkName + ".apk";
            }
            return this;
        }


        public int getRemoteVersionCode() {
            return mRemoteVersionCode;
        }

        public Uri getRemoteApkUri() {
            return mRemoteApkUri;
        }

        public int getNotificationVisibilityMode() {
            return mNotificationVisibilityMode;
        }

        public String getNotificationTitle() {
            return mNotificationTitle;
        }

        public boolean isNeedNotifiedProgress() {
            return mNeedNotifiedProgress;
        }

        public String getSavedApkName() {
            return mSavedApkName;
        }

        private void settingCheck() {

            if (mRemoteVersionCode == 0) {

                throw new IllegalStateException("尚未设置远程apk的版本号！");
            }

            if (mRemoteApkUri == null) {
                throw new IllegalStateException("尚未设置远程apk的下载地址！");
            }

            if (mSavedApkName == null) {
                savedApkName(mRemoteApkUri.getLastPathSegment());
            }

        }


        public void check() {

            settingCheck();

            if (needUpdate()) {
                MessageSender.sendMsg(new DownloadEvent(DownloadEvent.HAS_NEW_VERSION, this));
            } else {
                MessageSender.sendMsg(new DownloadEvent(DownloadEvent.VERSION_UP_TO_DATE));
            }
        }


        private boolean needUpdate() {
            return mRemoteVersionCode > mLocalVersionCode;
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {
            if (!MessageSender.isRegister(activity)) {
                MessageSender.register(activity);
            }
        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            if (MessageSender.isRegister(activity)) {
                MessageSender.unregister(activity);
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(mRemoteVersionCode);
            dest.writeInt(mLocalVersionCode);
            dest.writeParcelable(mRemoteApkUri, flags);
            dest.writeInt(mNotificationVisibilityMode);
            dest.writeString(mNotificationTitle);
            dest.writeByte((byte) (mNeedNotifiedProgress ? 1 : 0));
            dest.writeString(mSavedApkName);
        }
    }


}
