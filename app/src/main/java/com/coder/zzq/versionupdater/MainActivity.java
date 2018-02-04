package com.coder.zzq.versionupdater;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.coder.zzq.smartshow.toast.SmartToast;

import com.coder.zzq.versionupdaterlib.EventProcessor;
import com.coder.zzq.versionupdaterlib.MessageSender;
import com.coder.zzq.versionupdaterlib.VersionUpdater;
import com.coder.zzq.versionupdaterlib.bean.DownloadEvent;
import com.coder.zzq.versionupdaterlib.bean.UpdaterSetting;
import com.coder.zzq.versionupdaterlib.util.Utils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;


public class MainActivity extends AppCompatActivity {

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
        SmartToast.plainToast(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, mPermission, 1);
        } else {
            VersionUpdater.builder(this)
                    .remoteVersionCode(2)
                    .remoteApkUrl("http://testmu.liinji.cn/AppFolders/20180127/ps_version_2.7.1.apk")
                    .notificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//                    .isForceUpdate(true)
                    .detectMode(UpdaterSetting.DETECT_MODE_MANUAL)
                    .savedApkName("配送员")
                    .build()
                    .check();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        VersionUpdater.builder(this)
                .remoteVersionCode(1)
                .remoteApkUrl("http://testmu.liinji.cn/AppFolders/20180127/ps_version_2.7.1.apk")
                .notificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .notificationTitle("PP积配送员")
                .savedApkName("test.apk")
                .detectMode(UpdaterSetting.DETECT_MODE_AUTO)
                .build()
                .check();
    }

    public void onGetClick(View view) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onReceiveDownloadEvent(DownloadEvent event) {


        EventProcessor.create(this, event).process(new EventProcessor.Callback() {
            @Override
            public void localVersionUpToDate(Activity activity, DownloadEvent downloadEvent) {
                SmartToast.showInCenter("已是最新版本！");
            }

            @Override
            public void beforeNewVersionDownload(Activity activity, DownloadEvent event) {
                SmartToast.showInCenter("有新版本！");
            }

            @Override
            public void downloadInProgress(Activity activity, DownloadEvent event) {
                SmartToast.showInCenter("正在下载中！");
            }

            @Override
            public void downloadPaused(Activity activity, DownloadEvent event) {
                SmartToast.showInCenter("下载暂停！");
            }

            @Override
            public void downloadFailed(Activity activity, DownloadEvent event) {
                SmartToast.show("下载失败！");
            }

            @Override
            public void downloadComplete(Activity activity, DownloadEvent event) {

            }
        });
    }
}
