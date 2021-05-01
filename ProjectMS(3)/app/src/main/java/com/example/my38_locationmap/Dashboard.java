package com.example.my38_locationmap;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.my38_locationmap.MainActivity.redirectActivity;

public class Dashboard extends AppCompatActivity {
    //Initialize variable



    private List<ReserveVO> ReserveVOList;
    private RecyclerView recyclerView;

    DrawerLayout drawerLayout;
    TextView tv_title, tv_price;

    ReserveVO reserveVO;

    String url = "http://3.143.192.36/api/resvPageAll?msm_no=2"; //회원번호가 2인 월권 주차장
    RequestQueue requestQueue;  // 서버와 통신할 통로




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_dashboard);

        drawerLayout = findViewById(R.id.drawer_layout);
        tv_title = findViewById(R.id.tv_title);
        tv_price = findViewById(R.id.tv_price);
        //메인으로부터 마커 값 받아오기
        recyclerView = findViewById(R.id.recyclerView3);
//        MarkerItem markerItem = (MarkerItem) getIntent().getSerializableExtra("markerItem");
//        System.out.print(markerItem);
//        Log.d("HI", "item2 === "+markerItem.toString());
//        //Assign variable
//




 //     tv_title.setText(markerItem.getMsp_location());
//      tv_price.setText(markerItem.getMsp_price());
//
        //리사이클러뷰의 레이아웃 매니저 설정
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);//, LinearLayoutManager.VERTICAL, false

        recyclerView.setLayoutManager(layoutManager);

        ReserveVOList = new ArrayList<>();
        ReserveVOList.add(new ReserveVO(reserveVO));

        requestQueue = Volley.newRequestQueue(this);
        final ReserveAdapter adapter = new ReserveAdapter(ReserveVOList,Dashboard.this);












        //결과를 JsonArray 받을 것이므로.. StringRequest가 아니라.. JsonArrayRequest를 이용할 것임
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            //volley 라이브러리의 GET방식은 버튼 누를때마다 새로운 갱신 데이터를 불러들이지 않음. 그래서 POST 방식 사용
            @Override
            public void onResponse(JSONArray response) {
                ReserveVOList.clear();
                adapter.notifyDataSetChanged();
                //System.out.println(response.length());
                try {

                    //for (int i = response.length(); i > 0; i--) {
                    for (int i = 0; i<response.length(); i++){
                        JSONObject jsonObject = response.getJSONObject(i);

                        //int no= Integer.parseInt(jsonObject.getString("no")); //no가 문자열이라서 바꿔야함.
                        int msr_no = jsonObject.getInt("msr_no");
                        int msp_no = jsonObject.getInt("msp_no");
                        int msr_num = jsonObject.getInt("msr_num");
                        String msr_date = jsonObject.getString("msr_date");
                        int msr_price = jsonObject.getInt("msr_price");
                        int msm_no = jsonObject.getInt("msm_no");
                        int msr_status = jsonObject.getInt("msr_status");
                        int msr_reserv = jsonObject.getInt("msr_reserv");
                        String msr_sdate = jsonObject.getString("msr_sdate");
                        String msr_edate = jsonObject.getString("msr_edate");
                        int msr_use = jsonObject.getInt("msr_use");
                        String msp_location = jsonObject.getString("msp_location");
                        Double msp_lat = jsonObject.getDouble("msp_lat");
                        Double msp_lon = jsonObject.getDouble("msp_lon");
                        int msp_type = jsonObject.getInt("msp_type");
                        String msm_name = jsonObject.getString("msm_name");
                        //System.out.println(msnb_subject);

                        ReserveVOList.add(new ReserveVO(msr_no,msp_no,msr_num,msr_date,msr_price,msm_no,msr_status,msr_reserv,msr_sdate,msr_edate,msr_use,msp_location,msp_lat,msp_lon,msp_type,msm_name));
                        System.out.println(ReserveVOList.toString());
                        adapter.notifyItemInserted(0);
                        adapter.notifyItemChanged(0);

//
                        recyclerView.setAdapter(adapter);
                        recyclerView.setHasFixedSize(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        //실제 요청 작업을 수행해주는 요청큐 객체 생성
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //요청큐에 요청 객체 생성
        requestQueue.add(jsonArrayRequest);
        //Toast.makeText(getApplicationContext(), "성공ㅋ", Toast.LENGTH_SHORT).show();


    }

    public void ClickMenu(View view){
        //Open drawer
        MainActivity.openDrawer(drawerLayout);
    }

    public void ClickLogo(android.view.View view){
        //Close drawer
        MainActivity.closeDrawer(drawerLayout);
    }

    public void ClickHome(android.view.View view){
        //Redirect activity to home
        redirectActivity(this,  MainActivity.class);

    }

   public void ClickDashboard(android.view.View view){
      //  MainActivity.openDrawer(drawerLayout);
        //MainActivity.redirectActivity(this,Dashboard.class);
        //Recreate activity
        recreate();
//        tv_title.setText(""+markerItem.getMsp_location());
//        tv_price.setText(""+markerItem.msp_price);
    }
    public void ClickParkingCreate(android.view.View view){
        //Redirect activity to about us
        redirectActivity(this, ParkingCreate.class);
    }
    public void ClickParkingAddActivity(View view){
        //Redirect activity to about us
        redirectActivity(this, ParkingAddActivity.class);

    }
    public void ClickBoardActivity(View view){
      //  redirectActivity(this, boardActivity.class);
        redirectActivity(this, boardActivity.class);
        //sendRequest();
    }
    public void ClickLogout(android.view.View view){
        //Close app
        MainActivity.logout(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        //Close drawer
        MainActivity.closeDrawer(drawerLayout);

    }








}