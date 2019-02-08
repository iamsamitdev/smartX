package com.scg.smartx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MoreActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        // จัดการ Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ตั้งค่าเพิ่มเติม");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // เรียกใช้งานปุ่ม Button Logout
        Button btnLogout = findViewById(R.id.btnLogout);

        // เขียน Event ให้ปุ่ม Logout (Onclick)
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ส่งกลับไปหน้า Login (MainActivity)
                Intent intent = new Intent(MoreActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
