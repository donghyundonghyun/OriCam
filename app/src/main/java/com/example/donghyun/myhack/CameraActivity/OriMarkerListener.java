package com.example.donghyun.myhack.CameraActivity;

import android.content.Intent;
import android.view.View;

import com.example.donghyun.myhack.InfoActivity;

/**
 * Created by Ahn on 2017-02-13.
 */

public class OriMarkerListener implements View.OnClickListener{

    CameraActivity cameraActivity;
    static OriMarkerListener oriMarkerListener;

    OriMarkerListener(CameraActivity ca)
    {
        cameraActivity=ca;
        oriMarkerListener=this;
    }

    static public OriMarkerListener getInstance()
    {
        return oriMarkerListener;
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(cameraActivity.getApplicationContext(), InfoActivity.class);
        System.out.println("clicked!!");
        cameraActivity.startActivity(intent);
    }
}
