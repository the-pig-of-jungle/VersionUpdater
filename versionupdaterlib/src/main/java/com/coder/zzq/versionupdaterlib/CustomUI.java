package com.coder.zzq.versionupdaterlib;

/**
 * Created by 朱志强 on 2018/1/26.
 */

public interface CustomUI {
    void onHasNewVersion();

    void onConfirmDownload();

    void onCancelDownload();

    void onDownloadComplete();

    void onConfirmInstall();

    void onCancelInstall();

    boolean needNotifiedProgress();
}
