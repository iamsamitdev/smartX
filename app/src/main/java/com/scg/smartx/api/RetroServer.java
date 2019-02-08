package com.scg.smartx.api;
import com.scg.smartx.utils.BaseUrlApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroServer {
    private static Retrofit retrofit;

    public static Retrofit getClient(){

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(getBaseUrlApi(BaseUrlApi.baseURLAPI))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            //retrofit = new Retrofit.Builder().baseUrl("http://172.16.1.239/smartxapi/public/api/").addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;
    }

    private static String getBaseUrlApi(String base_url) {
        return base_url;
    }
}
