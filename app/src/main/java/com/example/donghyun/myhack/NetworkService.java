package com.example.donghyun.myhack;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by DH on 2017-01-15.
 */

public interface NetworkService {
    @GET("/index.php/myserver/getAllOries/{lat}/{lon}")
    Call<List<OriInfo>> getAllOries(@Path("lat") double lat, @Path("lon") double lon);

    @GET("/index.php/myserver/getOriInfo/{ID}")
    Call<OriInfo> getOriInfo(@Path("ID") int ID);
}
