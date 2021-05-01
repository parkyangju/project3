package com.example.my38_locationmap;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

public class ParkingAdapter extends RecyclerView.Adapter <ParkingAdapter.ParkVH>{
    RequestQueue requestQueue;  // 서버와 통신할 통로
    StringRequest deleteRequest;    // 내가 전송할 데이터

    private List<ParkingVO> parkingVOList;
    private Context context;

    public ParkingAdapter(List<ParkingVO> parkingVO) {
        this.parkingVOList = parkingVO;
    }

    @NonNull
    @Override
    public ParkVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parkinglist, parent, false);
        context = parent.getContext();
        return new ParkVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParkVH holder, int position) {
        String type;
        ParkingVO parkingVO = parkingVOList.get(position);
        Log.d("parking msp_type ::::",parkingVO.msp_type);
        if(parkingVO.getMsp_type().equals("1")) {
            type = "월권";
        } else {
            type = "일권";
        }

        holder.location_txt.setText(parkingVO.getMsp_location());
        holder.date_Txt.setText(parkingVO.getMsp_date());
        holder.type_txt.setText(type+"");
        holder.num_txt.setText(parkingVO.getMsp_num());

 //       boolean isExpandable = parkingVOList.get(position).isExpandable();
  //      holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return parkingVOList.size();
    }


    public class ParkVH extends RecyclerView.ViewHolder {

        TextView location_txt, date_Txt, type_txt, num_txt;
        LinearLayout linearLayout;
        RelativeLayout expandableLayout;

        public ParkVH(@NonNull View itemView) {
            super(itemView);

            location_txt = itemView.findViewById(R.id.tv_location);
            date_Txt = itemView.findViewById(R.id.tv_date);
            type_txt = itemView.findViewById(R.id.tv_type);
            num_txt = itemView.findViewById(R.id.tv_num);

            linearLayout = itemView.findViewById(R.id.linear_layout);
        //    expandableLayout = itemView.findViewById(R.id.);

            requestQueue = Volley.newRequestQueue(context);

            linearLayout.setOnClickListener(new View.OnClickListener() {    // 컨텍스트 메뉴 생성
                @Override
                public void onClick(View v) {
                    ParkingVO parkingVO = parkingVOList.get(getAdapterPosition());
                    parkingVO.setExpandable(!parkingVO.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });


        }
    }
}