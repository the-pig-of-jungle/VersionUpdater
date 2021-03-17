package com.coder.zzq.version_updater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.coder.zzq.version_updater.bean.update_event.LocalVersionIsUpToDate;
import com.coder.zzq.version_updater.communication.DetectObserverRegisterProvider;
import com.coder.zzq.version_updater.communication.DownloadVersionInfoCache;
import com.coder.zzq.version_updater.communication.UpdateEventNotifier;
import com.coder.zzq.version_updater.condition.UpdateCondition;
import com.coder.zzq.version_updater.tasks.TaskScheduler;
import com.coder.zzq.version_updater.util.UpdateUtil;

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

        if (!mCheckConfig.getUpdateCondition().needUpdate(mCheckConfig.getRemoteVersion().getVersionCode(), UpdateUtil.getLocalVersionCode())) {
            if (mCheckConfig.getDetectMode() == CheckConfig.DETECT_MODE_MANUAL) {
                UpdateEventNotifier.get().notifyEvent(new LocalVersionIsUpToDate());
            }
            if (DownloadVersionInfoCache.existsCachedDownloadVersion()) {
                TaskScheduler.cleanApkFile();
            }
            return;
        }

        if (mCheckConfig.getDetectMode() == CheckConfig.DETECT_MODE_AUTO
                && DownloadVersionInfoCache.isVersionIgnored(mCheckConfig.getRemoteVersion().getVersionCode(), mCheckConfig.getIgnorePeriod())) {
            return;
        }

        DownloadVersionInfoCache.clearIgnoreVersion();

        TaskScheduler.downloadApk(mCheckConfig.getRemoteVersion());
    }


    public interface IUpdaterBuilder {

        IUpdaterBuilder remoteVersionCode(int versionCode);

        IUpdaterBuilder remoteVersionName(String versionName);

        IUpdaterBuilder remoteApkUrl(String apkUrl);

        IUpdaterBuilder remoteVersionDesc(String desc);

        IUpdaterBuilder forceUpdate(boolean forceUpdate);

        IUpdaterBuilder notificationVisibility(boolean visible);

        IUpdaterBuilder updateCondition(UpdateCondition condition);

        IUpdaterBuilder ignorePeriod(long period);

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
        public IUpdaterBuilder updateCondition(UpdateCondition condition) {
            mCheckConfig.setUpdateCondition(condition);
            return this;
        }

        @Override
        public IUpdaterBuilder ignorePeriod(long period) {
            mCheckConfig.setIgnorePeriod(period);
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
