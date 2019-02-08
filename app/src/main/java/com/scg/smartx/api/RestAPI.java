package com.scg.smartx.api;

import com.scg.smartx.model.JobAdd;
import com.scg.smartx.model.UserLogin;
import com.scg.smartx.model.UserShow;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestAPI {

    // อ่านข้อมูลจากตาราง User
    @GET("user")
    Call<List<UserShow>> getUserShow();

    // ส่งข้อมูลไปตรวจการล็อกอินผ่าน API
    @FormUrlEncoded
    @POST("user/login")
    Call<UserLogin> checkLogin(
            @Field("username") String username,
            @Field("password") String password
    );

    // ส่งข้อมูล Job ไปบันทึกในตาราง job ผ่าน API
    @FormUrlEncoded
    @POST("job")
    Call<JobAdd> jobAdd(
            @Field("eqnum") String eqnum,
            @Field("description") String description,
            @Field("status") String status,
            @Field("picture1") String picture1,
            @Field("gps_lat") String gps_lat,
            @Field("gps_long") String gps_long,
            @Field("light") String light,
            @Field("userid") String userid
    );

}
