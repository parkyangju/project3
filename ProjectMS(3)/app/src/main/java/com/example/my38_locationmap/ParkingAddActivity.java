package com.example.my38_locationmap;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.my38_locationmap.MainActivity.redirectActivity;

public class ParkingAddActivity extends AppCompatActivity {

//    private List<ParkingVO> parkingVOList;
    DrawerLayout drawerLayout;
    RequestQueue requestQueue;  // 서버와 통신할 통로
    StringRequest stringRequest;    // 내가 전송할 데이터

    private TextView edt_address;
    private TextView edt_lat;
    private TextView edt_lon;
    private Button btn_next;

    String type = "1";
    String type_option;
    String num = "1";
    String date;
    String place;

    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;

//    ParkingVO parkingVO;

    AutoCompleteTextView autoCompleteTextView, autoCompleteTextView2, autoCompleteTextView3;
    private Button btn_add;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.calendar_layout);
        setContentView(R.layout.activity_parking_add);


        edt_address = (TextView)findViewById(R.id.edt_address);
        edt_lat = (TextView)findViewById(R.id.edt_lat);
        edt_lon = (TextView)findViewById(R.id.edt_lon);

        btn_next = (Button)findViewById(R.id.btn_next);

        drawerLayout = findViewById(R.id.drawer_layout);

        autoCompleteTextView = findViewById(R.id.autoCompletText);

        Intent intent = getIntent();
        final String data = intent.getStringExtra("data");


        if(data != null){
            Log.v("testes", data);
            edt_address.setText(data);

            List<Address> list = null;
            final Geocoder geocoder = new Geocoder(this);
            String str = edt_address.getText().toString();
            if(str != null) {
                try {
                    //getFromLocationName(읽을 이름, 읽을 개수)
                    list = geocoder.getFromLocationName( str,1); // 읽을 개수
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("test","입출력 오류 - 서버에서 주소변환시 에러발생");
                }

                if (list != null) {
                    if (list.size() == 0) {
                        //   edt_lat.setText("해당되는 주소 정보는 없습니다");
                    } else {
                        Log.v("위도 :::", String.valueOf(list.get(0).getLatitude()));
                        Log.v("경도 :::", String.valueOf(list.get(0).getLongitude()));
                        //list.get(0).getCountryName();  // 국가명
                        String a = list.get(0).getLatitude()+" "; //위도
                        String b = list.get(0).getLongitude()+" "; // 경도
                        edt_lat.setText(a);
                        edt_lon.setText(b);
                    }
                }
            }

        }

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParkingAddActivity.this, ParkingAddAddress.class);
                Log.v("testes", "aaaa");
                //startActivityForResult(intent, SEARCH_ADDRESS_ACTIVITY);
                startActivity(intent);
            }
        });

        final String[] option = {"월권", "일권"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.option_item, option);
        autoCompleteTextView.setText(arrayAdapter.getItem(0).toString(), false);
        autoCompleteTextView.setAdapter(arrayAdapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                switch (position) {
                    case 0:
                        type = option[position];
                        if (type.equals("월권")) {
                            type_option = "1";
                        }
                        break;
                    case 1:
                        type = option[position];
                        if (type.equals("일권")) {
                            type_option = "2";
                        }
                        break;
                }
            }
        });

        autoCompleteTextView2 = findViewById(R.id.autoCompletText2);
        final String[] option2 = {"09:00~18:00"};
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, R.layout.option_item, option2);
        autoCompleteTextView2.setText(arrayAdapter2.getItem(0).toString(), false);

        autoCompleteTextView2.setAdapter(arrayAdapter2);
        autoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                switch (position) {
                    case 0:
                        date = option2[position];
                        //tv_test.setText(type);
                        break;
                }
            }
        });

        autoCompleteTextView3 = findViewById(R.id.autoCompletText3);
        final String[] option3 = {"1", "2", "3", "4", "5"};
        ArrayAdapter arrayAdapter3 = new ArrayAdapter(this, R.layout.option_item, option3);
        autoCompleteTextView3.setText(arrayAdapter3.getItem(0).toString(), false);

        autoCompleteTextView3.setAdapter(arrayAdapter3);
        autoCompleteTextView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                switch (position) {
                    case 0:
                        num = option3[position];
                        //tv_test.setText(type);
                        break;
                    case 1:
                        num = option3[position];
                        // tv_test.setText(type);
                        break;
                    case 2:
                        num = option3[position];
                        //tv_test.setText(type);
                        break;
                    case 3:
                        num = option3[position];
                        // tv_test.setText(type);
                        break;
                    case 4:
                        num = option3[position];
                        //tv_test.setText(type);
                        break;
                }
            }
        });

        btn_add = findViewById(R.id.btn_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "http://3.143.192.36/api/parkingInsert?";
                //String url = "http://172.30.1.35:8081/smartcar/api/parkingInsert?";
                Log.v("url :: ", url);
                requestQueue = Volley.newRequestQueue(ParkingAddActivity.this);

                stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // 서버에서 돌려준 응답을 처리
                        if (response.equals("1")) {
                            finish();
                        } else {
                            //
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.toString(); //하면 에러 찍힘
                    }
                }) {

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

                        temp.put("msm_no", "2");
                        temp.put("msp_location", edt_address.getText()+""); //
                        temp.put("msp_lat",edt_lat.getText()+ ""); //
                        temp.put("msp_lon", edt_lon.getText()+ ""); //
                        temp.put("msp_num", num+"");
                        temp.put("msp_type", type+"");

//                        temp.put("msm_no", "2");
//                        temp.put("msp_location", "하하하 호호호");
//                        temp.put("msp_lat", "35.123");
//                        temp.put("msp_lon", "127.0112");
//                        temp.put("msp_num", "1");
//                        temp.put("msp_type", "1");

                        Log.d("parking insert ::::", edt_address.getText()+"");
                        Log.d("parking insert ::::", edt_address.getText()+"");
                        Log.d("parking insert ::::", edt_address.getText()+"");
                        return temp;
                    }
                };

                Intent intent = new Intent(getApplicationContext(), ParkingCreate.class);
                //intent.putExtra("ParkingVO", ParkingVO);
                startActivity(intent);
                requestQueue.add(stringRequest);
//
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        Log.v("request ::: ", String.valueOf(requestCode));
        switch(requestCode) {
            case SEARCH_ADDRESS_ACTIVITY :
                if(resultCode == RESULT_OK){
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        edt_address.setText(data);
                        List<Address> list = null;
                        final Geocoder geocoder = new Geocoder(this);
                        String str = edt_address.getText().toString();
                        if(str != null) {
                            try {
                                //getFromLocationName(읽을 이름, 읽을 개수)
                                list = geocoder.getFromLocationName( str,1); // 읽을 개수
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.e("test","입출력 오류 - 서버에서 주소변환시 에러발생");
                            }

                            if (list != null) {
                                if (list.size() == 0) {
                                 //   edt_lat.setText("해당되는 주소 정보는 없습니다");
                                } else {
                                    Log.v("위도 :::", String.valueOf(list.get(0).getLatitude()));
                                    Log.v("경도 :::", String.valueOf(list.get(0).getLongitude()));
                                    //list.get(0).getCountryName();  // 국가명
                                    String a = list.get(0).getLatitude()+" "; //위도
                                    String b = list.get(0).getLongitude()+" "; // 경도
                                    edt_lat.setText(a);
                                    edt_lon.setText(b);
                                }
                            }
                        }
                    }
                }
                break;
        }
    }


    public void ClickMenu(View view) {
        //Open drawer
        MainActivity.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view) {
        //Close drawer
        MainActivity.closeDrawer(drawerLayout);
    }

    public void ClickHome(View view) {
        //Redirect activity to home
        redirectActivity(this, MainActivity.class);
    }

    public void ClickDashboard(View view) {
        //Redirect activity to dashboard
        redirectActivity(this, Dashboard.class);
    }

    public void ClickParkingAddActivity(View view) {
        //Recreate activity
        redirectActivity(this, ParkingAddActivity.class);
    }

    public void ClickBoardActivity(View view){
        redirectActivity(this, boardActivity.class);
        //sendRequest();
    }

    public void ClickLogout(View view) {
        //Close app
        MainActivity.logout(this);

    }

    public void ClickBack(View view){
        redirectActivity(this, MainActivity.class);

    }

    @Override
    protected void onPause() {
        super.onPause();
        //Close drawer
        MainActivity.closeDrawer(drawerLayout);
    }
}

