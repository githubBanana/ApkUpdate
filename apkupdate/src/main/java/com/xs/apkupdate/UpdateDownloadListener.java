package com.xs.apkupdate;

public interface UpdateDownloadListener {

    public void onStarted();
    public void onProgressChanged(int progress, String downloadUrl);
    public void onFinished(float completeSize, String downloadUrl);
    public void onFailure();
}
