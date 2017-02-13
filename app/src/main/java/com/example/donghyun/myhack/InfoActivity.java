package com.example.donghyun.myhack;

/**
 * Created by DongHyun on 2016-12-21.
 */

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

// 건물 정보를 보여줌
// 건물 이름,이미지,전화번호,편의시설,기타 정보를 현재는 그냥 넣어놓은 상황 -> 서버에서 받아와야함
public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        //Intent intent = getIntent();
        //int index = intent.getIntExtra("index",1);

        // 건물 이미지
        int building_Img = R.drawable.gwang1;
        // 건물 이름
        String building_Name = "광개토관";
        // 건물 정보
        String building_Contents = "경영학과가 주로 사용하는 건물이며 강의실 뿐 아니라 다양한 편의시설을 갖추고 있다.\n" +
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

        // activity_info의 건물 이미지,이름,정보 내용을 설정
        ImageView img = (ImageView)findViewById(R.id.buildingImg);
        TextView name = (TextView)findViewById(R.id.buildingName);

        img.setBackgroundResource(building_Img);
        name.setText(building_Name);

        ListView listview ;
        ListViewAdapter adapter;

        adapter = new ListViewAdapter() ;
        listview = (ListView) findViewById(R.id.buildingInfo);
        listview.setAdapter(adapter);
        listview.setBackgroundColor(Color.WHITE);

//        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.phone),building_Phone) ;
//        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.facility),building_Facility);
//        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.info),building_Contents);

    }
}