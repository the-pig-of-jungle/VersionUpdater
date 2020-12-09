package com.coder.zzq.versionupdaterlib.bean.download_trigger;

import com.coder.zzq.versionupdaterlib.communication.DownloadVersionInfoCache;

public abstract class AbstractDownloadTrigger implements DownloadTrigger {
    private final int mVersionCode;
    private final long mCachedDownloadId;

    protected AbstractDownloadTrigger(int versionCode, long cachedDownloadId) {
        mVersionCode = versionCode;
        mCachedDownloadId = cachedDownloadId;
    }

    @Override
    public void ignoreThisVersion() {
        DownloadVersionInfoCache.setVersionIgnored(mVersionCode);
    }
}
