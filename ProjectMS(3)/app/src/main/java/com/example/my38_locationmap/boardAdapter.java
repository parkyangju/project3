package com.example.my38_locationmap;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class boardAdapter extends RecyclerView.Adapter <boardAdapter.boardVH>  {
    RequestQueue requestQueue;  // 서버와 통신할 통로
    StringRequest deleteRequest;    // 내가 전송할 데이터

    private List<BoardVO> boardVOList;
    private ImageButton btn_modify;
    private ImageButton btn_del;
    private Context context;
    private TextView msnb_no;

    public boardAdapter(List<BoardVO> boardVOList) {
        this.boardVOList = boardVOList;
    }

    @NonNull
    @Override
    public boardVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.boardlist, parent, false);
        context = parent.getContext();
        return new boardVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull boardVH holder, final int position) {

        BoardVO boardVO = boardVOList.get(position);
        holder.subjectTxt.setText(boardVO.getMsnb_subject());
        holder.contentTxt.setText(boardVO.getMsnb_content());
        holder.no.setText(boardVO.getMsnb_no());
        holder.dateTxt.setText(boardVO.getMsnb_date());

        boolean isExpandable = boardVOList.get(position).isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

        btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("test", "수정 눌림");
                Intent intent = new Intent(context, ContentActivity.class);

                System.out.println(boardVOList.get(position).getMsnb_no());
                //System.out.println(boardVOList.get(position).getMsnb_subject());
                //System.out.println(boardVOList.get(position).getMsnb_content());

                intent.putExtra("msm_no",boardVOList.get(position).getMsm_no());
                intent.putExtra("msnb_no",boardVOList.get(position).getMsnb_no());
                intent.putExtra("subjectTxt",boardVOList.get(position).getMsnb_subject());
                intent.putExtra("contentTxt",boardVOList.get(position).getMsnb_content());

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
            return boardVOList.size();
    }


    public class boardVH extends RecyclerView.ViewHolder {

        TextView subjectTxt, contentTxt, dateTxt, no;
        LinearLayout linearLayout;
        RelativeLayout expandableLayout;

        public boardVH(@NonNull View itemView) {
            super(itemView);

            subjectTxt = itemView.findViewById(R.id.msm_subject);
            contentTxt = itemView.findViewById(R.id.msnb_content);
            dateTxt = itemView.findViewById(R.id.msnb_date);
            no = itemView.findViewById(R.id.msnb_no);

            linearLayout = itemView.findViewById(R.id.linear_layout);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);
            btn_modify = itemView.findViewById(R.id.btn_modify);
            btn_del = itemView.findViewById(R.id.btn_del);

            requestQueue = Volley.newRequestQueue(context);


            linearLayout.setOnClickListener(new View.OnClickListener() {    // 컨텍스트 메뉴 생성
                @Override
                public void onClick(View v) {
                    BoardVO boardVO = boardVOList.get(getAdapterPosition());
                    boardVO.setExpandable(!boardVO.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });






            btn_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Log.d("test2", "삭제 눌림");

                    int position = Integer.parseInt(no.getText().toString());
                    requestQueue.add(deleteRequest(position));


                    // 뷰에서 없애기
                    boardVOList.remove(getLayoutPosition());

                    notifyDataSetChanged();

                }
            });

        }

        private StringRequest deleteRequest(final int position) {
            String url ="http://3.143.192.36/api/noticeDelete";

            deleteRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
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
                    temp.put("msnb_no", String.valueOf(position));
                    System.out.println("msnb_no : "+ String.valueOf(position));


                    return temp;

                }
            };

            return deleteRequest;
        }

    }
}