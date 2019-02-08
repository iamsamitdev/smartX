package com.scg.smartx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.scg.smartx.api.RestAPI;
import com.scg.smartx.api.RetroServer;
import com.scg.smartx.model.UserShow;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    Button btnMore, btnAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnMore = findViewById(R.id.btnMore);
        btnAction = findViewById(R.id.btnAction);
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, MoreActivity.class);
                startActivity(intent);
            }
        });

        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, ActionActivity.class);
                startActivity(intent);
            }
        });

        // ทดสอบอ่านไฟล์ API user ออกมาแสดง
        RestAPI api = RetroServer.getClient().create(RestAPI.class);
        Call<List<UserShow>> getuser = api.getUserShow();
        getuser.enqueue(new Callback<List<UserShow>>() {
            @Override
            public void onResponse(Call<List<UserShow>> call, Response<List<UserShow>> response) {
                Log.d("Data = ", new Gson().toJson(response.body()));
                //Log.d("Data = ", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
            }

            @Override
            public void onFailure(Call<List<UserShow>> call, Throwable t) {

            }
        });


    }
}
