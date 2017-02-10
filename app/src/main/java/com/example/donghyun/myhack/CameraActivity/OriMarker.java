package com.example.donghyun.myhack.CameraActivity;

import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.donghyun.myhack.BuildingInfo;
import com.example.donghyun.myhack.Constants;
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
        fl.addView(oriView, new FrameLayout.LayoutParams(500,500));
        //markerHeight = oriView.getLayoutParams().height = 500;
        //markerWidth = oriView.getLayoutParams().width = 500;
        setPositionByOriention();


        infoText= new TextView(pca.getApplicationContext());
        fl.addView(infoText, new FrameLayout.LayoutParams(500,100));
        //infoText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        infoText.setText(oi.name);
        //infoText.getLayoutParams().width=500;

    }

    public void setPositionByOriention()
    {
        BuildingInfo pos= ot.getMakerPoint(oi,markerWidth,markerHeight);
        if(pos.alti>0)
        {
            float nx,ny;
            Log.i("    name   "+oi.desc+"  ","발견됨");
            nx=oriView.getX();
            ny=oriView.getY();

            //if(nx-pos.)
            oriView.setVisibility(View.VISIBLE);
            setPosition((float) pos.lat, (float) pos.lon);
        }
        else
        {
            oriView.setVisibility(View.INVISIBLE);
        }
    }

    public void setPosition(float x, float y)
    {
        oriView.setX(x);
        oriView.setY(y);

        infoText.setX(x);
        infoText.setY(y+500);

    }
}
