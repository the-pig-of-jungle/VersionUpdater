package com.coder.zzq.versionupdater;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.coder.zzq.toolkit.Toolkit;
import com.coder.zzq.version_updater.VersionUpdater;
import com.coder.zzq.version_updater.bean.IgnorePeriod;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
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
                    .manualCheck();
        });
    }
}