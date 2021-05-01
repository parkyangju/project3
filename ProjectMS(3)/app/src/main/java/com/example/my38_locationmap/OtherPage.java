package com.example.my38_locationmap;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OtherPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.calendar_layout);
        setContentView(R.layout.activity_other);
        TextView t1,t2,t3,t4,t5,t6,t7;
        t1=findViewById(R.id.textView2);
        t2=findViewById(R.id.textView3);
        t3=findViewById(R.id.textView4);
        t4=findViewById(R.id.textView5);
        t5=findViewById(R.id.textView6);
        t6=findViewById(R.id.textView7);
        t7=findViewById(R.id.textView8);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String name = sp.getString("name","");
        String id = sp.getString("id","");
        String pass =sp.getString("pass","");
        String pass2 =sp.getString("pass2","");
        String phone =sp.getString("phone","");
        String email = sp.getString("email","");
        String car = sp.getString("car", "");

        t1.setText(name);
        t2.setText(email);
        t3.setText(id);
        t4.setText(pass);
        t5.setText(pass2);
        t6.setText(phone);
        t7.setText(car);


    }
}
