package com.example.donghyun.myhack.CameraActivity;

import android.location.Location;
import android.util.Log;

import com.example.donghyun.myhack.OriInfo;

import java.util.List;

/**
 * Created by Ahn on 2017-01-24.
 */

public class CameraActivityUpdater
{
    CameraActivityView cav;
    Orientation orientation;

    public CameraActivityUpdater(CameraActivityView pcav, Orientation ot)
    {
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
        orientation.setMyLocation(lat, lon);
        cav.setOriMarkerList(iol);
        Log.i("Update!!  >","LAT:: "+lat+" / LON:: "+lon);
    }
}
