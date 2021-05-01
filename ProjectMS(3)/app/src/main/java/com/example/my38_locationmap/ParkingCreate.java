package com.example.my38_locationmap;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

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

public class ParkingCreate extends AppCompatActivity {
    DrawerLayout drawerLayout;
    private List<ParkingVO> parkingVOList;
    private RecyclerView recyclerView3;

    TextView msp_location, msp_date, msp_type, msp_num;

    ParkingVO parkingVO;

    String url = "http://3.143.192.36/api/parkingListSelect2";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.calendar_layout);
        setContentView(R.layout.activity_parking_create);

        recyclerView3 = findViewById(R.id.recyclerView3);

        msp_location = findViewById(R.id.tv_location);
        msp_date = findViewById(R.id.tv_date);
        msp_type = findViewById(R.id.tv_type);
        msp_num = findViewById(R.id.tv_num);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);//, LinearLayoutManager.VERTICAL, false
        recyclerView3.setLayoutManager(layoutManager);
        drawerLayout = findViewById(R.id.drawer_layout);

        parkingVOList = new ArrayList<>();
        parkingVOList.add(new ParkingVO(parkingVO));

        requestQueue = Volley.newRequestQueue(this);
        final ParkingAdapter adapter = new ParkingAdapter(parkingVOList);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                parkingVOList.clear();
                adapter.notifyDataSetChanged();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        //int no= Integer.parseInt(jsonObject.getString("no")); //no가 문자열이라서 바꿔야함.
                        String msp_no = jsonObject.getString("msp_no");
                        String msm_no = jsonObject.getString("msm_no");
                        String msp_location = jsonObject.getString("msp_location");
                        String msp_lat = jsonObject.getString("msp_lat");
                        String msp_lon = jsonObject.getString("msp_lon");
                        String msp_num = jsonObject.getString("msp_num");
                        String msp_type = jsonObject.getString("msp_type");
                        String msp_date = jsonObject.getString("msp_date");

                        parkingVOList.add(new ParkingVO(msp_no, msm_no, msp_location, msp_lat, msp_lon,
                                msp_num, msp_type, msp_date));
                        //System.out.println(markerItems.toString());
                        //adapter.notifyDataSetInvalidated();
                        adapter.notifyItemInserted(0);
                        adapter.notifyItemChanged(0);

                        recyclerView3.setAdapter(adapter);
                        recyclerView3.setHasFixedSize(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);
        Toast.makeText(getApplicationContext(), "성공!", Toast.LENGTH_SHORT).show();
    }

   public void ClickMenu(View view){
        //Open drawer
        MainActivity.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view){
        //Close drawer
        MainActivity.closeDrawer(drawerLayout);
    }
    public void ClickHome(View view){
        //Redirect activity to home
        MainActivity.redirectActivity(this, MainActivity.class);
    }


    public void ClickDashboard(View view){
        //Redirect activity to dashboard
        MainActivity.redirectActivity(this,Dashboard.class);

    }
    public void ClickParkingCreate(View view){
        //Recreate activity
        MainActivity.redirectActivity(this,ParkingCreate.class);

    }
    public void ClickParkingAddActivity(View view){
        //Redirect activity to about us
        MainActivity.redirectActivity(this, ParkingAddActivity.class);

    }

    public void ClickLogout(View view){
        //Close app
        MainActivity.logout(this);

    }






    @Override
    protected void onPause() {
        super.onPause();
        //Close drawer
        MainActivity.closeDrawer(drawerLayout);

    }
}
