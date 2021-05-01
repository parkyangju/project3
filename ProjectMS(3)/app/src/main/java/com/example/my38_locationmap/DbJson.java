package com.example.my38_locationmap;

import android.content.Context;

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

public class DbJson {
    public static List<MarkerItem> MarkerVOList = new ArrayList<>();

    public ArrayList<MarkerItem> DBget(Context context) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        String url = "http://3.143.192.36/api/parkingList";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

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

        //요청큐에 요청 객체 생성
        requestQueue.add(jsonArrayRequest);

        return (ArrayList<MarkerItem>) MarkerVOList;
    }
}
