package com.coder.zzq.versionupdater;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.coder.zzq.version_updater.VersionUpdater;
import com.coder.zzq.version_updater.bean.IgnorePeriod;
import com.coder.zzq.version_updater.bean.RemoteVersion;

public class ManualCheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_check);

        Button fab = findViewById(R.id.manual_check);
        fab.setOnClickListener(view -> {
            RemoteVersion remoteVersion = mockNetData();
            VersionUpdater.builder()
                    .remoteVersionCode(remoteVersion.getVersionCode())
                    .remoteVersionName(remoteVersion.getVersionName())
                    .remoteVersionDesc(remoteVersion.getVersionDesc())
                    .remoteApkUrl(remoteVersion.getApkUrl())
                    .forceUpdate(remoteVersion.isForceUpdate())
                    .ignorePeriod(IgnorePeriod.create().days(1).combine())
                    .observer(this)
                    .build()
                    .manualCheck();
        });
    }

    private RemoteVersion mockNetData() {
        return new RemoteVersion()
                .setVersionCode(10)
                .setVersionName("3.1.0")
                .setVersionDesc("1.修复已知问题")
                .setForceUpdate(false)
                .setApkUrl("https://s3.cn-north-1.amazonaws.com.cn/uat.backend.app.jaguarlandrover.cn/app/Landrover_uat_3_9_1_release_0207.apk");
    }
}