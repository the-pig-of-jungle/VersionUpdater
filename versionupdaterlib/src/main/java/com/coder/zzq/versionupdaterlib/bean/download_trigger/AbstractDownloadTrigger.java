package com.coder.zzq.versionupdaterlib.bean.download_trigger;

import com.coder.zzq.versionupdaterlib.communication.DownloadVersionInfoCache;

public abstract class AbstractDownloadTrigger implements DownloadTrigger {
    private final int mVersionCode;

    protected AbstractDownloadTrigger(int versionCode) {
        mVersionCode = versionCode;
    }

    @Override
    public void ignoreThisVersion() {
        DownloadVersionInfoCache.setVersionIgnored(mVersionCode);
    }
}
