package com.example.my38_locationmap;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ReserveAdapter extends RecyclerView.Adapter <ReserveAdapter.reserveVH> {

    private int cnt = 0;
    ReserveVO reserveVO;
    ArrayList<String> MsrVO = new ArrayList<>();
    ArrayList<String> MsmVO = new ArrayList<>();

    private List<ReserveVO> ReserveVOList;
    private Context context;

    //블루투스

    BluetoothAdapter mBluetoothAdapter;
    Set<BluetoothDevice> mPairedDevices;
    List<String> mListPairedDevices;

    Handler mBluetoothHandler;
    ConnectedBluetoothThread mThreadConnectedBluetooth;
    BluetoothDevice mBluetoothDevice;
    BluetoothSocket mBluetoothSocket;

    final static int BT_REQUEST_ENABLE = 1;
    final static int BT_MESSAGE_READ = 2;
    final static int BT_CONNECTING_STATUS = 3;
    final static UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    //final static UUID BT_UUID = UUID.fromString("00001105-0000-1000-8000-00805f9b34fb");
    //final static UUID BT_UUID = UUID.fromString("8CE255C0-200A-11E0-AC64-0800200C9A66");
    //8CE255C0-200A-11E0-AC64-0800200C9A66
    // 00001105-0000-1000-8000-00805f9b34fb
    boolean isCheck = true;

    public class MyThread extends  Thread{
        @Override
        public void run() {
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //전달
            if (mThreadConnectedBluetooth != null) {
                Log.v("tttttt","시그널보내");
                mThreadConnectedBluetooth.write("1"); //여기를 수정
            }else{
                Log.v("tttttt","안되고 있어");
            }
        }
    }


    public ReserveAdapter(List<ReserveVO> ReserveVOList,Context context ) {
        this.ReserveVOList = ReserveVOList;
        this.context = context;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        mBluetoothHandler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what == BT_MESSAGE_READ){
                    String readMessage = null;
                    try{
                        readMessage = new String((byte[])msg.obj,"UTF-8");
                    }catch(UnsupportedEncodingException e){
                        e.printStackTrace();
                    }

                }
            }
        };

    }

    @NonNull
    @Override
    public reserveVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservlist, parent, false);
        context = parent.getContext();
        return new reserveVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull reserveVH holder, int position) {

        String type;
        reserveVO = ReserveVOList.get(position);

        MsmVO.add(reserveVO.getMsm_no()+"");
        MsrVO.add(reserveVO.getMsr_no()+"");

        if(reserveVO.getMsp_type() == 1) {
            type = "월권";
        } else {
            type = "일권";
        }

        if(reserveVO.getMsr_use() == 1) {
            holder.switch_bar.setChecked(true); //주차 (주차봉이 열림)

        } else {
            holder.switch_bar.setChecked(false); //비주차 (주차봉이 닫힘)
        }

        holder.tv_type.setText(type+"");
        holder.tv_title.setText(reserveVO.getMsp_location()+"");
        holder.tv_price.setText("시간당 "+ reserveVO.getMsr_price()+"");

    }

    @Override
    public int getItemCount() {
        return ReserveVOList.size();
    }


    public class reserveVH extends RecyclerView.ViewHolder {

        TextView tv_title, tv_price, tv_type;
        LinearLayout linearLayout;
        RelativeLayout expandableLayout;
        RoundedImageView roundedImageView;
        Switch switch_bar;

        public reserveVH(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_title);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_type = itemView.findViewById(R.id.tv_type);
            switch_bar = itemView.findViewById(R.id.switch_bar);
            roundedImageView = itemView.findViewById(R.id.roundedImageView);
            linearLayout = itemView.findViewById(R.id.linear_layout);

            //DB ===================================================================


            switch_bar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                String url;
                RequestQueue requestQueue;  // 서버와 통신할 통로
                StringRequest stringRequest;    // 내가 전송할 데이터

                @Override
                public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {

                    //switch if문 시작 ================================================================
                    if (isChecked == true){

                        if(cnt >= 3){
                            bluetoothOn();
                            listPairedDevices();

                            MyThread t = new MyThread();
                            t.start();
                        }



                        Log.v("testChange",cnt+"");
                        cnt++;

//                        listPairedDevices();
//
//                        MyThread t = new MyThread();
//                        t.start();

                        Log.d("check::::",reserveVO.getMsr_no()+"");
                        url = "http://3.143.192.36/api/parkOpen";
                        requestQueue = Volley.newRequestQueue(context);

                        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.v("tetet","이거 몇번 실행되니?");
                                // 서버에서 돌려준 응답을 처리
                                if (response.equals("1")){


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

                                temp.put("msr_no",MsrVO.get(getAdapterPosition()));//reserveVO.getMsr_no()+""
                                temp.put("msm_no",MsmVO.get(getAdapterPosition()));    // put - 인덱스따라 추가가아니라 집어넣는느낌

                                return temp;


                            }
                        };

                        Log.d("use 1 ","::::msr_use:::::"+reserveVO.getMsr_use()+""+"::::"+"msr_no"+reserveVO.getMsr_no()+"::::msm_no"+reserveVO.getMsm_no());

                        Log.d("use 1 url ",url);
                        //===============================
                    }else {

                        url = "http://3.143.192.36/api/parkClose";
                        Log.d("url::::",url+"");
                        requestQueue = Volley.newRequestQueue(context);

                        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // 서버에서 돌려준 응답을 처리
                                if (response.equals("1")){

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

                                temp.put("msr_no",MsrVO.get(getAdapterPosition()));//reserveVO.getMsr_no()+""
                                temp.put("msm_no",MsmVO.get(getAdapterPosition()));    // put - 인덱스따라 추가가아니라 집어넣는느낌



                                return temp;


                            }
                        };
                        Log.d("use 0 ","::::msr_use:::::"+reserveVO.getMsr_use()+""+"::::"+"msr_no:::::"+reserveVO.getMsr_no()+"::::msm_no::::"+reserveVO.getMsm_no());
                        Log.d("use 0 ",url);
                    }
                    requestQueue.add(stringRequest);
                }
            });

        }
    }









    // blue Test

    void bluetoothOn() {
        if(mBluetoothAdapter == null){
           // Toast.makeText(getApplicationContext(),"블루투스를 지원하지 않는 기기 입니다.",Toast.LENGTH_SHORT).show();
            Log.v("tttttt","블루투스를 지원하지 않는 기기");
        }else{
            if (mBluetoothAdapter.isEnabled()) {
               // Toast.makeText(getApplicationContext(), "블루투스가 이미 활성화 되어 있습니다.", Toast.LENGTH_LONG).show();
                Log.v("tttttt","블루투스가 이미 활성화 되어있음");
            }
            else {

               // Toast.makeText(getApplicationContext(), "블루투스가 활성화 되어 있지 않습니다.", Toast.LENGTH_LONG).show();
                Intent intentBluetoothEnable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                //startActivityForResult(intentBluetoothEnable, BT_REQUEST_ENABLE);
                ((Activity) context).startActivityForResult(intentBluetoothEnable, BT_REQUEST_ENABLE);

                //((Activity)context).startActivityForResult(intentBluetoothEnable, BT_REQUEST_ENABLE);
                Log.v("tttttt","블루투스가 활성화 되어 있지 않음");
            }
        }
    }

    void bluetoothOff() {
        if (mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.disable();
           // Toast.makeText(getApplicationContext(), "블루투스가 비활성화 되었습니다.", Toast.LENGTH_SHORT).show();
            Log.v("tttttt","블루투스 비활성화 됨");
        }
        else {
           // Toast.makeText(getApplicationContext(), "블루투스가 이미 비활성화 되어 있습니다.", Toast.LENGTH_SHORT).show();
            Log.v("tttttt","블루투스 이미 비활성화 되어있음");
        }
    }


//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        switch (requestCode) {
//            case BT_REQUEST_ENABLE:
//                if (resultCode == RESULT_OK) { // 블루투스 활성화를 확인을 클릭하였다면
//                   // Toast.makeText(getApplicationContext(), "블루투스 활성화", Toast.LENGTH_LONG).show();
//
//                } else if (resultCode == RESULT_CANCELED) { // 블루투스 활성화를 취소를 클릭하였다면
//                   // Toast.makeText(getApplicationContext(), "취소", Toast.LENGTH_LONG).show();
//
//                }
//                break;
//        }
//        //super.onActivityResult(requestCode, resultCode, data);
//    }


    void listPairedDevices() {
        //Toast.makeText(context, "블루투스 목록보여주기", Toast.LENGTH_LONG).show();
        Log.v("tttttt","블루투스 목록보여주기");
        if (mBluetoothAdapter.isEnabled()) {
            mPairedDevices = mBluetoothAdapter.getBondedDevices();

            if (mPairedDevices.size() > 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("장치 선택");

                mListPairedDevices = new ArrayList<String>();
                for (BluetoothDevice device : mPairedDevices) {
                    mListPairedDevices.add(device.getName());
                    //mListPairedDevices.add(device.getName() + "\n" + device.getAddress());
                }
                final CharSequence[] items = mListPairedDevices.toArray(new CharSequence[mListPairedDevices.size()]);
                mListPairedDevices.toArray(new CharSequence[mListPairedDevices.size()]);

                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        connectSelectedDevice(items[item].toString());
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            } else {
              // Toast.makeText(context, "페어링된 장치가 없습니다.", Toast.LENGTH_LONG).show();
                Log.v("tttttt","페어링된 장치가 없습니다");
            }
        }
        else {
            //Toast.makeText(context, "블루투스가 비활성화 되어 있습니다.", Toast.LENGTH_SHORT).show();
            Log.v("tttttt","블루투스 비활성화됨");
        }
    }

    void connectSelectedDevice(String selectedDeviceName) {
        for(BluetoothDevice tempDevice : mPairedDevices) {
            if (selectedDeviceName.equals(tempDevice.getName())) {
                mBluetoothDevice = tempDevice;
                break;
            }
        }
        try {
            mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(BT_UUID);
            mBluetoothSocket.connect();
            mThreadConnectedBluetooth = new ConnectedBluetoothThread(mBluetoothSocket);
            mThreadConnectedBluetooth.start();
            mBluetoothHandler.obtainMessage(BT_CONNECTING_STATUS, 1, -1).sendToTarget();
        } catch (IOException e) {
           // Toast.makeText(getApplicationContext(), "블루투스 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            Log.v("tttttt","블루투스 연결 중 오류");
            e.printStackTrace();
        }
    }


    private class ConnectedBluetoothThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedBluetoothThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                //Toast.makeText(getApplicationContext(), "소켓 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
                Log.v("tttttt","소켓 연결 중 오류");
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;

            while (true) {
                try {
                    bytes = mmInStream.available();
                    if (bytes != 0) {
                        SystemClock.sleep(100);
                        bytes = mmInStream.available();
                        bytes = mmInStream.read(buffer, 0, bytes);
                        mBluetoothHandler.obtainMessage(BT_MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                    }
                } catch (IOException e) {
                    break;
                }
            }
        }
        public void write(String str) {
            byte[] bytes = str.getBytes();
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
                //Toast.makeText(getApplicationContext(), "데이터 전송 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
                Log.v("tttttt","데이터 전송 중 오류");
            }
        }
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                //Toast.makeText(getApplicationContext(), "소켓 해제 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
                Log.v("tttttt","소켓 해제 중 오류  ");
            }
        }
    }



}