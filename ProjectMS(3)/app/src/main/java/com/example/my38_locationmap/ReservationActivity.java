package com.example.my38_locationmap;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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

public class ReservationActivity extends AppCompatActivity {

    MarkerItem item = null;
    AutoCompleteTextView autoCompleteTextView,autoCompleteTextView2,autoCompleteTextView3;
    Button btn_pay;
    String type;
    String time;
    String place = "1";
    RequestQueue requestQueue;  // 서버와 통신할 통로
    StringRequest stringRequest;    // 내가 전송할 데이터

    private final String TAG = getClass().getSimpleName();
//    private final String TAG = "ReservationActivity";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.calendar_layout);
        setContentView(R.layout.activity_reservation);

        //메인으로부터 마커 값 받아오기
        final MarkerItem markerItem = (MarkerItem) getIntent().getSerializableExtra("markerItem");
        String option_value;
        if(markerItem.getMsp_type()== 1) {
            option_value =  "월권";
            type = "1";
        } else {
            option_value =  "일권";
            type = "2";
        }
        //System.out.print(markerItem);
        Log.d(TAG, "item === "+markerItem.toString());

        autoCompleteTextView = findViewById(R.id.autoCompletText);


        final String [] option = {option_value};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.option_item,option);
        autoCompleteTextView.setText(arrayAdapter.getItem(0).toString(),false);

        autoCompleteTextView.setAdapter(arrayAdapter);




        autoCompleteTextView2 = findViewById(R.id.autoCompletText2);
        final String [] option2 = {"9시","10시","11시","12시","13시","14시","15시","16시","17시","18시"};
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this,R.layout.option_item,option2);
        autoCompleteTextView2.setText(arrayAdapter2.getItem(0).toString(),false);

        autoCompleteTextView2.setAdapter(arrayAdapter2);
        autoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                switch (position) {
                    case 0:
                        time = option2[position];
                        //tv_test.setText(type);
                        break;
                    case 1:
                        time = option2[position];
                        // tv_test.setText(type);
                        break;
                    case 2:
                        time = option2[position];
                        //tv_test.setText(type);
                        break;
                    case 3:
                        time = option2[position];
                        // tv_test.setText(type);
                        break;
                    case 4:
                        time = option2[position];
                        //tv_test.setText(type);
                        break;
                    case 5:
                        time = option2[position];
                        // tv_test.setText(type);
                        break;
                    case 6:
                        time = option2[position];
                        //tv_test.setText(type);
                        break;
                    case 7:
                        time = option2[position];
                        // tv_test.setText(type);
                        break;
                    case 8:
                        time = option2[position];
                        //tv_test.setText(type);
                        break;
                    case 9:
                        time = option2[position];
                        // tv_test.setText(type);
                        break;
                }
            }
        });

        autoCompleteTextView3 = findViewById(R.id.autoCompletText3);
        final String [] option3 = {"1번","2번","3번","4번","5번","6번","7번","8번","9번","10번"};
        ArrayAdapter arrayAdapter3 = new ArrayAdapter(this,R.layout.option_item,option3);
        autoCompleteTextView3.setText(arrayAdapter3.getItem(0).toString(),false);

        autoCompleteTextView3.setAdapter(arrayAdapter3);
        autoCompleteTextView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                switch (position) {
                    case 0:
                        place = option3[position];
                        //tv_test.setText(type);
                        break;
                    case 1:
                        place = option3[position];
                        // tv_test.setText(type);
                        break;
                    case 2:
                        place = option3[position];
                        //tv_test.setText(type);
                        break;
                    case 3:
                        place = option3[position];
                        // tv_test.setText(type);
                        break;
                    case 4:
                        place = option3[position];
                        //tv_test.setText(type);
                        break;
                    case 5:
                        place = option3[position];
                        // tv_test.setText(type);
                        break;
                    case 6:
                        place = option3[position];
                        //tv_test.setText(type);
                        break;
                    case 7:
                        place = option3[position];
                        // tv_test.setText(type);
                        break;
                    case 8:
                        place = option3[position];
                        //tv_test.setText(type);
                        break;
                    case 9:
                        place = option3[position];
                        // tv_test.setText(type);
                        break;
                }
            }
        });


        btn_pay = findViewById(R.id.btn_pay) ;

        btn_pay.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url ="http://3.143.192.36/api/popupPage";
                requestQueue = Volley.newRequestQueue(ReservationActivity.this);

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
//                        temp.put("msr_no","1");
//                        temp.put("msp_no","1");    // put - 인덱스따라 추가가아니라 집어넣는느낌
//
//                        temp.put("msr_num","4");   //temp.put("msr_num",place.toString());
//                        temp.put("msr_date","2021-04-20 11:58:55");
//                        temp.put("msr_price","3000");
//                        temp.put("msm_no","2");
//                        temp.put("msr_status","0");
//                        temp.put("msr_reserv","1");// temp.put("msr_reserv",type.toString());
//                        temp.put("msr_sdate","2021-04-20 11:58:55");
//                        temp.put("msr_edate","2021-04-20 11:58:55");
//                        temp.put("msr_use","1");
//                        temp.put("msp_location","광주광역시 송정동 훼미리파크아파트");//temp.put("msp_location",markerItem.getMsp_location());
//                        temp.put("msp_lat","35.15300900000000"); // temp.put("msp_lat",markerItem.getMsp_lat()+"");
//                        temp.put("msp_lon","126.79241400000000"); // temp.put("msp_lon",markerItem.getMsp_lon()+"");
//                        temp.put("msp_type","1"); // temp.put("msp_type",type.toString());
//                        temp.put("msm_name","test");
                        temp.put("msm_no","2"); // 회원 번호

                        temp.put("msp_no",markerItem.getMsp_no().toString()); // 주차장 번호 markerItem.getMsp_no().toString()

                        temp.put("msr_num",place.substring(0, place.length()-1)+"");                        //temp.put("msr_num",place.toString().substring(0, place.length()-1)+""); //자리번호
                        //temp.put("msr_num",place.substring(0, place.length()-1)); //

                        temp.put("msp_type",markerItem.getMsp_type()+"");  //월권인가 일권인가 //월권 일권 맞춰줘야함 markerItem.getMsp_type()+""


                        Log.d("test::::",place.toString().substring(0, place.length()-1));
                        return temp;



                    }
                };

                Intent intent = new Intent(getApplicationContext(),Dashboard.class);
                intent.putExtra("markerItem", markerItem);
                startActivity(intent);
                requestQueue.add(stringRequest);

//                tv_test.setText(type);
//                tv_time.setText(time);
//                tv_place.setText(place);
            }
        }) ;



    }
}