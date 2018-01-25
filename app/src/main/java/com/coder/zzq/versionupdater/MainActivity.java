package com.coder.zzq.versionupdater;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.coder.zzq.versionupdaterlib.VersionUpdater;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;


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
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        mEditText = (EditText) findViewById(R.id.edt_id);
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

    public void onGetClick(View view) {
        DownloadManager downloadManager = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(Long.parseLong(mEditText.getText().toString().trim()));
        Cursor cursor = downloadManager.query(query);
        String uriStr = "";
        if (cursor.moveToFirst()){
            Log.d("fuck","任务存在" );
            uriStr = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));

        }

        cursor.close();



        Log.d("fuck","uri----->" + uriStr);



        Log.d("fuck",Uri.parse(uriStr).getEncodedPath());

        File file = new File(Uri.parse(uriStr).getEncodedPath());

        Log.d("fuck",file.exists() + "");

    }
}
