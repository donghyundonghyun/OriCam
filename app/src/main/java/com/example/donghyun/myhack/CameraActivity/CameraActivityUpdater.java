package com.example.donghyun.myhack.CameraActivity;

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
        int num = cav.markerList.size();
        for(int i=0;i<num;i++) cav.markerList.get(i).setPositionByOriention();
    }
}
