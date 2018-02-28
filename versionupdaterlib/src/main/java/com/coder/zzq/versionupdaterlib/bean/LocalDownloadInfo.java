package com.coder.zzq.versionupdaterlib.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 喜欢、陪你看风景 on 2018/2/14.
 */

public class LocalDownloadInfo {

    public static final String ITEM_DOWNLOAD_ID = "download_id";
    public static final String ITEM_VERSION_CODE = "version_code";


    private long mDownloadId;
    private int mVersionCode;

    public LocalDownloadInfo() {

    }


    public LocalDownloadInfo(String jsonStr) {
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            mDownloadId = jsonObject.getLong(ITEM_DOWNLOAD_ID);
            mVersionCode = jsonObject.getInt(ITEM_VERSION_CODE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ITEM_DOWNLOAD_ID, mDownloadId)
                    .put(ITEM_VERSION_CODE, mVersionCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }


    public long getDownloadId() {
        return mDownloadId;
    }

    public int getVersionCode() {
        return mVersionCode;
    }


    public LocalDownloadInfo setDownloadId(long downloadId) {
        mDownloadId = downloadId;
        return this;
    }

    public LocalDownloadInfo setVersionCode(int versionCode) {
        mVersionCode = versionCode;
        return this;
    }

}
