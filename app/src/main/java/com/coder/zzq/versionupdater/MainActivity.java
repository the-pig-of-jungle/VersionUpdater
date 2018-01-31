package com.coder.zzq.versionupdater;

import android.Manifest;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.coder.zzq.versionupdaterlib.DownloadEventProcessor;
import com.coder.zzq.versionupdaterlib.MessageSender;
import com.coder.zzq.versionupdaterlib.VersionUpdater;
import com.coder.zzq.versionupdaterlib.bean.DownloadEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class MainActivity extends AppCompatActivity implements DownloadEventProcessor {

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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, mPermission, 1);
        } else {
            VersionUpdater.builder(this)
                    .remoteVersionCode(2)
                    .remoteApkUrl("http://testmu.liinji.cn/AppFolders/20180127/ps_version_2.7.1.apk")
                    .notificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .build()
                    .check();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        VersionUpdater.builder(this)
                .remoteVersionCode(2)
                .remoteApkUrl("http://testmu.liinji.cn/AppFolders/20180127/ps_version_2.7.1.apk")
                .notificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .notificationTitle("PP积配送员")
                .savedApkName("test.apk")
                .build()
                .check();
    }

    public void onGetClick(View view) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    @Override
    public void onReceiveDownloadEvent(final DownloadEvent event) {
        switch (event.getEvent()) {
            case DownloadEvent.BEFORE_NEW_VERSION_DOWNLOAD:
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle("发现新版本v1.0.1")
                        .setMessage("**********")
                        .setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                event.startDownload(MainActivity.this);
                            }
                        })
                        .setNegativeButton("暂不更新", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                alertDialog.show();
                break;
            case DownloadEvent.DOWNLOAD_COMPLETE:
                new AlertDialog.Builder(this)
                        .setTitle("下载已完成v1.0.1")
                        .setMessage("**********")
                        .setPositiveButton("立即安装", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                event.installApk(MainActivity.this);
                            }
                        })
                        .setNegativeButton("稍后安装", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
                break;
            case DownloadEvent.APK_HAS_EXISTS:
                event.installApk(this);
                break;
        }

        MessageSender.removeDownloadEvent(event);
    }
}
