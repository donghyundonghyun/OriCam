package com.example.donghyun.myhack.CameraActivity;

import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.donghyun.myhack.BuildingInfo;
import com.example.donghyun.myhack.OriInfo;
import com.example.donghyun.myhack.R;

/**
 * Created by Ahn on 2017-01-23.
 */

public class OriMarker {

    FrameLayout fl;
    OriInfo oi;
    Orientation ot;
    ImageView oriView;
    ImageView facilityView[];
    ImageView SBView;
    TextView infoText;

    public int markerWidth, markerHeight;

    public OriMarker(CameraActivity pca, OriInfo poi, Orientation pot)
    {
        fl = (FrameLayout) pca.findViewById(R.id.activity_main);
        oi= poi;
        ot= pot;

        oriView= new ImageView(pca.getApplicationContext());
        oriView.setImageResource(R.drawable.duck1);
        oriView.setOnClickListener(OriMarkerListener.getInstance());
        fl.addView(oriView, new FrameLayout.LayoutParams(500,500));
        //markerHeight = oriView.getLayoutParams().height = 500;
        //markerWidth = oriView.getLayoutParams().width = 500;

        infoText= new TextView(pca.getApplicationContext());
        fl.addView(infoText, new FrameLayout.LayoutParams(500,100));
        infoText.setGravity(Gravity.CENTER_HORIZONTAL);
        infoText.setText(oi.name+": "+(int)oi.distance+"m");
        //infoText.setBackgroundColor(Color.BLACK);
        //infoText.getLayoutParams().width=500;a

        SBView = new ImageView(pca.getApplicationContext());
        SBView.setScaleType(ImageView.ScaleType.FIT_XY);
        SBView.setImageResource(R.drawable.bg_speech);
        fl.addView(SBView,new FrameLayout.LayoutParams(500,140));

        facilityView = new ImageView[4];
        for(int i=0;i<4;i++)facilityView[i]=new ImageView(pca.getApplicationContext());

        if(oi.isFacilityEntered(0))facilityView[0].setImageResource(R.drawable.icon_restaurant);
        else facilityView[0].setImageResource(R.drawable.icon_restaurant);
        if(oi.isFacilityEntered(0))facilityView[1].setImageResource(R.drawable.icon_cafe);
        else facilityView[1].setImageResource(R.drawable.icon_cafe);
        if(oi.isFacilityEntered(0))facilityView[2].setImageResource(R.drawable.icon_convenience);
        else facilityView[2].setImageResource(R.drawable.icon_convenience);
        if(oi.isFacilityEntered(0))facilityView[3].setImageResource(R.drawable.icon_restroom);
        else facilityView[3].setImageResource(R.drawable.icon_restroom);
        for(int i=0;i<4;i++)fl.addView(facilityView[i], new FrameLayout.LayoutParams(80,80));

        setPositionByOriention();
    }

    public void setPositionByOriention()
    {
        BuildingInfo pos= ot.getMakerPoint(oi,markerWidth,markerHeight);
        if(pos.alti>0)
        {

            float nx,ny;
            //Log.i("    name   "+oi.desc+"  ","발견됨");
            nx=oriView.getX();
            ny=oriView.getY();

            //if(nx-pos.)

            setVisible(true);
            setPosition((float) pos.lat, (float) pos.lon);
        }
        else
        {
            setVisible(false);
        }
    }

    public void setPosition(float x, float y)
    {
        oriView.setX(x);
        oriView.setY(y);

        infoText.setX(x);
        infoText.setY(y+500);

        SBView.setX(x);
        SBView.setY(y-120);

        for(int i=0;i<4;i++)
        {
            facilityView[i].setX(x+60+100*i);
            facilityView[i].setY(y-100);
        }

    }

    public void setVisible(boolean tf)
    {
        if(tf==true)
        {
            oriView.setVisibility(View.VISIBLE);
            infoText.setVisibility(View.VISIBLE);
            SBView.setVisibility(View.VISIBLE);
            for(int i=0;i<4;i++)facilityView[i].setVisibility(View.VISIBLE);
        }
        else
        {
            oriView.setVisibility(View.INVISIBLE);
            infoText.setVisibility(View.INVISIBLE);
            SBView.setVisibility(View.INVISIBLE);
            for(int i=0;i<4;i++)facilityView[i].setVisibility(View.INVISIBLE);
        }
    }

}
