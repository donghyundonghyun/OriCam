package com.example.donghyun.myhack.CameraActivity;

import android.view.TextureView;

import com.example.donghyun.myhack.OriInfo;
import com.example.donghyun.myhack.R;

import java.util.ArrayList;

/**
 * Created by Ahn on 2017-01-23.
 */

public class CameraActivityView {
    private TextureView mCameraTextureView;
    private CameraActivity ca;
    public Orientation ot;
    public ArrayList<OriMarker> markerList;

    public CameraActivityView(CameraActivity cameraActivity, ArrayList<OriInfo> oiList, Orientation pot)
    {
        ca= cameraActivity;
        ot = pot;

        //mCameraTextureView = (TextureView) ca.findViewById(R.id.cameraTextureView);

        markerList=new ArrayList<OriMarker>();
        for(int i=0;i<oiList.size(); i++)
        {
            markerList.add(new OriMarker(ca,oiList.get(i),ot));
        }

        for(int i=0;i<markerList.size(); i++)
        {
           markerList.get(i).setPositionByOriention();
        }

    }
}
