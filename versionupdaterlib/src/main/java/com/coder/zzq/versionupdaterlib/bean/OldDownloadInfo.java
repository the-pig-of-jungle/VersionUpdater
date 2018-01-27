package com.coder.zzq.versionupdaterlib.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 朱志强 on 2018/1/27.
 */

public class OldDownloadInfo {


    public static final String DOWNLOAD_ID = "download_id";
    public static final String VERSION_CODE = "version_code";

    private long mDownloadId;
    private int mVersionCode;

    public OldDownloadInfo(long downloadId, int versionCode) {
        mDownloadId = downloadId;
        mVersionCode = versionCode;
    }

    public OldDownloadInfo(String jsonStr) {
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            mDownloadId = jsonObject.getLong(DOWNLOAD_ID);
            mVersionCode = jsonObject.getInt(VERSION_CODE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(DOWNLOAD_ID, mDownloadId)
                    .put(VERSION_CODE, mVersionCode);
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
}
