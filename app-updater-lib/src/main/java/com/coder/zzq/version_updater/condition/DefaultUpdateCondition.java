package com.coder.zzq.version_updater.condition;

public class DefaultUpdateCondition implements UpdateCondition {
    @Override
    public boolean needUpdate(int remoteVersionCode, int localVersionCode) {
        return remoteVersionCode > localVersionCode;
    }
}
