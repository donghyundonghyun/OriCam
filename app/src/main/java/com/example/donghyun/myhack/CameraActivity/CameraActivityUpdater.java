package com.example.donghyun.myhack.CameraActivity;

import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.example.donghyun.myhack.OriInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahn on 2017-01-24.
 */

public class CameraActivityUpdater
{
    CameraActivity ca;
    CameraActivityView cav;
    Orientation orientation;

    public CameraActivityUpdater(CameraActivityView pcav, Orientation ot, CameraActivity pca)
    {
        ca = pca;
        cav=pcav;
        orientation=ot;
    }

    public void updateViewByOrientaion()
    {
        //Log.i("    업데이트   ","호출");
        int num = cav.markerList.size();
        for(int i=0;i<num;i++) cav.markerList.get(i).setPositionByOriention();
    }

    public void updateByGPS(double lat, double lon , List<OriInfo> iol)
    {
        int minDistance=200;

        orientation.setMyLocation(lat, lon);

        ArrayList<OriMarker> markerList;
        ArrayList<OriMarker> postMarkerList;
        OriInfo oriInfo;
        OriInfo pOriinfo;
        OriMarker pOriMarker;

        markerList= new ArrayList<OriMarker>();
        postMarkerList = cav.getOriMarkerList();
       int pIndex=-1;

        for(int i=0;i<iol.size(); i++)
        {
            boolean eFlag=false;

            oriInfo=iol.get(i);
            if(minDistance>(int)oriInfo.distance)minDistance=(int)oriInfo.distance;
            if(oriInfo.near==1||oriInfo.near==2)
            {
                eFlag=false;
                for(int p=0;p<postMarkerList.size();p++)
                {
                    pOriinfo=postMarkerList.get(p).oi;
                    if((pOriinfo.near==oriInfo.near) && (pOriinfo.ID==oriInfo.ID))
                    {
                        eFlag=true;
                        pIndex=p;
                        break;
                    }
                }

                if(eFlag==true)
                {
                    markerList.add(postMarkerList.get(pIndex));
                    postMarkerList.get(pIndex).updateOriInfo(oriInfo);
                }
                else markerList.add(new OriMarker(ca,oriInfo,orientation));
            }
        }

        if(minDistance<=100)Toast.makeText(ca.getApplicationContext(),"근처에 오리가 있습니다"+"( "+minDistance+"m )",Toast.LENGTH_SHORT);
        cav.setOriMarkerList(markerList);

        for(int i=markerList.size()-1;i>-1; i--)
        {
            Log.i(markerList.get(i).oi.name,"_distance :"+markerList.get(i).oi.distance);
            markerList.get(i).setPositionByOriention();
        }

        Log.i("Update!!  >","LAT:: "+lat+" / LON:: "+lon);
    }
}
