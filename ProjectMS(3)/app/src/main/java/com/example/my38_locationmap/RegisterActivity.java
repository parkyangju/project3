package com.example.my38_locationmap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class
RegisterActivity extends AppCompatActivity {
    TextView tv_already;
    private EditText edt_name, edt_id, edt_pass, edt_pass2, edt_phone, edt_email, edt_car;
    Button btn_regi;
    SharedPreferences sp;

    RequestQueue requestQueue;
    StringRequest stringRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.calendar_layout);
        setContentView(R.layout.activity_register);

        tv_already = findViewById(R.id.tv_already);
        edt_name = findViewById(R.id.edt_name);
        edt_id = findViewById(R.id.edt_id);
        edt_pass = findViewById(R.id.edt_pass);
        edt_pass2 = findViewById(R.id.edt_pass2); //비밀번호 확인
        edt_phone = findViewById(R.id.edt_phone);
        edt_email = findViewById(R.id.edt_email);
        edt_car = findViewById(R.id.edt_car);

        btn_regi = findViewById(R.id.btn_regi);



        sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String url = "http://3.143.192.36/api/memberInsert";
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("1")) {

                    Toast.makeText(getApplicationContext(), "회원가입 완료 되었습니다", Toast.LENGTH_SHORT).show();
                    finish();


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

    }){
            //StringRequest 객체 범위위
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //전송할 데이터 key, value로 세팅하기
                //msm_id=edt_id&msm_password=edt_pass&msm_name=edt_name&msm_phone=edt_phone&msm_email=edt_email&msm_car=edt_car
                Map<String, String> temp = new HashMap<>();


                temp.put("msm_name", edt_name.getText().toString());
                temp.put("msm_id", edt_id.getText().toString());
                temp.put("msm_password", edt_pass.getText().toString());
                temp.put("msm_phone", edt_phone.getText().toString());
                temp.put("msm_email", edt_email.getText().toString());
                temp.put("msm_car", edt_car.getText().toString());

                if( edt_name.getText().toString().length() ==0||edt_id.getText().toString().length() ==0
                        ||edt_pass.getText().toString().length() ==0|| edt_phone.getText().toString().length() ==0
                        || edt_email.getText().toString().length() ==0||edt_car.getText().toString().length() ==0){

                }

                return temp;
            }

        };

        btn_regi.setOnClickListener(new View.OnClickListener() {//세션 유지
            @Override
            public void onClick(View v) {


                if(checkCrededentials()) {

                    String msm_name = edt_name.getText().toString();
                    String msm_email = edt_email.getText().toString();
                    String msm_id = edt_id.getText().toString();
                    String msm_password = edt_pass.getText().toString();
                    String msm_pass2 = edt_pass2.getText().toString();
                    String msm_phone = edt_phone.getText().toString();
                    String msm_car = edt_car.getText().toString();

                    Toast.makeText(RegisterActivity.this, "정보가 저장되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);


                    startActivity(intent);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("id", msm_id);
                    editor.putString("pass", msm_password);
                    editor.putString("name", msm_name);
                    editor.putString("email", msm_email);
                    editor.putString("pass2", msm_pass2);
                    editor.putString("phone", msm_phone);
                    editor.putString("car", msm_car);
                    editor.commit();


                    requestQueue.add(stringRequest);


                }

            }
        });

        tv_already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });

    }

    private boolean checkCrededentials() {
        //유효한 아이디 확인 및 검증 안될시 에러 메시지 띄움.
        String name = edt_name.getText().toString();
        String email = edt_email.getText().toString();
        String id = edt_id.getText().toString();
        String pass = edt_pass.getText().toString();
        String pass2 = edt_pass2.getText().toString();
        String phone = edt_phone.getText().toString();
        String car = edt_car.getText().toString();

        if (name.isEmpty() || name.length() < 7) {
            showError(edt_name, "유효한 이름이 아닙니다.");
            return false;

        } else if (email.isEmpty() || !email.contains("@")) {
            showError(edt_email, "이메일이 유효하지 않습니다.");
            return false;
        } else if (pass2.isEmpty() || !pass2.equals(pass)) {
            showError(edt_pass2, "비밀번호가 일치하지 않습니다.");
            return false;
        } else {
            Toast.makeText(this, "Call Registration Method", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

}

