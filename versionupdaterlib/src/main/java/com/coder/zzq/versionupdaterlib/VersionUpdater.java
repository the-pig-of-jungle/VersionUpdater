package com.coder.zzq.versionupdaterlib;

import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import com.coder.zzq.smartshow.toast.SmartToast;

import java.io.File;
import java.io.FileDescriptor;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

/**
 * Created by 朱志强 on 2018/1/23.
 */

public class VersionUpdater {

    private Context mAppContext;
    private int mRemoteVersionCode;
    private String mRemoteApkUrl;
    private String mSavedApkName;


    private VersionUpdater(Context context) {
        mAppContext = context.getApplicationContext();
    }


    public static VersionUpdater get(Context context) {
        return new VersionUpdater(context);
    }


    public VersionUpdater remoteVersionCode(int versionCode) {
        mRemoteVersionCode = versionCode;
        return this;
    }


    public VersionUpdater remoteApkUrl(String apkUrl) {
        mRemoteApkUrl = apkUrl;
        return this;
    }

    public VersionUpdater savedApkName(String savedApkName) {
        mSavedApkName = savedApkName;
        return this;
    }


    public void check() {
        if (needUpdate()) {
            DownloadManager downloadManager = (DownloadManager) mAppContext.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(mRemoteApkUrl);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, mSavedApkName)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setTitle("abc");
           long downloadId = downloadManager.enqueue(request);
        }
    }


    private boolean needUpdate() {
        int localVersion = localVersionCode();
        return mRemoteVersionCode > localVersion;
    }


    private int localVersionCode() {
        int versionCode = 1;

        try {
            versionCode = mAppContext.getPackageManager().getPackageInfo(mAppContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionCode;
    }


}
