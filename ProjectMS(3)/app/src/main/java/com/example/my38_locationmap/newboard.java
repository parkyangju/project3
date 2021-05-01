package com.example.my38_locationmap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class newboard extends AppCompatActivity {
    RequestQueue requestQueue;  // 서버와 통신할 통로
    StringRequest stringRequest;    // 내가 전송할 데이터


    private EditText edt_msm_no, edt_msnb_subject, edt_msnb_content;
    private ImageButton btn_add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.calendar_layout);
        setContentView(R.layout.activity_newboard);

        edt_msm_no = findViewById(R.id.msm_no);
        edt_msnb_subject = findViewById(R.id.msnb_subject);
        edt_msnb_content = findViewById(R.id.msnb_content);
        btn_add = findViewById(R.id.btn_add);

        String url ="http://3.143.192.36/api/noticeInsert";
        requestQueue = Volley.newRequestQueue(this);

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // 서버에서 돌려준 응답을 처리
                if (response.equals("1")){
                    finish();
                }else {
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error.toString(); 하면 에러 찍힘
            }
        }){
            // StringRequest 객체 범위

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // 전송할 데이터 Key, Value 로 셋팅하기
                Map<String, String> temp = new HashMap<>();
                temp.put("msm_no","1");
                temp.put("msnb_subject",edt_msnb_subject.getText().toString());    // put - 인덱스따라 추가가아니라 집어넣는느낌
                temp.put("msnb_content",edt_msnb_content.getText().toString());
                return temp;
            }
        };


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(newboard.this,boardActivity.class);
                startActivity(intent);

                requestQueue.add(stringRequest);

            }
        });
    }

}


