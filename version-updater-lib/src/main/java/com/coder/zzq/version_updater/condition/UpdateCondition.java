package com.coder.zzq.version_updater.condition;

public interface UpdateCondition {
    boolean needUpdate(int remoteVersionCode, int localVersionCode);
}
