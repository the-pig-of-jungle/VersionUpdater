package com.coder.zzq.versionupdater;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.coder.zzq.versionupdaterlib.DownloadEvent;
import com.coder.zzq.versionupdaterlib.DownloadEventProcessor;
import com.coder.zzq.versionupdaterlib.MessageSender;
import com.coder.zzq.versionupdaterlib.VersionUpdater;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;


public class MainActivity extends AppCompatActivity{

    private String[] mPermission;

    public static long mId;

    private EditText mEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SmartToast.plainToast(this);
        mPermission = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        mEditText = (EditText) findViewById(R.id.edt_id);
    }

    public void onDetectClick(View view) {
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,mPermission,1);
        }else {
            VersionUpdater.get(this)
                    .remoteVersionCode(2)
                    .remoteApkUrl("http://testmu.liinji.cn/AppFolders/20180127/ps_version_2.7.1.apk")
                    .notificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .check();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    public void onGetClick(View view) {

    }


    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onReceiveDownloadEvent(DownloadEvent event) {
        switch (event.getEvent()){
            case DownloadEvent.HAS_NEW_VERSION:
                SmartToast.showAtTop("有新版本！");
                event.startDownload();
                break;
            case DownloadEvent.DOWNLOAD_COMPLETE:

                event.installApk(this);
                break;
        }
    }

}
