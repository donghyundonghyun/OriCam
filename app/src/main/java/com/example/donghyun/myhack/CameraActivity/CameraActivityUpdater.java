package com.example.donghyun.myhack.CameraActivity;

import android.util.Log;

/**
 * Created by Ahn on 2017-01-24.
 */

public class CameraActivityUpdater
{
    CameraActivityView cav;

    public CameraActivityUpdater(CameraActivityView pcav)
    {
        cav=pcav;
    }

    public void updateViewByOrientaion()
    {
        //Log.i("    업데이트   ","호출");
        int num = cav.markerList.size();
        for(int i=0;i<num;i++) cav.markerList.get(i).setPositionByOriention();
    }
}
