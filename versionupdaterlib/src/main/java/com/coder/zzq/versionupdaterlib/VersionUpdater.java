package com.coder.zzq.versionupdaterlib;

import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;

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
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(mRemoteApkUrl));
            downloadManager.enqueue(request);
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
