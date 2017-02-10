package com.example.donghyun.myhack.CameraActivity;

import android.view.TextureView;

/**
 * Created by Ahn on 2017-02-02.
 */

public class PreviewManager {
    int version;
    Preview1 preview1;
    Preview2 preview2;
    CameraActivity cameraActivity;
    TextureView cameraTextureView;

    public PreviewManager(CameraActivity cameraActivity, TextureView cameraTextureView)
    {
        version= android.os.Build.VERSION.SDK_INT;
        this.cameraActivity=cameraActivity;
        this.cameraTextureView = cameraTextureView;

        if(version<21)preview1=new Preview1(cameraActivity, cameraTextureView);
        else preview2 = new Preview2(cameraActivity, cameraTextureView);
    }


    protected void onResume()
    {
        if(version<21)preview1.onResume();
        else preview2.onResume();
    }

    protected void onPause()
    {
        if(version<21)preview1.onPause();
        else preview2.onPause();
    }
}
