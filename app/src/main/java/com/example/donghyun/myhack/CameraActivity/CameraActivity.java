package com.example.donghyun.myhack.CameraActivity;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.TextureView;

import com.example.donghyun.myhack.OriInfo;
import com.example.donghyun.myhack.R;
import com.example.donghyun.myhack.UpdateManager;

import java.util.ArrayList;
import java.util.List;

public class CameraActivity extends AppCompatActivity {
    private Orientation ot;
    private TextureView mCameraTextureView; // ddd
    CameraActivityView cav;
    CameraActivityUpdater cau;
    OriMarkerListener oml;
    PreviewManager previewManager;
    UpdateManager updateManager;
    int version;
    boolean pFlag;


    private ArrayList<OriInfo> ories;

    DisplayMetrics dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        pFlag=true;

        ories = getIntent().getParcelableArrayListExtra("ories"); //수정필요

        version= android.os.Build.VERSION.SDK_INT;
        dm = getApplicationContext().getResources().getDisplayMetrics();

        mCameraTextureView = (TextureView) findViewById(R.id.cameraTextureView);

        previewManager = new PreviewManager(this, mCameraTextureView);

        ot=new Orientation(this);
        cav = new CameraActivityView(this,ories,ot);


        cau =new CameraActivityUpdater(cav,ot,this);

        oml =new OriMarkerListener(this);

        updateManager=UpdateManager.getInstance();
        updateManager.setCameraActivity(this);
        updateManager.updateCameraActivity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pFlag=true;
        ot.onResume();
        previewManager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pFlag=false;
        ot.onPause();
        previewManager.onPause();
    }

    public void updateByGPS(double lat, double lon, List<OriInfo> ories)
    {
        if(pFlag==false)return;
        cau.updateByGPS(lat,lon,ories);
    }

    protected  void updateByOrientaion()
    {
        cau.updateViewByOrientaion();
    }

}