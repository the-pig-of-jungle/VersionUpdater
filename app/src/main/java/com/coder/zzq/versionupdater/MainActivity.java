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

import com.coder.zzq.smartshow.snackbar.SmartSnackbar;
import com.coder.zzq.smartshow.toast.SmartToast;

import com.coder.zzq.versionupdaterlib.EventProcessor;
import com.coder.zzq.versionupdaterlib.MessageSender;
import com.coder.zzq.versionupdaterlib.VersionUpdater;
import com.coder.zzq.versionupdaterlib.bean.DownloadEvent;
import com.coder.zzq.versionupdaterlib.bean.UpdaterSetting;
import com.coder.zzq.versionupdaterlib.util.Utils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;



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
                    .remoteVersionName("3.7.1")
                    .updateDesc("我愛你")
                    .remoteApkUrl("http://testmu.liinji.cn/AppFolders/20180203/ps_2.8.3.apk")
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
                .remoteVersionName("3.7.1")
                .remoteApkUrl("http://testmu.liinji.cn/AppFolders/20180127/ps_version_2.7.1.apk")
                .updateDesc("")
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
    public void onReceiveDownloadEvent(final DownloadEvent downloadEvent) {
        EventProcessor.create(this, downloadEvent).process(new EventProcessor.Callback() {
            @Override
            public void localVersionUpToDate(Activity activity, DownloadEvent downloadEvent) {
                SmartToast.showInCenter("当前已为最新版本！");
            }

            @Override
            public void beforeNewVersionDownload(final Activity activity, final DownloadEvent downloadEvent) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                        .setTitle("发现新版本 v" + downloadEvent.getNewVersionName())
                        .setMessage(downloadEvent.getUpdateDesc())
                        .setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                downloadEvent.updateImmediately(activity);
                            }
                        }).setCancelable(downloadEvent.isForceUpdate());

                if (!downloadEvent.isForceUpdate()) {
                    builder.setNegativeButton("暂不更新", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            downloadEvent.delayUpdate(activity);
                        }
                    });
                }

                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

            }

            @Override
            public void afterDownloadHasStarted(final Activity activity, final DownloadEvent downloadEvent) {
                SmartToast.showInCenter("已在后台下载新版本！");
            }

            @Override
            public void downloadComplete(Activity activity, DownloadEvent downloadEvent) {

            }

            @Override
            public void downloadInProgress(Activity activity, DownloadEvent downloadEvent) {

            }

            @Override
            public void downloadPaused(Activity activity, DownloadEvent downloadEvent) {

            }

            @Override
            public void downloadFailed(Activity activity, DownloadEvent downloadEvent) {

            }
        });
    }


}
