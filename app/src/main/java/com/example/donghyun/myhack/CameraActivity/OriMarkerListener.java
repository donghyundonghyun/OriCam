package com.example.donghyun.myhack.CameraActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.donghyun.myhack.InfoActivity;
import com.example.donghyun.myhack.OriInfo;

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
        //System.out.println("clicked!!");
        OriInfo oi;
        oi = (OriInfo) v.getTag();
        if(oi.near==1)
        {
            intent.putExtra("ID", oi.ID);
            cameraActivity.startActivity(intent);
        }
        else
        {
            Toast.makeText(cameraActivity.getApplicationContext(),"좀 더 가까이 가서 눌러 주십시오",Toast.LENGTH_SHORT).show();
        }
    }
}
