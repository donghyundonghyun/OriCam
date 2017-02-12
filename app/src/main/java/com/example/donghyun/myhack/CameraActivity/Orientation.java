package com.example.donghyun.myhack.CameraActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.ImageView;

import com.example.donghyun.myhack.BuildingInfo;
import com.example.donghyun.myhack.Gpsinfo;
import com.example.donghyun.myhack.OriInfo;

import java.util.ArrayList;

import static android.content.Context.SENSOR_SERVICE;


/**
 * Created by DongHyun on 2016-12-20.
 */


//public class Orientation extends Thread implements SensorEventListener {
public class Orientation implements SensorEventListener {
    //TextView[] tv;

    boolean flag;
    private SensorManager m_sensor_manager;
    private Sensor m_ot_sensor;
    private CameraActivity cameraActivity;

    private ImageView[] img;

    private Gpsinfo gpsinfo;

    BuildingInfo my;
    BuildingInfo[] bi;

    float viewAngle = 20;


    float width;
    float height;

    float[] imgWidth = new float[3];
    float[] imgHeight = new float[3];

    double myWay, grad, bearing;

    ArrayList<OriInfo> ories;


    public Orientation(CameraActivity ma, ArrayList<OriInfo> ories)
    {
        this.ories = ories;

        flag=true;

        cameraActivity =ma;
        gpsinfo = new Gpsinfo(cameraActivity.getApplicationContext());

        my = new BuildingInfo(gpsinfo.getLatitude(), gpsinfo.getLongitude(),0);
        bi = new BuildingInfo[ories.size()];

        imgWidth = new float[ories.size()];
        imgHeight = new float[ories.size()];
        img = new ImageView[ories.size()];

        for(int i=0;i<ories.size();i++){
            bi[i] = new BuildingInfo(ories.get(i).lon, ories.get(i).lat, 0);
        }

        width = ma.dm.widthPixels;
        height = ma.dm.heightPixels;


        // 시스템서비스로부터 SensorManager 객체를 얻는다.
        m_sensor_manager = (SensorManager)ma.getSystemService(SENSOR_SERVICE);
        // SensorManager 를 이용해서 방향 센서 객체를 얻는다.
        m_ot_sensor = m_sensor_manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        m_sensor_manager.registerListener(this, m_ot_sensor, SensorManager.SENSOR_DELAY_UI);
    }

    public void onPause()
    {
        flag=false;
    }
    public void onResume()
    {
        flag=true;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {


        if(event.sensor.getType() == Sensor.TYPE_ORIENTATION&&flag==true)
        {
            myWay = event.values[0];        //방위
            grad = event.values[1];     //경사도

            if(myWay > 180)myWay -= 360;
            cameraActivity.updateByOrientaion();
                       //img[i].setX((float)((width - imgWidth[i]) / 2 + ((width / 2.0) * ((bearing - myWay) / viewAngle - 5))));
                       //img[i].setY((float)((height - imgHeight[i]) / 2 + (height / 2.0) * (-(grad + 90.0) / 35.0)))
        }



    }

    public BuildingInfo getMakerPoint(OriInfo oriinfo,int iw,int ih)
    {
        BuildingInfo tbi =  new BuildingInfo(oriinfo.lat,oriinfo.lon,0);
        bearing = bearingP1toP2(my, tbi);

        Log.i(oriinfo.name+":",""+myWay);
        if(bearing > 180)bearing -= 360;
        else if(bearing<-180) bearing +=360;

        if (isBuildingVisible(bearing, myWay, grad)) {
            double x,y;

            x=((width - iw) / 2 + ((width / 2.0) * ((bearing - myWay) / (viewAngle - 5))));
            y=((height - ih) / 2 + (height / 2.0) * (-(grad + 90.0) / 35.0));

            return new BuildingInfo(x,y,1);
        }
        else
        {
            return new BuildingInfo(0,0,-1);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    protected void finalize() throws Throwable {
        m_sensor_manager.unregisterListener(this);
        super.finalize();
    }

    public double radian2degree(double _num)
    {
        return _num * 180.0 / Math.PI;
    }

    public double degree2radian(double _num)
    {
        return _num * Math.PI / 180.0;
    }

    public double bearingP1toP2(BuildingInfo myLoc, BuildingInfo dest)
    {
        //위도나 경도는 지구 중심을 기반으로 하는 각도이기 때문에 라디안 각도로 변환
        double my_lat_radian = degree2radian(myLoc.lat);
        double my_lon_radian = degree2radian(myLoc.lon);

        double dest_lat_radian = degree2radian(dest.lat);
        double dest_lon_radian = degree2radian(dest.lon);

        double radian_distance = Math.acos(Math.sin(my_lat_radian) * Math.sin(dest_lat_radian)
                + Math.cos(my_lat_radian) * Math.cos(dest_lat_radian) * Math.cos(my_lon_radian - dest_lon_radian));

        //목적지 방향 구함
        double radian_bearing = Math.acos((Math.sin(dest_lat_radian) - Math.sin(my_lat_radian) * Math.cos(radian_distance))
            / (Math.cos(my_lat_radian) * Math.sin(radian_distance)));

        double true_bearing = radian2degree(radian_bearing);


        if(Math.sin(dest_lon_radian - my_lon_radian) < 0)
            true_bearing = 360 - true_bearing;

        return true_bearing;
    }

    public boolean isBuildingVisible(double bearing, double myWay, double grad)
    {
        if(bearing - viewAngle <= myWay && myWay <= bearing + viewAngle && -135 <= grad && grad <= -45)
            return true;
        else
            return false;
    }
}