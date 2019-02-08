package com.scg.smartx;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.scg.smartx.api.RestAPI;
import com.scg.smartx.api.RetroServer;
import com.scg.smartx.model.JobAdd;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActionActivity extends AppCompatActivity {

    Toolbar toolbar;
    Spinner spinner;
    Button btnSubmit , btnTakePicture, btnChooseGallery;
    EditText editEqnum , editDescription;
    ImageView imgPreview;

    // ตัวแปรไว้เก็บ status ที่ผู้ใช้เลือก
    String status_spinner;
    SharedPreferences getpref;

    String[] spinItem = {
            "Normal",
            "Abnormal",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);

        // ทดสอบดึง UserID จากตัวแปร getSharedPreferences
        getpref = getSharedPreferences("pref_login", Context.MODE_PRIVATE);
        /*Toast.makeText(
                ActionActivity.this,
                "User ID = "+getpref.getString("pref_userid",null),
                Toast.LENGTH_LONG).show();
        */

        // จัดการ Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("เพิ่มงานใหม่");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner = findViewById(R.id.spinStatus);
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.
                R.layout.simple_spinner_dropdown_item ,spinItem);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int sid=spinner.getSelectedItemPosition();
                //Toast.makeText(getBaseContext(), "You have selected : " + spinItem[sid],Toast.LENGTH_SHORT).show();
                status_spinner = spinItem[sid];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // ============================================================================
        // ส่วนของการ FindView
        // ============================================================================
        btnSubmit = findViewById(R.id.btnSubmit);
        btnTakePicture = findViewById(R.id.btnTakePicture);
        btnChooseGallery = findViewById(R.id.btnChooseGallery);
        editEqnum = findViewById(R.id.editEqnum);
        editDescription = findViewById(R.id.editDescription);
        imgPreview = findViewById(R.id.imgPreview);

        // ============================================================================
        // ส่วนของการเขียนกดปุ่มถ่ายรูป
        // ============================================================================
        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                // ตรวจการอนุญาติการใช้งานกล้องจากผู้ใช้
                if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[] {Manifest.permission.CAMERA},111);
                }else{
                    // การ Intent เพื่อกล้องถ่ายภาพ
                    Intent camera = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(camera,0);
                }
            }
        });


        // ============================================================================
        // จบส่วนของการกดปุ่มถ่ายรูป
        // ============================================================================

        // ============================================================================
        // ส่วนของการเขียนกดปุ่มเลือกภาพจาก Gallery
        // ============================================================================
        btnChooseGallery.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                // ตรวจการอนุญาติการใช้งานกล้องจากผู้ใช้
                if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},222);
                }else{
                    // การ Intent เพื่อกล้องถ่ายภาพ
                    Intent camera = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(camera,1);
                }
            }
        });


        // ============================================================================
        // ส่วนของการเขียน Event บันทึกข้อมูลผ่าน API
        // ============================================================================
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ค่าไปทาง RestAPI
                RestAPI api = RetroServer.getClient().create(RestAPI.class);
                Call<JobAdd> jobAdd = api.jobAdd(
                        editEqnum.getText().toString(),
                        editDescription.getText().toString(),
                        status_spinner,
                        "job1.jpg",
                        "99.983256",
                        "10.345634",
                        "20.20",
                        getpref.getString("pref_userid",null)
                );

                jobAdd.enqueue(new Callback<JobAdd>() {
                    @Override
                    public void onResponse(Call<JobAdd> call, Response<JobAdd> response) {
                        Toast.makeText(
                                ActionActivity.this,
                                "Status "+response.body().getStatus(),
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<JobAdd> call, Throwable t) {

                    }
                });

            }
        });

    } // onCreate


    // ============================================================================
    // ส่วนของการเขียนรับข้อมูลรูปภาพหลังจากถ่ายภาพหรือเลือกจาก Gallery มาแล้ว
    // ============================================================================

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // ตรวจว่าผู้ใช้เลือกถ่ายรูปหรือ gallery
        if(requestCode == 0 && resultCode == Activity.RESULT_OK){
            Bitmap image = (Bitmap) data.getExtras().get("data");
            imgPreview.setImageBitmap(image);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
