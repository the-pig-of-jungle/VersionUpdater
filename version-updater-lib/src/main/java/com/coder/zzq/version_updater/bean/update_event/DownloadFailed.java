package com.coder.zzq.version_updater.bean.update_event;

import android.app.DownloadManager;

import com.coder.zzq.version_updater.Constants;

public class DownloadFailed extends VersionUpdateEvent {
    public DownloadFailed(int failedReason) {
        mFailedReason = failedReason;
    }


    private final int mFailedReason;


    public int getFailedReason() {
        return mFailedReason;
    }

    public boolean isResourceNotFound() {
        return mFailedReason == Constants.HTTP_CODE_404;
    }

    public boolean isExternalSpaceInsufficient() {
        return mFailedReason == DownloadManager.ERROR_INSUFFICIENT_SPACE;
    }

    public boolean isExternalDeviceNotFound() {
        return mFailedReason == DownloadManager.ERROR_DEVICE_NOT_FOUND;
    }

    public String description() {
        switch (mFailedReason) {
            case Constants.HTTP_CODE_404:
                return "资源未找到";
            case DownloadManager.ERROR_INSUFFICIENT_SPACE:
                return "外部存储空间不足";
            case DownloadManager.ERROR_DEVICE_NOT_FOUND:
                return "未找到外部存储设备";
            case DownloadManager.ERROR_CANNOT_RESUME:
                return "中断后无法恢复";
            case DownloadManager.ERROR_TOO_MANY_REDIRECTS:
                return "重定向次数过多";
            case DownloadManager.ERROR_FILE_ALREADY_EXISTS:
                return "文件已存在，无法覆盖同名文件";
            case DownloadManager.ERROR_FILE_ERROR:
                return "文件存储错误";
            case DownloadManager.ERROR_UNHANDLED_HTTP_CODE:
            case DownloadManager.ERROR_HTTP_DATA_ERROR:
                return "http请求出错";
            default:
                return "未知错误";
        }
    }

}
