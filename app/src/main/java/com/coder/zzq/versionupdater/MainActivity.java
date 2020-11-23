package com.coder.zzq.versionupdater;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.coder.zzq.toolkit.Toolkit;
import com.coder.zzq.versionupdaterlib.VersionUpdater;
import com.coder.zzq.versionupdaterlib.bean.DownloadTaskInfo;
import com.coder.zzq.versionupdaterlib.bean.download.event.DownloadEvent;
import com.coder.zzq.versionupdaterlib.communication.DownloadEventViewModel;
import com.coder.zzq.versionupdaterlib.communication.ManualDetectObserver;


public class MainActivity extends AppCompatActivity {

    private String[] mPermission;

    public static long mId;

    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPermission = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        mEditText = (EditText) findViewById(R.id.edt_id);
        MutableLiveData<DownloadEvent> liveData = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory())
                .get(DownloadEventViewModel.class)
                .downloadEvent();
        liveData.observe(this, new DownloadObserver(this));
        liveData.observe(this, new ManualDetectObserver() {
            @Override
            protected void onLocalVersionIsUpToDate() {
                Toast.makeText(Toolkit.getContext(), "当前已是最新版本", Toast.LENGTH_LONG).show();
            }

            @Override
            protected void onDownloadRequestDuplicate() {
                Toast.makeText(Toolkit.getContext(), "已在后台下载中...", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void onGetClick(View view) {

        VersionUpdater.builder(getApplication())
                .remoteVersionCode(2)
                .remoteVersionName("3.7.1")
                .remoteVersionDesc("我愛你")
                .remoteApkUrl("https://bxvip.oss-cn-zhangjiakou.aliyuncs.com/bxvip/androidapk/xunyingzy.apk")
//                .notificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//                .isForceUpdate(true)
//                .detectMode(UpdaterSetting.DETECT_MODE_MANUAL)
//                .savedApkName("配送员")
                .detectMode(DownloadTaskInfo.DETECT_MODE_MANUAL)
                .build()
                .check();
    }
}
