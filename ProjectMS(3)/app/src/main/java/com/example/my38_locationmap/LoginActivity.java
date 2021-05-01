package com.example.my38_locationmap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class LoginActivity extends AppCompatActivity {

    TextView signup;
    EditText edt_name, edt_pass, edt_id;
    Button btn_login;

    RequestQueue requestQueue;
    StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.calendar_layout);
        setContentView(R.layout.activity_login);

        signup = findViewById(R.id.tv_signup);

        edt_name = findViewById(R.id.edt_name);
        edt_pass = findViewById(R.id.edt_pass);

        btn_login = findViewById(R.id.btn_login);

        String url = "http://3.143.192.36/api/loginOk";
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //서버에서 돌려준 응답을 처리

                Log.d("LoginActivity", "response = "+response);
                if (!response.equals(true)) {
                    Toast.makeText(getApplicationContext(), "로그인을 성공했습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("msm_id", edt_name.getText().toString());
                    startActivity(intent);

                } else {

                    Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> temp = new HashMap<>();
                temp.put("msm_id", edt_name.getText().toString());
                temp.put("msm_password", edt_pass.getText().toString());
                return temp;
            }
        };

        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                requestQueue.add(stringRequest);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });
    }
}


    /*private void checkCrededentials() {

        //유효한 아이디 확인 및 검증 안될시 에러 메시지 띄움.

        String id = edt_name.getText().toString();
        String pass = edt_pass.getText().toString();


        if (pass.isEmpty()) {
            showError(edt_pass, "비밀번호를 입력해주세요.");

        } else if (id.isEmpty()) {
            showError(edt_name, "아이디를 입력해주세요");

        } else {
            Toast.makeText(this, "Call Login Method", Toast.LENGTH_SHORT).show();

        }

    }



    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }*/



