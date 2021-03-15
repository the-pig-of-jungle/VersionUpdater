package com.coder.zzq.version_updater.bean.download_trigger;

import com.coder.zzq.version_updater.communication.DownloadVersionInfoCache;

public abstract class AbstractDownloadTrigger implements DownloadTrigger {
    private final int mVersionCode;
    private final long mCachedDownloadId;
    private final long mIgnorePeriod;

    protected AbstractDownloadTrigger(int versionCode, long cachedDownloadId, long ignorePeriod) {
        mVersionCode = versionCode;
        mCachedDownloadId = cachedDownloadId;
        mIgnorePeriod = ignorePeriod;
    }

    @Override
    public void cancelUpdate() {
        DownloadVersionInfoCache.ignoreVersion(mVersionCode, mIgnorePeriod);
    }
}
