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
import java.util.List;
import java.util.Map;

public class ContentActivity extends AppCompatActivity {

    RequestQueue requestQueue;  // 서버와 통신할 통로
    StringRequest stringRequest;    // 내가 전송할 데이터

    private EditText edt_msnb_no,edt_msm_no, edt_msnb_subject, edt_msnb_content;
    private ImageButton btn_modify;
    private List<BoardVO> boardVOList;
    String url = "http://3.143.192.36/api/noticeUpdate";
    boardAdapter adapter = new boardAdapter(boardVOList);


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.calendar_layout);
            setContentView(R.layout.activity_content);


            edt_msnb_no = findViewById(R.id.msnb_no);
            edt_msnb_subject = findViewById(R.id.msnb_subject);
            edt_msnb_content = findViewById(R.id.msnb_content);
            edt_msm_no = findViewById(R.id.msm_no);

            btn_modify = findViewById(R.id.btn_modify);


        Intent intent = getIntent();

        edt_msnb_no.setText(intent.getStringExtra("msnb_no"));
        edt_msnb_subject.setText(intent.getStringExtra("subjectTxt"));
        edt_msnb_content.setText(intent.getStringExtra("contentTxt"));
        edt_msm_no.setText(intent.getStringExtra("msm_no"));


        System.out.println(intent.getStringExtra("subjectTxt"));


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

                Map<String, String> temp = new HashMap<String, String>();

                temp.put("msnb_no",edt_msnb_no.getText().toString());
                temp.put("msnb_subject",edt_msnb_subject.getText().toString());    // put - 인덱스따라 추가가아니라 집어넣는느낌
                temp.put("msnb_content",edt_msnb_content.getText().toString());
                temp.put("msm_no","1");

                return temp;


            }
        };


            btn_modify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(com.example.my38_locationmap.ContentActivity.this, boardActivity.class);

                    intent.putExtra("msnb_no", String.valueOf(edt_msnb_no));
                    intent.putExtra("msnb_subject", String.valueOf(edt_msnb_subject));
                    intent.putExtra("msnb_content", String.valueOf(edt_msnb_content));
                    intent.putExtra("msm_no", String.valueOf(edt_msm_no));

                    requestQueue.add(stringRequest);
                    adapter.notifyDataSetChanged();

                    startActivity(intent);



                }
            });

        }
}