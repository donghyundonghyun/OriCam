package com.example.donghyun.myhack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.TextureView;

import java.util.ArrayList;

public class CameraActivity extends AppCompatActivity {
    private TextureView mCameraTextureView;
    private Preview mPreview;
    private PastPreview pPreview;
    int version;


    private ArrayList<OriInfo> ories;

    DisplayMetrics dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ories = getIntent().getParcelableArrayListExtra("ories");

        version= android.os.Build.VERSION.SDK_INT;



        dm = getApplicationContext().getResources().getDisplayMetrics();

        mCameraTextureView = (TextureView) findViewById(R.id.cameraTextureView);
        //mPreview = new Preview(this, mCameraTextureView);

        if(version<21)pPreview=new PastPreview(this, mCameraTextureView);
        else mPreview = new Preview(this, mCameraTextureView);

        new Orientation(this, ories);


    }

    @Override
    protected void onResume() {
        super.onResume();
        PreviewOnResume(version);
    }

    @Override
    protected void onPause() {
        super.onPause();
        PreviewOnPause(version);
    }

    protected void PreviewOnResume(int ver)
    {
        if(version<21)pPreview.onResume();
        else mPreview.onResume();
    }

    protected void PreviewOnPause(int ver)
    {
        if(version<21)pPreview.onPause();
        else mPreview.onPause();
    }
}