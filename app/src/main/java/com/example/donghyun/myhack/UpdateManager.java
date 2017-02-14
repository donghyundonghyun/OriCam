package com.example.donghyun.myhack;

import android.location.Location;

import com.example.donghyun.myhack.CameraActivity.CameraActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wolf on 2017-02-14.
 */

public class UpdateManager {
    static UpdateManager updateManager;

    CameraActivity cameraActivity;

    double lat,lon;
    List<OriInfo> oil;


    public static UpdateManager getInstance() {
        if (updateManager == null) updateManager = new UpdateManager();
        return updateManager;
    }

    public void update(double lat, double lon, List<OriInfo> paloi)
    {
        this.lat=lat;
        this.lon=lon;
        oil= paloi;
        if(cameraActivity!=null)updateCameraActivity();
    }

    public void updateCameraActivity()
    {
        cameraActivity.updateByGPS(lat,lon,oil);
    }

    public void setCameraActivity(CameraActivity ca)
    {
        cameraActivity=ca;
    }




}
