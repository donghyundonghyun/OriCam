package com.example.donghyun.myhack.CameraActivity;

import android.view.TextureView;

import com.example.donghyun.myhack.OriInfo;
import com.example.donghyun.myhack.R;

import java.util.ArrayList;
import java.util.List;

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

    public void setOriMarkerList(List<OriInfo> oi)
    {
        OriInfo oriInfo;
        for(int i=0; i<markerList.size();i++) markerList.get(i).setVisible(false);
        markerList=new ArrayList<OriMarker>();
        for(int i=0;i<oi.size(); i++)
        {
            oriInfo=oi.get(i);

            //바로 아래 것으로 코드 바꾸면 가까이 있는 것만 뜸
            //if(oriInfo.near==1)markerList.add(new OriMarker(ca,oriInfo,ot));
            if(oriInfo.near!=1)markerList.add(new OriMarker(ca,oriInfo,ot));
        }

        for(int i=0;i<markerList.size(); i++)
        {
            markerList.get(i).setPositionByOriention();
        }

    }
}
