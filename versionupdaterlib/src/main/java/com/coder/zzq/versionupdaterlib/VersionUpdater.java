package com.coder.zzq.versionupdaterlib;

import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.coder.zzq.smartshow.toast.SmartToast;

/**
 * Created by 朱志强 on 2018/1/23.
 */

public class VersionUpdater{

    private Context mAppContext;

    private int mRemoteVersionCode;
    private String mRemoteApkUrl;

    private boolean mRemoteVersionCodeHasSet;

    private boolean mRemoteApkUrlHasSet;

    private VersionUpdater(Context context){
        mAppContext = context.getApplicationContext();
    }


    public static VersionUpdater get(Context context){
        return new VersionUpdater(context);
    }

    public VersionUpdater remoteVersionCode(int remoteVersion){
        mRemoteVersionCode = remoteVersion;
        mRemoteVersionCodeHasSet = true;
        return this;
    }

    public VersionUpdater remoteApkUrl(String remoteApkUrl){
        mRemoteApkUrl = remoteApkUrl;
        mRemoteApkUrlHasSet = true;
        return this;
    }



    public void detect(){

        if (!mRemoteVersionCodeHasSet || !mRemoteApkUrlHasSet){
            new IllegalStateException("必须同时设置远程app的版本号和下载地址！");
        }

        if (needUpdate()){
            DownloadManager downloadManager = (DownloadManager) mAppContext.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(mRemoteApkUrl);
            SmartToast.show(uri.getLastPathSegment());
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(mRemoteApkUrl));
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"test.apk");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setTitle("abc");
            request.setDescription("efg");

            long downloadId = downloadManager.enqueue(request);

            for (;;){

                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(downloadId);
                Cursor cursor = downloadManager.query(query);

                if (cursor.moveToFirst()){
                    switch (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))){
                        case DownloadManager.STATUS_PENDING:
                            Log.d("main","pending");
                            break;
                        case DownloadManager.STATUS_RUNNING:

                            Log.d("main","running");
                            break;
                        case DownloadManager.STATUS_PAUSED:
                            Log.d("main","paused");
                            break;
                        case DownloadManager.STATUS_SUCCESSFUL:
                            Log.d("main","succ");
                            break;
                    }
                }

                cursor.close();
            }

        }

    }




    private boolean needUpdate(){
        int localVersion = localVersionCode();
        return mRemoteVersionCode > localVersion;
    }



    private int localVersionCode(){
        int versionCode = 1;

        try {
            versionCode = mAppContext.getPackageManager().getPackageInfo(mAppContext.getPackageName(),0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionCode;
    }



}
