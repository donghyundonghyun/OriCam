package com.example.donghyun.myhack.CameraActivity;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.TextureView;

import com.example.donghyun.myhack.OriInfo;
import com.example.donghyun.myhack.R;

import java.util.ArrayList;

public class CameraActivity extends AppCompatActivity {
    private Orientation ot;
    private TextureView mCameraTextureView; // ddd
    CameraActivityView cav;
    CameraActivityUpdater cau;
    OriMarkerListener oml;
    PreviewManager previewManager;
    int version;


    private ArrayList<OriInfo> ories;

    DisplayMetrics dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        ories = getIntent().getParcelableArrayListExtra("ories"); //수정필요

        version= android.os.Build.VERSION.SDK_INT;
        dm = getApplicationContext().getResources().getDisplayMetrics();

        mCameraTextureView = (TextureView) findViewById(R.id.cameraTextureView);

        previewManager = new PreviewManager(this, mCameraTextureView);

        ot=new Orientation(this);
        cav = new CameraActivityView(this,ories,ot);

        cau =new CameraActivityUpdater(cav,ot);

        oml =new OriMarkerListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ot.onResume();
        previewManager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ot.onPause();
        previewManager.onPause();
    }

    public void updateByGPS(Location location)
    {
        cau.updateByGPS(location);
    }

    protected  void updateByOrientaion()
    {
        cau.updateViewByOrientaion();
    }

}