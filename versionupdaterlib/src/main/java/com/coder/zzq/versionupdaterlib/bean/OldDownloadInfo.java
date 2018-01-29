package com.coder.zzq.versionupdaterlib.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 朱志强 on 2018/1/27.
 */

public class OldDownloadInfo {


    public static final String DOWNLOAD_ID = "download_id";
    public static final String VERSION_CODE = "version_code";
    public static final String FORCE_UPDATE = "force_update";
    public static final String SUPPRESSED_UPDATE = "suppressed";

    private long mDownloadId;
    private int mVersionCode;
    private boolean mIsForceUpdate;
    private boolean mSuppressedUpdate;

    public OldDownloadInfo() {

    }

    public OldDownloadInfo(long downloadId, int versionCode) {
        mDownloadId = downloadId;
        mVersionCode = versionCode;
    }

    public OldDownloadInfo(String jsonStr) {
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            mDownloadId = jsonObject.getLong(DOWNLOAD_ID);
            mVersionCode = jsonObject.getInt(VERSION_CODE);
            mIsForceUpdate = jsonObject.getBoolean(FORCE_UPDATE);
            mSuppressedUpdate = jsonObject.getBoolean(SUPPRESSED_UPDATE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(DOWNLOAD_ID, mDownloadId)
                    .put(VERSION_CODE, mVersionCode)
                    .put(FORCE_UPDATE, mIsForceUpdate)
                    .put(SUPPRESSED_UPDATE, mSuppressedUpdate);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }

    public long getDownloadId() {
        return mDownloadId;
    }

    public void setDownloadId(long downloadId) {
        mDownloadId = downloadId;
    }

    public int getVersionCode() {
        return mVersionCode;
    }

    public void setVersionCode(int versionCode) {
        mVersionCode = versionCode;
    }

    public boolean isForceUpdate() {
        return mIsForceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        mIsForceUpdate = forceUpdate;
        mSuppressedUpdate = forceUpdate ? false : mSuppressedUpdate;
    }

    public boolean isSuppressedUpdate() {
        return mIsForceUpdate ? false : mSuppressedUpdate;
    }

    public void setSuppressedUpdate(boolean suppressedUpdate) {
        if (!mIsForceUpdate) {
            mSuppressedUpdate = suppressedUpdate;
        }
    }

    public void reset() {
        mDownloadId = 0;
        mVersionCode = 0;
        mIsForceUpdate = false;
        mSuppressedUpdate = false;
    }
}
