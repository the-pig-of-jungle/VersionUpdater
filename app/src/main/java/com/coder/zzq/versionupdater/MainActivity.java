package com.coder.zzq.versionupdater;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.coder.zzq.toolkit.Toolkit;
import com.coder.zzq.version_updater.VersionUpdater;
import com.coder.zzq.version_updater.bean.IgnorePeriod;


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
    }


    public void onAutoDetect(View view) {
        Toolkit.setEnablePrintLog(true);
        VersionUpdater.builder()
                .remoteVersionCode(6)
                .remoteVersionName("3.7.1")
                .remoteVersionDesc("我愛你")
                .remoteApkUrl("https://bxvip.oss-cn-zhangjiakou.aliyuncs.com/bxvip/androidapk/xunyingzy.apk")
                .forceUpdate(false)
                .ignorePeriod(IgnorePeriod.create().days(1).combine())
                .observer(this)
                .build()
                .autoCheck();
    }

    public void nextPage(View view) {
        startActivity(new Intent(this, MainActivity2.class));
    }
}
