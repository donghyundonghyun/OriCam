package com.example.donghyun.myhack;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by DH on 2017-01-15.
 */

public interface NetworkService {
    @GET("/index.php/myserver/getOries")
    Call<List<OriInfo>> getOries();

    @GET("/index.php/myserver/getInfo")
    Call<OriInfo> getInfo(@Path("ID") long ID);
}
