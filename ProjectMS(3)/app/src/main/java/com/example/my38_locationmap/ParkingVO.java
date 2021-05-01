package com.example.my38_locationmap;

public class ParkingVO {

    String msp_no; //주차장 고유 번호
    String msm_no; //작성자 고유번호
    String msp_location; //위치주소
    String msp_lat; //위도
    String msp_lon; //경도
    String msp_num; //자리개수
    String msp_type; //주차타입
    String msp_date; //작성일

    public ParkingVO(String msp_no, String msm_no, String msp_location, String msp_lat, String msp_lon, String msp_num, String msp_type, String msp_date) {
        this.msp_no = msp_no;
        this.msm_no = msm_no;
        this.msp_location = msp_location;
        this.msp_lat = msp_lat;
        this.msp_lon = msp_lon;
        this.msp_num = msp_num;
        this.msp_type = msp_type;
        this.msp_date = msp_date;
        this.expandable = false;
    }

    private boolean expandable;

    public ParkingVO(ParkingVO parkingVO) {
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
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

    public String getMsp_lat() {
        return msp_lat;
    }

    public void setMsp_lat(String msp_lat) {
        this.msp_lat = msp_lat;
    }

    public String getMsp_lon() {
        return msp_lon;
    }

    public void setMsp_lon(String msp_lon) {
        this.msp_lon = msp_lon;
    }

    public String getMsp_num() {
        return msp_num;
    }

    public void setMsp_num(String msp_num) {
        this.msp_num = msp_num;
    }

    public String getMsp_type() {
        return msp_type;
    }

    public void setMsp_type(String msp_type) {
        this.msp_type = msp_type;
    }

    public String getMsp_date() {
        return msp_date;
    }

    public void setMsp_date(String msp_date) {
        this.msp_date = msp_date;
    }

    @Override
    public String toString() {
        return "ParkingVO{" +
                "msp_no='" + msp_no + '\'' +
                ", msm_no='" + msm_no + '\'' +
                ", msp_location='" + msp_location + '\'' +
                ", msp_lat='" + msp_lat + '\'' +
                ", msp_lon='" + msp_lon + '\'' +
                ", msp_num='" + msp_num + '\'' +
                ", msp_type='" + msp_type + '\'' +
                ", msp_date='" + msp_date + '\'' +
                ", expandable=" + expandable +
                '}';
    }


}
