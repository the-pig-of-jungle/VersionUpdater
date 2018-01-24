package com.coder.zzq.versionupdater;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.coder.zzq.versionupdaterlib.VersionUpdater;



public class MainActivity extends AppCompatActivity {

    private String[] mPermission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SmartToast.plainToast(this);
        mPermission = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
    }

    public void onDetectClick(View view) {
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,mPermission,1);
        }else {
            VersionUpdater.get(this)
                    .remoteVersionCode(2)
                    .remoteApkUrl("http://testmu.liinji.cn/AppFolders/20180124/ps_version_2.6.6.apk")
                    .detect();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        VersionUpdater.get(this)
                .remoteVersionCode(2)
                .remoteApkUrl("http://mu.liinji.com/AppFolders/20180122/ps_version_1.3.6.apk")
                .detect();
    }
}
