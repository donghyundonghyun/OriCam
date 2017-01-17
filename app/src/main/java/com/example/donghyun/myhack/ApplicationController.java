package com.example.donghyun.myhack;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by DH on 2017-01-15.
 */

public class ApplicationController extends Application {

    private static ApplicationController instance;
    public static ApplicationController getInstance(){return instance;}

    private String baseUrl;

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationController.instance = this;
    }


    private NetworkService networkService;
    public NetworkService getNetworkService() {return networkService;}

    public void buildNetworkService(String ip, int port) {
        synchronized (ApplicationController.class) {
            if (networkService == null) {
                baseUrl = String.format("http://%s:%d", ip, port);
                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                        .create();

                //서버에서 json 형식으로 데이터를 보내고 이를 파싱해서 받아오기 위함
                GsonConverterFactory factory = GsonConverterFactory.create(gson);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(factory) //GsonConverterFactory를 통해 JSON Array를 객체배열로 바꿔줌.
                        .build();//retrofit을 반환

                networkService = retrofit.create(NetworkService.class);
                // Retrofit.Builder()를 이용해 Retrofit 객체를 생성한 후 이를 이용해 networkService 정의
            }
        }
    }






}
