package com.example.donghyun.myhack;

/**
 * Created by DongHyun on 2016-12-21.
 */

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

// 건물 정보를 보여줌
// 건물 이름,이미지,전화번호,편의시설,기타 정보를 현재는 그냥 넣어놓은 상황 -> 서버에서 받아와야함
public class InfoActivity extends AppCompatActivity {

    int oriID;
    OriInfo oi;

    ImageView buildingImgV;
    TextView buildingNameTextV;

    ListView listview ;
    ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        oriID = getIntent().getIntExtra("ID",0);

        /*// 건물 이름
        building_Name = "광개토관";
        // 건물 정보
        building_Contents = "경영학과가 주로 사용하는 건물이며 강의실 뿐 아니라 다양한 편의시설을 갖추고 있다.\n" +
                "5층에는 경영학과 학생들이 운영하는 카페가 있다.\n\n"+
                "지하 3,4층 : 주차장\n"+
                "지하 2층 : 컨벤션 센터\n"+
                "지하 1층 : 중소회의실, 전시장, 카페, 편의점\n" +
                "지상 1층 ~ 13층 : 강의실 및 연구실\n"+
                "지상 14층 : 외국인 학생 기숙사\n"+
                "지상 15층 : 식당(찬), 소극장";
        //건물 전화번호
        String building_Phone = "02) 3408-1234";
        //건물 편의시설
        String building_Facility = "식당, 카페, 매점, 화장실";
*/
        // activity_info의 건물 이미지,이름,정보 내용을 설정
        buildingImgV = (ImageView)findViewById(R.id.buildingImg);
        buildingNameTextV = (TextView)findViewById(R.id.buildingName);

        //buildingImgV.setBackgroundResource(building_Img);


        adapter = new ListViewAdapter() ;
        listview = (ListView) findViewById(R.id.buildingInfo);
        listview.setAdapter(adapter);
        listview.setBackgroundColor(Color.WHITE);

        Glide.with(this).load("http://35.166.255.30/static/img/"+oriID+"_info.jpg").placeholder(R.drawable.loading).into(buildingImgV);
        /*Glide.with(this)
                .load("http://35.166.255.30/static/img/"+oriID+"_info.jpg")
                .asGif()
                .placeholder(R.drawable.loading)
                .into(new GlideDrawableImageViewTarget(buildingImgV));
*/


        setdata();
    }

    public void setdata(){
        Call<OriInfo> DetailCall = ApplicationController.getInstance().getNetworkService().getOriInfo(oriID);

        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();

        DetailCall.enqueue(new Callback<OriInfo>() {
            @Override
            public void onResponse(Response<OriInfo> response, Retrofit retrofit) {
                if(mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                if (response.isSuccess()) {
                    oi = response.body();

                    buildingNameTextV.setText(oi.name);

                    Log.i("TEST",oi.info);

                    Log.i("phone",oi.tel);
                    Log.i("fac",oi.facility+"");
                    Log.i("info",oi.info);

                    adapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.phone), oi.tel) ;
                    adapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.facility), oi.facility+"");
                    adapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.info), oi.info);
                    adapter.notifyDataSetChanged();

                } else {
                    int statusCode = response.code();
                    Log.i("MyTag", "에러 상태 코드 : " + statusCode);
                }
            }
            @Override
            public void onFailure(Throwable t) {
                if(mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                //Log.i("MyTag", t.getMessage());
            }
        });
    }
}