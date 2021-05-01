package com.example.my38_locationmap;


import java.io.Serializable;

public class RealItem implements Serializable {

    String msp_no; //주차장 고유 번호
    String msm_no; //작성자 고유번호
    String msp_location; //위치주소
    double msp_lat; //위도
    double msp_lon; //경도
    int msp_price; //자리개수
    int msp_num; //자리개수
    int msp_type; //주차타입
    String msp_date; //작성일

    public RealItem() {
    }

    @Override
    public String toString() {
        return "RealItem{" +
                "msp_no='" + msp_no + '\'' +
                ", msm_no='" + msm_no + '\'' +
                ", msp_location='" + msp_location + '\'' +
                ", msp_lat=" + msp_lat +
                ", msp_lon=" + msp_lon +
                ", msp_price=" + msp_price +
                ", msp_num=" + msp_num +
                ", msp_type=" + msp_type +
                ", msp_date='" + msp_date + '\'' +
                '}';
    }

    public String getMsp_no() {
        return msp_no;
    }

    public void setMsp_no(String msp_no) {
        this.msp_no = msp_no;
    }

    public String getMsm_no() {
        return msm_no;
    }

    public void setMsm_no(String msm_no) {
        this.msm_no = msm_no;
    }

    public String getMsp_location() {
        return msp_location;
    }

    public void setMsp_location(String msp_location) {
        this.msp_location = msp_location;
    }


    public double getMsp_lat() {
        return msp_lat;
    }

    public void setMsp_lat(double msp_lat) {
        this.msp_lat = msp_lat;
    }

    public double getMsp_lon() {
        return msp_lon;
    }

    public void setMsp_lon(double msp_lon) {
        this.msp_lon = msp_lon;
    }

    public int getMsp_price() {
        return msp_price;
    }

    public void setMsp_price(int msp_price) {
        this.msp_price = msp_price;
    }

    public int getMsp_num() {
        return msp_num;
    }

    public void setMsp_num(int msp_num) {
        this.msp_num = msp_num;
    }

    public int getMsp_type() {
        return msp_type;
    }

    public void setMsp_type(int msp_type) {
        this.msp_type = msp_type;
    }

    public String getMsp_date() {
        return msp_date;
    }

    public void setMsp_date(String msp_date) {
        this.msp_date = msp_date;
    }

    public RealItem(String msp_no, String msm_no, String msp_location, double msp_lat, double msp_lon, int msp_price, int msp_num, int msp_type, String msp_date) {
        this.msp_no = msp_no;
        this.msm_no = msm_no;
        this.msp_location = msp_location;
        this.msp_lat = msp_lat;
        this.msp_lon = msp_lon;
        this.msp_price = msp_price;
        this.msp_num = msp_num;
        this.msp_type = msp_type;
        this.msp_date = msp_date;
    }
}
