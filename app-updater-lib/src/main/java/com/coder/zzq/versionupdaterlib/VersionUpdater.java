package com.coder.zzq.versionupdaterlib;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.coder.zzq.versionupdaterlib.bean.download_event.LocalVersionIsUpToDate;
import com.coder.zzq.versionupdaterlib.communication.DetectObserverRegisterProvider;
import com.coder.zzq.versionupdaterlib.communication.DownloadEventNotifier;
import com.coder.zzq.versionupdaterlib.communication.DownloadVersionInfoCache;
import com.coder.zzq.versionupdaterlib.tasks.TaskScheduler;
import com.coder.zzq.versionupdaterlib.util.UpdateUtil;

//import com.coder.zzq.versionupdaterlib.communication.DetectObserverRegister;

/**
 * Created by zhiqiang.zhu on 2018/1/31.
 */

public class VersionUpdater implements IVersionUpdater {
    private static final VersionUpdater sVersionUpdater = new VersionUpdater();

    protected static IVersionUpdater create(CheckConfig checkConfig) {
        return sVersionUpdater.setCheckConfig(checkConfig);
    }

    private CheckConfig mCheckConfig;

    private VersionUpdater() {

    }

    protected VersionUpdater setCheckConfig(CheckConfig checkConfig) {
        mCheckConfig = checkConfig;
        return this;
    }

    public static IUpdaterBuilder builder() {

        return new UpdateBuilder();
    }

    @Override
    public void autoCheck() {
        mCheckConfig.setDetectMode(CheckConfig.DETECT_MODE_AUTO);
        check();
    }

    @Override
    public void manualCheck() {
        mCheckConfig.setDetectMode(CheckConfig.DETECT_MODE_MANUAL);
        check();
    }

    private void check() {
        DetectObserverRegisterProvider.getDetectObserverRegister()
                .register(mCheckConfig);

        if (mCheckConfig.getRemoteVersion().getVersionCode() <= UpdateUtil.getVersionCode()) {
            if (mCheckConfig.getDetectMode() == CheckConfig.DETECT_MODE_MANUAL) {
                DownloadEventNotifier.get().notifyEvent(new LocalVersionIsUpToDate());
            }
            if (DownloadVersionInfoCache.getDownloadVersionCodeFromCache() != 0) {
                TaskScheduler.cleanApkFile();
            }
            return;
        }

        if (mCheckConfig.getDetectMode() == CheckConfig.DETECT_MODE_AUTO
                && DownloadVersionInfoCache.isVersionIgnored(mCheckConfig.getRemoteVersion().getVersionCode())) {
            return;
        }

        DownloadVersionInfoCache.setVersionIgnored(0);

        TaskScheduler.downloadApk(mCheckConfig.getRemoteVersion());
    }


    public interface IUpdaterBuilder {

        IUpdaterBuilder remoteVersionCode(int versionCode);

        IUpdaterBuilder remoteVersionName(String versionName);

        IUpdaterBuilder remoteApkUrl(String apkUrl);

        IUpdaterBuilder remoteVersionDesc(String desc);

        IUpdaterBuilder forceUpdate(boolean forceUpdate);

        IUpdaterBuilder notificationVisibility(boolean visible);

        IUpdaterBuilder observer(AppCompatActivity activity);

        IUpdaterBuilder observer(Fragment fragment);

        IVersionUpdater build();
    }

    public static class UpdateBuilder implements IUpdaterBuilder {
        private final CheckConfig mCheckConfig;

        private UpdateBuilder() {
            mCheckConfig = new CheckConfig();
        }

        public IUpdaterBuilder remoteVersionCode(int versionCode) {
            mCheckConfig.getRemoteVersion().setVersionCode(versionCode);
            return this;
        }

        @Override
        public IUpdaterBuilder remoteVersionName(String versionName) {
            mCheckConfig.getRemoteVersion().setVersionName(versionName);
            return this;
        }

        @Override
        public IUpdaterBuilder remoteApkUrl(String apkUrl) {
            mCheckConfig.getRemoteVersion().setApkUrl(apkUrl);
            return this;
        }

        @Override
        public IUpdaterBuilder remoteVersionDesc(String desc) {
            mCheckConfig.getRemoteVersion().setVersionDesc(desc);
            return this;
        }

        @Override
        public IUpdaterBuilder forceUpdate(boolean forceUpdate) {
            mCheckConfig.getRemoteVersion().setForceUpdate(forceUpdate);
            return this;
        }

        @Override
        public IUpdaterBuilder notificationVisibility(boolean visible) {
            mCheckConfig.setNotificationVisibility(visible);
            return this;
        }

        @Override
        public IUpdaterBuilder observer(AppCompatActivity activity) {
            mCheckConfig.getObserverPage()
                    .setActivityContext(activity)
                    .setViewModelStoreOwner(activity)
                    .setLifecycleOwner(activity);
            return this;
        }

        @Override
        public IUpdaterBuilder observer(Fragment fragment) {
            mCheckConfig.getObserverPage()
                    .setActivityContext(fragment.getActivity())
                    .setViewModelStoreOwner(fragment)
                    .setLifecycleOwner(fragment.getViewLifecycleOwner());
            return this;
        }

        @Override
        public IVersionUpdater build() {
            return VersionUpdater.create(mCheckConfig);
        }

    }
}
