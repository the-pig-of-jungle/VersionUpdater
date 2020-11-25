package com.coder.zzq.versionupdater;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.coder.zzq.toolkit.Toolkit;
import com.coder.zzq.versionupdaterlib.ManualDetectObserver;
import com.coder.zzq.versionupdaterlib.VersionUpdater;
import com.coder.zzq.versionupdaterlib.autoDetectObserver;
import com.coder.zzq.versionupdaterlib.communication.DownloadEventLiveData;
import com.coder.zzq.versionupdaterlib.communication.DownloadEventViewModel;


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
    }


    public void onGetClick(View view) {
        Toolkit.init(getApplication());
        Toolkit.setEnablePrintLog(true);
        VersionUpdater.builder()
                .remoteVersionCode(4)
                .remoteVersionName("3.7.1")
                .remoteVersionDesc("我愛你")
                .remoteApkUrl("https://bxvip.oss-cn-zhangjiakou.aliyuncs.com/bxvip/androidapk/xunyingzy.apk")
                .forceUpdate(false)
                .observer(this)
                .build()
                .manualCheck();
    }
}
