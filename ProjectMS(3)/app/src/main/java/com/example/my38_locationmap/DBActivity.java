package com.example.my38_locationmap;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class DBActivity extends AppCompatActivity {



    // 여기

    public MarkerItem MarkerVO;

    public static List<MarkerItem> MarkerVOList;

    private final int WRITE = 1;

    String url = "http://3.143.192.36/api/parkingList";
    RequestQueue requestQueue;  // 서버와 통신할 통로


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MarkerVOList = new ArrayList<>();
        MarkerVOList.add(new MarkerItem(MarkerVO));
        requestQueue = Volley.newRequestQueue(this);


        //결과를 JsonArray 받을 것이므로.. StringRequest가 아니라.. JsonArrayRequest를 이용할 것임
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            //volley 라이브러리의 GET방식은 버튼 누를때마다 새로운 갱신 데이터를 불러들이지 않음. 그래서 POST 방식 사용
            @Override
            public void onResponse(JSONArray response) {
                //Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();


                //파라미터로 응답받은 결과 JsonArray를 분석

                //System.out.println(response.length());
                MarkerVOList.clear();
                try {

                    //for (int i = response.length(); i > 0; i--) {
                    for (int i = 0; i<response.length(); i++){
                        JSONObject jsonObject = response.getJSONObject(i);

                        //int no= Integer.parseInt(jsonObject.getString("no")); //no가 문자열이라서 바꿔야함.
                        String msp_no = jsonObject.getString("msp_no");
                        String msm_no = jsonObject.getString("msm_no");
                        String msp_location = jsonObject.getString("msp_location");
                        Double msp_lat = jsonObject.getDouble("msp_lat");
                        Double msp_lon = jsonObject.getDouble("msp_lon");
                        int msp_price = 3000;
                        int msp_num = jsonObject.getInt("msp_num");
                        int msp_type = jsonObject.getInt("msp_type");
                        String msp_date = jsonObject.getString("msp_date");
                        int msr_num = jsonObject.getInt("msr_num");
                        String msm_name = jsonObject.getString("msm_name");
                        //System.out.println(msnb_subject);

                        MarkerVOList.add(new MarkerItem(msp_no,msm_no,msp_location,msp_lat,msp_lon,msp_price,msp_num,msp_type,msp_date,msr_num,msm_name));
                        System.out.println(MarkerVOList.toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });

        //실제 요청 작업을 수행해주는 요청큐 객체 생성
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //요청큐에 요청 객체 생성
        requestQueue.add(jsonArrayRequest);
        Toast.makeText(getApplicationContext(), "성공ㅋ", Toast.LENGTH_SHORT).show();
    }
}