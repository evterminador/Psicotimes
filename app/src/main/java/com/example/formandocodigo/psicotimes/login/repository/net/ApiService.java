package com.example.formandocodigo.psicotimes.login.repository.net;

import com.example.formandocodigo.psicotimes.login.repository.net.entity.RegisterResponse;

import java.util.Date;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by FormandoCodigo on 01/12/2017.
 */

public interface ApiService {

    @POST("register")
    @FormUrlEncoded
    Call<RegisterResponse> register(@Field("name") String name, @Field("email") String email, @Field("password") String password);

    @POST("post/profile")
    @FormUrlEncoded
    Call<RegisterResponse> profile(@Field("email") String email, @Field("birthDate") Date birthDate,
                                   @Field("sex") String sex, @Field("occupation") String occupation,
                                   @Field("state") Boolean isWorking, @Field("useTime") Integer timeUse);
}