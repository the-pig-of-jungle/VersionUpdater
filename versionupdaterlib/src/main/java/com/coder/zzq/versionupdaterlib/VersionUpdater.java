package com.coder.zzq.versionupdaterlib;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.coder.zzq.toolkit.Toolkit;
import com.coder.zzq.versionupdaterlib.bean.DownloadTaskInfo;
import com.coder.zzq.versionupdaterlib.bean.download.event.LocalVersionIsUpToDate;
import com.coder.zzq.versionupdaterlib.communication.DownloadEventNotifier;
import com.coder.zzq.versionupdaterlib.communication.DownloadVersionInfoCache;
import com.coder.zzq.versionupdaterlib.tasks.TaskScheduler;
import com.coder.zzq.versionupdaterlib.util.UpdateUtil;

/**
 * Created by zhiqiang.zhu on 2018/1/31.
 */

public class VersionUpdater implements IVersionUpdater {
    private static VersionUpdater sVersionUpdater = new VersionUpdater();

    protected static IVersionUpdater create(DownloadTaskInfo downloadTaskInfo) {
        return sVersionUpdater.setDownloadTaskInfo(downloadTaskInfo);
    }

    private DownloadTaskInfo mDownloadTaskInfo;

    private VersionUpdater() {

    }

    private VersionUpdater setDownloadTaskInfo(DownloadTaskInfo downloadTaskInfo) {
        mDownloadTaskInfo = downloadTaskInfo;
        return this;
    }

    public static IUpdaterBuilder builder(Application application) {
        Toolkit.init(application);
        return new UpdateBuilder();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void check() {

        if (mDownloadTaskInfo.getRemoteVersionCode() <= UpdateUtil.getVersionCode()) {
            if (mDownloadTaskInfo.getDetectMode() == DownloadTaskInfo.DETECT_MODE_MANUAL) {
                DownloadEventNotifier.get().notifyEvent(new LocalVersionIsUpToDate());
            }
            if (DownloadVersionInfoCache.getDownloadVersionCodeFromCache() != 0) {
                TaskScheduler.cleanApkFile();
            }
            return;
        }

        TaskScheduler.downloadApk(mDownloadTaskInfo);
    }


    public interface IUpdaterBuilder {

        IUpdaterBuilder remoteVersionCode(int versionCode);

        IUpdaterBuilder remoteVersionName(String versionName);

        IUpdaterBuilder remoteApkUrl(String apkUrl);

        IUpdaterBuilder remoteVersionDesc(String desc);

        IUpdaterBuilder forceUpdate(boolean forceUpdate);

        IUpdaterBuilder notificationVisibility(boolean visible);

        IUpdaterBuilder detectMode(int detectMode);

        IVersionUpdater build();
    }

    public static class UpdateBuilder implements IUpdaterBuilder {
        private DownloadTaskInfo mDownloadTaskInfo;

        private UpdateBuilder() {
            mDownloadTaskInfo = new DownloadTaskInfo();
        }

        public IUpdaterBuilder remoteVersionCode(int versionCode) {
            mDownloadTaskInfo.setRemoteVersionCode(versionCode);
            return this;
        }

        @Override
        public IUpdaterBuilder remoteVersionName(String versionName) {
            mDownloadTaskInfo.setRemoteVersionName(versionName);
            return this;
        }

        @Override
        public IUpdaterBuilder remoteApkUrl(String apkUrl) {
            mDownloadTaskInfo.setRemoteApkUrl(apkUrl);
            return this;
        }

        @Override
        public IUpdaterBuilder remoteVersionDesc(String desc) {
            mDownloadTaskInfo.setRemoteVersionDesc(desc);
            return this;
        }

        @Override
        public IUpdaterBuilder forceUpdate(boolean forceUpdate) {
            mDownloadTaskInfo.setForceUpdate(forceUpdate);
            return this;
        }

        @Override
        public IUpdaterBuilder notificationVisibility(boolean visible) {
            mDownloadTaskInfo.setNotificationVisibility(visible);
            return this;
        }


        @Override
        public IUpdaterBuilder detectMode(int detectMode) {
            mDownloadTaskInfo.setDetectMode(detectMode);
            return this;
        }

        @Override
        public IVersionUpdater build() {
            return VersionUpdater.create(mDownloadTaskInfo);
        }

    }
}
