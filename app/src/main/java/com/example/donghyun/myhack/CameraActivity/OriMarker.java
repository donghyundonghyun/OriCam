package com.example.donghyun.myhack.CameraActivity;

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
        //infoText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        infoText.setText(oi.name);
        //infoText.getLayoutParams().width=500;a

    }

    public void setPositionByOriention()
    {
        BuildingInfo pos= ot.getMakerPoint(oi,markerWidth,markerHeight);
        if(pos.alti>0)
        {
            oriView.setVisibility(View.VISIBLE);
            oriView.setX((float) pos.lat);
            oriView.setY((float) pos.lon);
        }
        else
        {
            oriView.setVisibility(View.INVISIBLE);
        }
    }
}
