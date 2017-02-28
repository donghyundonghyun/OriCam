package com.example.donghyun.myhack.CameraActivity;

import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.donghyun.myhack.BuildingInfo;
import com.example.donghyun.myhack.OriInfo;
import com.example.donghyun.myhack.R;

/**
 * Created by Ahn on 2017-01-23.
 */

public class OriMarker {

    FrameLayout fl;
    CameraActivity ca;
    OriInfo oi;
    Orientation ot;
    ImageView oriView;
    ImageView facilityView[];
    ImageView SBView;
    TextView infoText;

    public int markerWidth, markerHeight;
    private String urlPostfix = "http://35.166.255.30/static/img/";
    private String fileName;

    public OriMarker(CameraActivity pca, OriInfo poi, Orientation pot)
    {
        fl = (FrameLayout) pca.findViewById(R.id.activity_main);
        ca =pca;
        oi= poi;
        ot= pot;

        //ca.isDestroyed()

        oriView= new ImageView(pca.getApplicationContext());

        updateFileName(oi);
        Glide.with(ca).load(urlPostfix+fileName).into(oriView);

        oriView.setTag(poi);
        oriView.setOnClickListener(OriMarkerListener.getInstance());
        fl.addView(oriView, new FrameLayout.LayoutParams(500,500));
        //markerHeight = oriView.getLayoutParams().height = 500;
        //markerWidth = oriView.getLayoutParams().width = 500;

        infoText= new TextView(pca.getApplicationContext());
        infoText.setBackgroundResource(R.drawable.name_back);
        fl.addView(infoText, new FrameLayout.LayoutParams(500,70));
        infoText.setGravity(Gravity.CENTER);
        //infoText.setBackground();
        setInfoTextByNearValue(oi);
        //infoText.setBackgroundColor(Color.BLACK);
        //infoText.getLayoutParams().width=500;a

        SBView = new ImageView(pca.getApplicationContext());
        SBView.setVisibility(View.INVISIBLE);
        SBView.setScaleType(ImageView.ScaleType.FIT_XY);
        SBView.setImageResource(R.drawable.bg_speech);
        fl.addView(SBView,new FrameLayout.LayoutParams(500,140));

        facilityView = new ImageView[4];
        for(int i=0;i<4;i++)facilityView[i]=new ImageView(pca.getApplicationContext());
        for(int i=0;i<4;i++)facilityView[i].setVisibility(View.INVISIBLE);
        if(oi.isFacilityEntered(0))facilityView[0].setImageResource(R.drawable.icon_restaurant2);
        else facilityView[0].setImageResource(R.drawable.icon_restaurant);
        if(oi.isFacilityEntered(1))facilityView[1].setImageResource(R.drawable.icon_cafe2);
        else facilityView[1].setImageResource(R.drawable.icon_cafe);
        if(oi.isFacilityEntered(2))facilityView[2].setImageResource(R.drawable.icon_convenience2);
        else facilityView[2].setImageResource(R.drawable.icon_convenience);
        if(oi.isFacilityEntered(3))facilityView[3].setImageResource(R.drawable.icon_restroom2);
        else facilityView[3].setImageResource(R.drawable.icon_restroom);
        for(int i=0;i<4;i++)fl.addView(facilityView[i], new FrameLayout.LayoutParams(80,80));

        if(oi.near==1)
        {
            SBView.setVisibility(View.VISIBLE);
            for(int i=0;i<4;i++)facilityView[i].setVisibility(View.VISIBLE);
        }

        //setPositionByOriention();
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

    public void setVisibleFacilityView(boolean tf)
    {
        if(tf==true)
        {
            SBView.setVisibility(View.VISIBLE);
            for(int i=0;i<4;i++)facilityView[i].setVisibility(View.VISIBLE);
        }
        else
        {
            SBView.setVisibility(View.INVISIBLE);
            for(int i=0;i<4;i++)facilityView[i].setVisibility(View.INVISIBLE);
        }

    }


    public void setVisible(boolean tf)
    {
        if(tf==true)
        {
            oriView.setVisibility(View.VISIBLE);
            infoText.setVisibility(View.VISIBLE);
            if(oi.near==1)setVisibleFacilityView(true);
            else setVisibleFacilityView(false);
        }
        else
        {
            oriView.setVisibility(View.INVISIBLE);
            infoText.setVisibility(View.INVISIBLE);
            SBView.setVisibility(View.INVISIBLE);
            for(int i=0;i<4;i++)facilityView[i].setVisibility(View.INVISIBLE);
        }
    }

    public void updateOriInfo(OriInfo poi)
    {
        oi= poi;
        setInfoTextByNearValue(oi);
    }

    public void updateFileName(OriInfo poi)
    {
        fileName = oi.ID+"_";
        if(oi.near==2)fileName=fileName+"sil.png";
        else if (oi.near==1)fileName=fileName+"real.png";
    }

    public void setInfoTextByNearValue(OriInfo poi)
    {
        if(poi.near==1)infoText.setText(oi.name+": "+(int)oi.distance+"m");
        else infoText.setText("???"+": "+(int)oi.distance+"m");
    }

    public void bringChildToFront()
    {
        oriView.bringToFront();
        SBView.bringToFront();
        for(int i=0;i<4;i++) facilityView[i].bringToFront();
        infoText.bringToFront();
    }

}
