package com.scg.smartx.api;

import com.scg.smartx.model.JobAdd;
import com.scg.smartx.model.UserLogin;
import com.scg.smartx.model.UserShow;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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
    @Multipart
//    @FormUrlEncoded
    @POST("job")
    Call<JobAdd> jobAdd(
            @Part("eqnum") RequestBody eqnum,
            @Part("description") RequestBody  description,
            @Part("status") RequestBody  status,
            @Part MultipartBody.Part file,
            @Part("file") RequestBody name,
            @Part("gps_lat") RequestBody  gps_lat,
            @Part("gps_long") RequestBody  gps_long,
            @Part("light") RequestBody  light,
            @Part("userid") RequestBody  userid
    );

}
