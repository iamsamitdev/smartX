package com.scg.smartx;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.scg.smartx.api.RestAPI;
import com.scg.smartx.api.RetroServer;
import com.scg.smartx.model.UserLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText editUsername;
    EditText editPassword;

    // กำหนดตัวแบบ SharedPreferences
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find View
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        Button btnLogin = findViewById(R.id.btnLogin);

        // Event Click Listener
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // ทดสอบเรียกใช้ API Login ที่สร้างไว้ใน RestAPI
                RestAPI api = RetroServer.getClient().create(RestAPI.class);
                Call<UserLogin> checkLogin = api.checkLogin(
                        editUsername.getText().toString(),
                        editPassword.getText().toString()
                );

                checkLogin.enqueue(new Callback<UserLogin>() {
                    @Override
                    public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {

                        if (response.body().getStatus().equals("success")) {

                            // สร้างตัวแปรประเภทที่จำค่าไว้ใช้ในหน้าอื่นได้
                            pref = getSharedPreferences("pref_login", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("pref_userid", response.body().getUserId());
                            editor.apply();

                            // พาไปหน้า Dashboard
                            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                            startActivity(intent);
                        } else {
                            // แจ้งเตือนว่าล็อกอินไม่ถูกต้อง
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("แจ้งเตือน");
                            builder.setMessage("ข้อมูลเข้าระบบไม่ถูกต้อง ลองใหม่");
                            builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                }
                            });
                            builder.create();
                            builder.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserLogin> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "ติดต่อเซิฟเวอร์ไม่ได้ :( ลองอีกครั้ง", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });


    }
}
