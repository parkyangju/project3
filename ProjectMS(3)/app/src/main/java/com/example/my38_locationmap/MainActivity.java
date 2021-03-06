package com.example.my38_locationmap;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    //????????? ?????? ??????
    private static final String TAG = "MainActivity";

    private TextView tv_marker;

    public MarkerItem clickedMarker = null;

    //?????? ??????
    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private ImageButton btn_search;
    private EditText editText;
    private MarkerOptions myMarker;
    private ClusterManager<MarkerItem> clusterManager;
    DrawerLayout drawerLayout;
    Geocoder geocoder;
    DBActivity dbActivity = new DBActivity();
    List<MarkerItem> MarkerVOList = new ArrayList<>();


    ArrayList sampleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.calendar_layout);
        setContentView(R.layout.activity_main);
        DBActivity DBA = new DBActivity();

        //?????? ?????????
        //Assign variable
        drawerLayout =findViewById(R.id.drawer_layout);
        // ImageView imageView = findViewById(R.id.myMenu);


        //?????? ??????
        checkDangerousPermissions();

        //?????? ?????????
        editText = findViewById(R.id.editText);
        btn_search = findViewById(R.id.btn_search);

        //?????? ???????????? ????????? ?????????
        clusterManager = new ClusterManager<>(this, map);


        //?????? ??????????????? ??????
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                //????????? ?????? ?????? ??????
                UiSettings uiSettings = googleMap.getUiSettings();
                //uiSettings.setCompassEnabled(false);
                uiSettings.setZoomControlsEnabled(true);

                Log.d(TAG, "onMapReady: ");

                //?????? ??? ??????
                map = googleMap;  // GoogleMap map = googleMap;

                try {
                    //GPS ?????? ?????? (?????? ??????)
                    map.setMyLocationEnabled(true);
                    setUpClusterer();

                    // ???????????? ?????? ????????? ????????? ?????????
                    clusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MarkerItem>() {
                        @Override
                        public boolean onClusterClick(Cluster<MarkerItem> cluster) {
                            LatLngBounds.Builder builder_c = LatLngBounds.builder();
                            for (ClusterItem item : cluster.getItems()) {
                                builder_c.include(item.getPosition());
                            }
                            LatLngBounds bounds_c = builder_c.build();
                            map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds_c, 16));
                            float zoom = map.getCameraPosition().zoom - 0.5f;
                            map.animateCamera(CameraUpdateFactory.zoomTo(zoom));
                            return true;
                        }
                    });

                    //?????? ?????? ?????????
                    map.setOnMarkerClickListener(clusterManager);

                    clusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MarkerItem>() {
                        @Override
                        public boolean onClusterItemClick(final MarkerItem markerItem) {
                            //?????? ???????????? ????????? ??????
                            map.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(markerItem.msp_lat, markerItem.msp_lon)));

                            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                                    MainActivity.this, R.style.BottomSheetDialogTheme
                            );
                            View bottomSheetView = LayoutInflater.from(getApplicationContext())
                                    .inflate(
                                            R.layout.layout_bottom_sheet,
                                            (LinearLayout) findViewById(R.id.btn_add)
                                    );

                            //?????????(????????? ??????)
                            TextView title = bottomSheetView.findViewById(R.id.tv_title);
                            String myaddress = markerlocation(markerItem.msp_lat,markerItem.msp_lon);
                            String myaddress2 = myaddress.replaceFirst("????????????", "");
                            title.setText(myaddress2);

                            //????????? ????????????
                            TextView time  = bottomSheetView.findViewById(R.id.tv_time);
                            time.setText(" ?????? 9??? ~ ?????? 6???");

                            //????????? ??????
                            TextView price = bottomSheetView.findViewById(R.id.tv_price);
                            price.setText(" ????????? "+markerItem.msp_price + "???");
                            
                            //????????? ??????????????? ??????
                            bottomSheetView.findViewById(R.id.btn_road).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //?????? GPS ??????
                                    Double mylat = map.getMyLocation().getLatitude();
                                    Double mylng = map.getMyLocation().getLongitude();

                                    Log.d(TAG, "mylocation = "+ mylat + "," + mylng);

                                    Double placelat = markerItem.msp_lat;
                                    Double placelng = markerItem.msp_lon;
                                    Log.d(TAG, "mylocation = "+ placelat + "," + placelng);

                                   // String url = "daummaps://route?sp="+mylat+","+mylng+"&ep="+placelat+","+placelng+"&by=FOOT";
                                    String url = "kakaomap://route?sp="+mylat+","+mylng+"&ep="+placelat+","+placelng+"&by=CAR";
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    startActivity(intent);


                                }
                            });

                            //???????????? ?????? ?????????
                            bottomSheetView.findViewById(R.id.buttonShare).setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                   // Toast.makeText(MainActivity.this,"Share...",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),ReservationActivity.class);
//                                    String msp_no; //????????? ?????? ??????
//                                    String msm_no; //????????? ????????????
//                                    String msp_location; //????????????
//                                    LatLng msp_position;
//                                    double msp_lat; //??????
//                                    double msp_lon; //??????
//                                    int msp_price; //????????????
//                                    int msp_num; //????????????
//                                    int msp_type; //????????????
//                                    String msp_date; //?????????

//                                    RealItem realItem = new RealItem(markerItem.msp_no, markerItem.msm_no, markerItem.msp_location,
//                                            markerItem.msp_position,
//                                            markerItem.msp_lat, markerItem.msp_lon, markerItem.msp_price, markerItem.msp_num,
//                                            markerItem.msp_type, markerItem.msp_date);

//                                    Log.d(TAG, "markerItem = "+markerItem.toString());
//                                    RealItem realItem = new RealItem();
//                                    realItem.setMsp_no(markerItem.msp_no);
//                                    realItem.setMsp_position(markerItem.msp_position);

                                    intent.putExtra("markerItem", markerItem);
//                                    clickedMarker = markerItem;
                                    startActivity(intent);


                                    //???????????? ??? ?????????
                                    bottomSheetDialog.dismiss();


                                }
                            });

                            bottomSheetDialog.setContentView(bottomSheetView);
                            bottomSheetDialog.show();
                            //?????? ???????????? ????????? ???????????? ????????? ????????????
//                            Toast.makeText(MainActivity.this,markerItem.position.toString(),Toast.LENGTH_SHORT).show();
//                            Toast.makeText(MainActivity.this,markerItem.title.toString(),Toast.LENGTH_SHORT).show();
//                            Toast.makeText(MainActivity.this,markerItem.price.toString(),Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    });


                } catch (SecurityException e) {

                }
            }
        });
        MapsInitializer.initialize(this);

        //??????????????? ????????? ?????? ?????? ?????? ?????????
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().length() > 0) {
                    //?????? ????????? ????????? ????????? ????????? ??????
                    Location location = getLocationFromAddress(getApplicationContext(), editText.getText().toString());

                    //?????? ????????? ?????? ????????? ????????? ??????
                    showCurrentLocation(location);
                }
            }
        });


    }

    //?????? ?????? ?????? ????????? -> ????????? ????????????
public String markerlocation(double lat,double lng) {
    List<Address> addresses = null;
    geocoder = new Geocoder(this, Locale.getDefault());

    try {
        addresses = geocoder.getFromLocation(lat, lng, 1);
    } catch (Exception e) {
        e.printStackTrace();
    }
    String address = addresses.get(0).getAddressLine(0);
    
    return address;
}



    //??????????????? ????????? ????????? ????????? ?????? ????????? ??????????????? ?????????
    private Location getLocationFromAddress(Context context, String address) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses;
        Location resLocation = new Location("");
        try {
            addresses = geocoder.getFromLocationName(address, 5);
            if((addresses == null) || (addresses.size() == 0)) {
                return null;
            }
            Address addressLoc = addresses.get(0);

            resLocation.setLatitude(addressLoc.getLatitude());
            resLocation.setLongitude(addressLoc.getLongitude());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resLocation;
    }




    //?????? ?????? ?????? ?????? ????????? (?????? ??????)
    private void showCurrentLocation(Location location) {
        LatLng curPoint = new LatLng(location.getLatitude(), location.getLongitude());
        String msg = "Latitutde : " + curPoint.latitude
                + "\nLongitude : " + curPoint.longitude;

        //???????????? ????????? ????????? ?????? ?????? ??????
       // Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        //?????? ??????, ????????? ????????? ??????
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));
    }

    //------------------?????? ?????? ??????------------------------
    private void checkDangerousPermissions() {
        String[] permissions = {
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_WIFI_STATE
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
          //  Toast.makeText(this, "?????? ??????", Toast.LENGTH_LONG).show();
        } else {
         //   Toast.makeText(this, "?????? ??????", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
            //    Toast.makeText(this, "?????? ?????? ?????????.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
              //      Toast.makeText(this, permissions[i] + " ????????? ?????????.", Toast.LENGTH_LONG).show();
                } else {
              //      Toast.makeText(this, permissions[i] + " ????????? ???????????? ??????.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    //------------------?????? ?????? ???------------------------

    //?????? ??????

//    private void getSampleMarkerItems() {
////
////        //?????? DB??? ??????
//        ArrayList<MarkerItem> sampleList = new ArrayList();
////
//        sampleList.add(new MarkerItem("????????? ?????????",35.152916277678024, 126.91507705274016, "2500000" , "1500"));
//        sampleList.add(new MarkerItem("????????? ?????????",35.1480388479638, 126.91117175632145, "100000", "1500"));
//        sampleList.add(new MarkerItem("????????? ????????? ",35.15161799760233, 126.90722354455748, "15000", "1500"));
//        sampleList.add(new MarkerItem("????????? ?????????",35.15719694629607, 126.90271743330514, "5000", "1500"));
//        sampleList.add(new MarkerItem("????????? ?????????",35.154144361386074, 126.91649325913374, "4000", "1500"));
//        sampleList.add(new MarkerItem("????????? ?????????",35.149126645388314, 126.91168674046457, "2500000", "1500"));
//        sampleList.add(new MarkerItem("????????? ?????????",35.14853011311741, 126.916536174479, "100000", "1500"));
//        sampleList.add(new MarkerItem("????????? ????????? ",35.14828448091133, 126.91190131719087, "15000", "1500"));
//        sampleList.add(new MarkerItem("????????? ?????????",35.151653086525386, 126.91297420082238, "5000", "1500"));
//        sampleList.add(new MarkerItem("????????? ?????????",35.1512320184499, 126.91400416910864, "4000", "1500"));
////
////        //?????? ????????? ????????????
//          View marker_root_view = LayoutInflater.from(this).inflate(R.layout.marker_layout, null);
////        tv_marker = (TextView) marker_root_view.findViewById(R.id.tv_marker);
////        //?????? ????????? ????????????
////        for (int i = 0 ; i < sampleList.size(); i++) {
////            //???????????? ????????? ??????
////            tv_marker.setText(sampleList.get(i).getPrice());
////            myMarker = new MarkerOptions();
////            myMarker
////                    .position(sampleList.get(i).position);
////            myMarker.getInfoWindowAnchorU();
////            myMarker.getInfoWindowAnchorV();
////
//            myMarker.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this,marker_root_view)));
////            myMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.mylocation));
//
////            // ?????? ?????? (????????? ?????????)
////            map.addMarker(myMarker).hideInfoWindow();
////
////        }
////        map.addMarker(myMarker);
//    }

    // View??? Bitmap?????? ?????? (?????? ????????? ???????????? ?????? ?????????)
//    private Bitmap createDrawableFromView(Context context, View view) {
//
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
//        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
//        view.buildDrawingCache();
//        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
//
//        Canvas canvas = new Canvas(bitmap);
//        view.draw(canvas);
//
//        return bitmap;
//    }

    //???????????? ????????? ?????????
    private void setUpClusterer() {
        //?????? GPS ??????

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.149883400000014, 126.91993394232735), 10));

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        clusterManager = new ClusterManager<MarkerItem>(this, map);

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
//        map.setOnCameraIdleListener(clusterManager);
        final CameraPosition[] mPreviousCameraPosition = {null};
        map.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                CameraPosition position = map.getCameraPosition();
                if(mPreviousCameraPosition[0] == null || mPreviousCameraPosition[0].zoom != position.zoom) {
                    mPreviousCameraPosition[0] = map.getCameraPosition();
                    clusterManager.cluster();
                }
            }
        });
        map.setOnMarkerClickListener(clusterManager);

        // Add cluster items (markers) to the cluster manager.
        addItems();
    }

    //DB?????? ????????? ?????? ?????? ?????????
    private void addItems() {


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://3.143.192.36/api/parkingListSelect2";
        //String url = "http://172.30.1.23:8081/smartcar/api/parkingList";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                try {

                    //for (int i = response.length(); i > 0; i--) {
                    for (int i = 0; i<response.length(); i++){
                        JSONObject jsonObject = response.getJSONObject(i);

                        //int no= Integer.parseInt(jsonObject.getString("no")); //no??? ?????????????????? ????????????.
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

                    for(int i = 0 ; i<MarkerVOList.size(); i++) {
                        clusterManager.addItem(MarkerVOList.get(i));
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

        //?????? ?????? ????????? ??????????????? ????????? ?????? ??????

        //???????????? ?????? ?????? ??????
        requestQueue.add(jsonArrayRequest);



//        sampleList.add(new MarkerItem("1","1", "??????????????? ?????? ?????????  27-2" ,35.15086128213082, 126.91565591341606,3000,5,1,"2021.04.19",
//                0,"administrator"
//        ));
//        sampleList.add(new MarkerItem("2","2", "??????????????? ?????? ?????????  27-5" ,35.1516771032652, 126.91596704964208,2000,5,2,"2021.04.19",
//                0,"administrator" ));
//
//        sampleList.add(new MarkerItem("3","3", "??????????????? ?????? ?????????  27-6" ,35.15048407174421, 126.91715795036939,1000,5,1,"2021.04.19",
//                0,"administrator"));
//
//        sampleList.add(new MarkerItem("4","4", "??????????????? ?????? ?????????  28-7" ,35.15272976361022, 126.91747981543082,5000,5,2,"2021.04.19",
//                0,"administrator"
//        ));
//        sampleList.add(new MarkerItem("5","5","??????????????? ?????? ?????????  27-8" ,35.1528525730967, 126.91545206554378,5000,5,1,"2021.04.19",
//                0,"administrator"
//        ));
//        sampleList.add(new MarkerItem("6","6", "??????????????? ?????? ?????????  26-9" ,35.15252800476574, 126.91551643855607,6000,5,2,"2021.04.19",
//                0,"administrator"
//        ));
//        sampleList.add(new MarkerItem("7","7","??????????????? ?????? ?????????  22-1" ,35.15208939685609, 126.91448647035948,1000,5,1,"2021.04.19",
//                0,"administrator"
//        ));
//        sampleList.add(new MarkerItem("8","8", "??????????????? ?????? ?????????  29-2" ,35.15121217394378, 126.9182201050721,2100,5,2,"2021.04.19",
//                0,"administrator"
//        ));

    }


    public MarkerItem getMarker(){
        return this.clickedMarker;
    }


    //============?????????????????? ??????????????? ?????? ?????????========================================================================================

    public void ClickMenu(View view){
        //open drawer
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        //open drawer Layout
        drawerLayout.openDrawer(GravityCompat.START);
        //Close drawer  
        closeDrawer(drawerLayout);

    }
    public void ClickLogo(View view){

        closeDrawer(drawerLayout);


    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
                //check condition

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            //when drawer is open
            //close drawer
            drawerLayout.closeDrawer(GravityCompat.START);

        }

    }
    
    public void ClickHome(View view){

        //Recreate activity
        recreate();

    }
    public void ClickDashboard(View view){
        //Redirect activity to dashboard
        redirectActivity(this, Dashboard.class);
    }
    public void ClickParkingCreate(View view){
        //Redirect activity to about us
        redirectActivity(this, ParkingCreate.class);

    }
    public void ClickParkingAddActivity(View view){
        //Redirect activity to about us
        //Intent intent = new Intent(this, ParkingAddActivity.class);
        redirectActivity(this, ParkingAddActivity.class);
        //finish();
    }

    public void ClickBoardActivity(View view){
        Intent intent = new Intent(this, boardActivity.class);
        startActivity(intent);
        //redirectActivity(this, boardActivity.class);
        //sendRequest();
    }

    public void ClickLogout(View view){

        logout(this);

    }

    //?????? MarkerItem ??????
    public static void logout(final Activity activity) {

        //INITIALIZE alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //set title
        builder.setTitle("Logout");
        //set message
        builder.setMessage("?????? ???????????? ???????????????????");
        //Positive yes button
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Finish activity
                activity.finishAffinity();
                //exit app
                System.exit(0);
            }
        });
        //Negative no button
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Dismiss dialog
                dialog.dismiss();
            }
        });
        //show dialog
        builder.show();
    }

    public static void redirectActivity(Activity activity, Class aClass) {
        //Initialize intent
        Intent intent= new Intent(activity,aClass);
        //set flag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //START activity
        activity.startActivity(intent);

    }

    @Override
    protected void onPause() {
        super.onPause();
        //Close drawer
        closeDrawer(drawerLayout);

    }



}
