package com.example.donghyun.myhack.CameraActivity;

import android.location.Location;
import android.util.Log;

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

    public void updateByGPS(Location location)
    {
        orientation.setMyLocation(location);
        cav.setOriMarkerList(null);
        Log.i("Update!!  >","LAT:: "+location.getLatitude()+" / LON:: "+location.getLongitude());
    }
}
