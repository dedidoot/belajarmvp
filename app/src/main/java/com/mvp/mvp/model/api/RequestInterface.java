package com.mvp.mvp.model.api;

import com.mvp.mvp.model.pojo.Rintik;
import com.mvp.mvp.model.pojo.User;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by TEAM on 12/4/2016.
 * That's it
 */

public interface RequestInterface {

    @GET("/waktu_hujan")
    Observable<Rintik> rintik();

    @GET("/activityprovinsi/{kata_kunci}/{waktu}/{token_provinsi}/{batas}")
    Observable<List<User>> getUserActivities(@Path("kata_kunci") String kata_kunci,
                                             @Path("waktu") String waktu,
                                             @Path("token_provinsi") String token_provinsi,
                                             @Path("batas") String batas);


}
