package com.coder.zzq.versionupdater;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.coder.zzq.versionupdaterlib.VersionUpdater;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onDetectClick(View view) {
        VersionUpdater.get(this)
                .remoteVersionCode(2)
                .remoteApkUrl("http://mu.liinji.com/AppFolders/20180122/ps_version_1.3.6.apk")
                .detect();
    }
}
