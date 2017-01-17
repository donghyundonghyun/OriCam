package com.example.donghyun.myhack.CameraActivity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.donghyun.myhack.InfoActivity.InfoActivity;
import com.example.donghyun.myhack.R;

/**
 * Created by Ahn on 2017-01-17.
 */

public class CameraActivityView {
    ImageView[] img = new ImageView[3];
    float[] imgWidth = new float[3];
    float[] imgHeight = new float[3];

    CameraActivity cameraActivity;

    CameraActivityView(CameraActivity ma)
    {
        cameraActivity=ma;

        img[2] = (ImageView)ma.findViewById(R.id.duck2); //광개토
        img[1] = (ImageView)ma.findViewById(R.id.duck1); //충무관
        img[0] = (ImageView)ma.findViewById(R.id.duck3); //학생

        for(int i=0;i<3;i++) {
            final int finali = i;

            img[i].getLayoutParams().width = 500;
            img[i].getLayoutParams().height = 500;

            imgWidth[i]=(float) img[i].getLayoutParams().width;
            imgHeight[i]=(float) img[i].getLayoutParams().height;

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

            //Log.i("값 비교 -----> " + img.getX()+"",img.getY()+" : " + imgHeight + " : " + img.getHeight());

        }

    }

    void setImg(int idx, float x, float y)
    {
        img[idx].setVisibility(View.VISIBLE);
        img[idx].setX(x);
        img[idx].setY(y);
    }

    void setImgVisibility(int idx, boolean tf)
    {
        if(tf==false)img[idx].setVisibility(View.INVISIBLE);
        else img[idx].setVisibility(View.VISIBLE);
    }

    float getImgWidth(int idx)
    {
        return imgWidth[idx];
    }

    float getImgHeight(int idx)
    {
        return imgHeight[idx];
    }


}
