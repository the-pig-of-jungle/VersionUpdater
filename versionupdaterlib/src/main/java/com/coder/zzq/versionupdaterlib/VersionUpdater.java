package com.coder.zzq.versionupdaterlib;

import android.app.Activity;
import android.app.Application;
import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.Log;

import com.coder.zzq.smartshow.toast.SmartToast;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileDescriptor;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import static com.coder.zzq.versionupdaterlib.Utils.checkNullOrEmpty;

/**
 * Created by 朱志强 on 2018/1/23.
 */

public class VersionUpdater implements UpdaterSetting, Application.ActivityLifecycleCallbacks {

    private Application mApplication;
    private int mRemoteVersionCode;
    private Uri mRemoteApkUri;
    private int mNotificationVisibilityMode;
    private String mNotificationTitle;
    private boolean mNeedNotifiedProgress;
    private String mSavedApkName;


    private VersionUpdater(Activity activity) {
        if (activity == null){
            throw new IllegalArgumentException("参数activity不可为null！");
        }
        if (!MessageSender.isRegister(activity)){
            MessageSender.register(activity);
        }
        mApplication = activity.getApplication();
        mApplication.registerActivityLifecycleCallbacks(this);
        mNotificationVisibilityMode = DownloadManager.Request.VISIBILITY_VISIBLE;
    }


    public static VersionUpdater get(Activity activity) {
        return new VersionUpdater(activity);
    }

    @Override
    public UpdaterSetting remoteVersionCode(int versionCode) {
        if (versionCode < 1){
            new IllegalArgumentException("版本不可小于1");
        }
        mRemoteVersionCode = versionCode;
        return this;
    }

    @Override
    public UpdaterSetting remoteApkUrl(String apkUrl) {
        mRemoteApkUri = Uri.parse(Utils.checkNullOrEmpty(apkUrl));
        return this;
    }

    @Override
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

    @Override
    public UpdaterSetting notificationTitle(String title) {
        mNotificationTitle = checkNullOrEmpty(title);
        return this;
    }

    @Override
    public UpdaterSetting needNotifiedProgress(boolean need) {
        mNeedNotifiedProgress = need;
        return this;
    }

    @Override
    public UpdaterSetting savedApkName(String apkName) {
        mSavedApkName = checkNullOrEmpty(apkName);
        if (!mSavedApkName.endsWith(".apk")){
            mSavedApkName = mSavedApkName + ".apk";
        }
        return this;
    }



    private void settingCheck() {

        if (mRemoteVersionCode == 0){
            throw new IllegalStateException("尚未设置远程apk的版本号！");
        }

        if (mRemoteApkUri == null){
            throw new IllegalStateException("尚未设置远程apk的下载地址！");
        }

        if (mSavedApkName == null){
            savedApkName(mRemoteApkUri.getLastPathSegment());
        }

    }



    @Override
    public void check() {

        settingCheck();

        if (needUpdate()) {
            MessageSender.sendMsg(new DownloadEvent(DownloadEvent.HAS_NEW_VERSION, this));
        }
    }




    private boolean needUpdate() {
        int localVersion = localVersionCode();
        return mRemoteVersionCode > localVersion;
    }


    private int localVersionCode() {
        int versionCode = 1;

        try {
            versionCode = mApplication.getPackageManager().getPackageInfo(mApplication.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionCode;
    }


    public void startDownload() {

        DownloadManager downloadManager = (DownloadManager) mApplication.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(mRemoteApkUri);

        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, mSavedApkName)
                .setNotificationVisibility(mNotificationVisibilityMode);

        if (mNotificationTitle != null){
            request.setTitle(mNotificationTitle);
        }

        downloadManager.enqueue(request);

    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (!MessageSender.isRegister(activity)){
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
        if (MessageSender.isRegister(activity)){
            MessageSender.unregister(activity);
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }


}
