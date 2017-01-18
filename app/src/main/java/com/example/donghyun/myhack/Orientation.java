package com.example.donghyun.myhack;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by DongHyun on 2016-12-20.
 */


//public class Orientation extends Thread implements SensorEventListener {
public class Orientation implements SensorEventListener {
    //TextView[] tv;
    SensorManager m_sensor_manager;
    Sensor m_ot_sensor;
    CameraActivity cameraActivity;

    ImageView[] img = new ImageView[3];

    Gpsinfo gpsinfo;

    BuildingInfo[] bi;
    BuildingInfo my;

    float viewAngle = 20;


    float width;
    float height;

    float[] imgWidth = new float[3];
    float[] imgHeight = new float[3];


    public Orientation(CameraActivity ma)
    {
        cameraActivity =ma;
        gpsinfo = new Gpsinfo(cameraActivity.getApplicationContext());

        my = new BuildingInfo(gpsinfo.getLatitude(), gpsinfo.getLongitude(),0);

        bi = new BuildingInfo[3];
/*
        bi[2] = new BuildingInfo(127.075201, 37.549441, 0);//학생회관 37.549441, 127.075201 ->충무
        bi[1] = new BuildingInfo(127.073152, 37.550276, 0);//광개토 37.550276, 127.073152 ->
        bi[0] = new BuildingInfo(127.073952, 37.552261,0);//충무관 ->학생
*/
        bi[0] = new BuildingInfo(37.472559, 127.04298, 0);      //언남고
        bi[1] = new BuildingInfo(37.46808, 127.038968, 0);      //AT센터
        bi[2] = new BuildingInfo(37.47588, 127.052701, 0);      //포이초

        width = ma.dm.widthPixels;
        height = ma.dm.heightPixels;

        img[2] = (ImageView)ma.findViewById(R.id.duck2); //광개토
        img[1] = (ImageView)ma.findViewById(R.id.duck1); //충무관
        img[0] = (ImageView)ma.findViewById(R.id.duck3); //학생

        for(int i=0;i<3;i++) {
            img[i].getLayoutParams().width = 500;
            img[i].getLayoutParams().height = 500;

            imgWidth[i]=(float) img[i].getLayoutParams().width;
            imgHeight[i]=(float) img[i].getLayoutParams().height;


            //Log.i("값 비교 -----> " + img.getX()+"",img.getY()+" : " + imgHeight + " : " + img.getHeight());
            final int finali = i;

            img[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(finali==0)
                        Toast.makeText(cameraActivity.getApplicationContext(),"더 가까이 가서 선택해주세요",Toast.LENGTH_SHORT).show();
                    else{
                        Intent intent = new Intent(cameraActivity.getApplicationContext(), InfoActivity.class);
                        intent.putExtra("index", finali);
                        cameraActivity.startActivity(intent);
                    }
                }
            });

        }

        img[0].getLayoutParams().width = 500;
        img[0].getLayoutParams().height = 500;

        // 시스템서비스로부터 SensorManager 객체를 얻는다.
        m_sensor_manager = (SensorManager)ma.getSystemService(SENSOR_SERVICE);
        // SensorManager 를 이용해서 방향 센서 객체를 얻는다.
        m_ot_sensor = m_sensor_manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        m_sensor_manager.registerListener(this, m_ot_sensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        double myWay, grad, bearing;

        if(event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
                for (int i = 0; i < 3; i++) {

                    bearing = bearingP1toP2(my, bi[i]);

                    myWay = event.values[0];        //방위
                    grad = event.values[1];     //경사도

                    if(bearing > 180)
                        bearing -= 360;

                    if(myWay > 180)
                        myWay -= 360;

                    if (isBuildingVisible(bearing, myWay, grad)) {

                        img[i].setVisibility(View.VISIBLE);
/*
                        Log.i(i + "", "번째");
                        Log.i("내 위도 : " + my.lat, " 내 경도 : " + my.lon);
                        Log.i("건물 위도 : " + bi[i].lat, "건물 경도 : " + bi[i].lon);
*/
                        img[i].setX((float)((width - imgWidth[i]) / 2 + ((width / 2.0) * ((bearing - myWay) / viewAngle - 5))));
                        img[i].setY((float)((height - imgHeight[i]) / 2 + (height / 2.0) * (-(grad + 90.0) / 35.0)));
                    }
                }

          /*  String str;
            // 첫번째 데이터인 방위값으로 문자열을 구성하여 텍스트뷰에 출력한다.
            str = "azimuth(z) : " + (int) event.values[0];
            tv[0].setText(str);

            // 두번째 데이터인 경사도로 문자열을 구성하여 텍스트뷰에 출력한다.
            str = "pitch(x) : " + (int) event.values[1];
            tv[1].setText(str);

            // 세번째 데이터인 좌우 회전값으로 문자열을 구성하여 텍스트뷰에 출력한다.
            str = "roll(y) : " + (int) event.values[2];
            tv[2].setText(str);
*/
            /*
            // 함수의 출력횟수를 텍스트뷰에 출력한다.
            m_check_count++;
            str = "호출 횟수 : " + m_check_count + " 회";
            m_check_view.setText(str);
            */
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