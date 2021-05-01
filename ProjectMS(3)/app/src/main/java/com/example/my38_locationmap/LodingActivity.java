package com.example.my38_locationmap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LodingActivity extends AppCompatActivity {
    EditText et_save;
    String shared = "file";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.calendar_layout);
        setContentView(R.layout.activity_loding);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    Intent intent =new Intent(LodingActivity.this,LoginActivity.class);
                    startActivity(intent);
            }
        },1000);


    }
}