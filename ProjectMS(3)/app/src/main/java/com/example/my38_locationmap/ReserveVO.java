package com.example.my38_locationmap;

public class ReserveVO {

    private int msr_no;     // 게시판 고유번호
    private int msp_no;      // 작성자 고유번호
    private int msr_num;       // 제목
    private String msr_date;
    private int msr_price;     // 내용
    private int msm_no;
    private int msr_status;    // 조회수
    private int msr_reserv; //사용자 이름
    private String msr_sdate;
    private String msr_edate;
    private int msr_use;
    private String msp_location;
    private Double msp_lat;
    private Double msp_lon;
    private int msp_type;
    private String msm_name;
    private boolean expandable;

    public ReserveVO(int msr_no, int msp_no, int msr_num, String msr_date, int msr_price, int msm_no, int msr_status, int msr_reserv, String msr_sdate, String msr_edate, int msr_use, String msp_location, Double msp_lat, Double msp_lon, int msp_type, String msm_name) {
        this.msr_no = msr_no;
        this.msp_no = msp_no;
        this.msr_num = msr_num;
        this.msr_date = msr_date;
        this.msr_price = msr_price;
        this.msm_no = msm_no;
        this.msr_status = msr_status;
        this.msr_reserv = msr_reserv;
        this.msr_sdate = msr_sdate;
        this.msr_edate = msr_edate;
        this.msr_use = msr_use;
        this.msp_location = msp_location;
        this.msp_lat = msp_lat;
        this.msp_lon = msp_lon;
        this.msp_type = msp_type;
        this.msm_name = msm_name;
        this.expandable = false;
    }

    public ReserveVO(ReserveVO reserveVO) {
        this.expandable = false;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public int getMsr_no() {
        return msr_no;
    }

    public void setMsr_no(int msr_no) {
        this.msr_no = msr_no;
    }

    public int getMsp_no() {
        return msp_no;
    }

    public void setMsp_no(int msp_no) {
        this.msp_no = msp_no;
    }

    public int getMsr_num() {
        return msr_num;
    }

    public void setMsr_num(int msr_num) {
        this.msr_num = msr_num;
    }

    public String getMsr_date() {
        return msr_date;
    }

    public void setMsr_date(String msr_date) {
        this.msr_date = msr_date;
    }

    public int getMsr_price() {
        return msr_price;
    }

    public void setMsr_price(int msr_price) {
        this.msr_price = msr_price;
    }

    public int getMsm_no() {
        return msm_no;
    }

    public void setMsm_no(int msm_no) {
        this.msm_no = msm_no;
    }

    public int getMsr_status() {
        return msr_status;
    }

    public void setMsr_status(int msr_status) {
        this.msr_status = msr_status;
    }

    public int getMsr_reserv() {
        return msr_reserv;
    }

    public void setMsr_reserv(int msr_reserv) {
        this.msr_reserv = msr_reserv;
    }

    public String getMsr_sdate() {
        return msr_sdate;
    }

    public void setMsr_sdate(String msr_sdate) {
        this.msr_sdate = msr_sdate;
    }

    public String getMsr_edate() {
        return msr_edate;
    }

    public void setMsr_edate(String msr_edate) {
        this.msr_edate = msr_edate;
    }

    public int getMsr_use() {
        return msr_use;
    }

    public void setMsr_use(int msr_use) {
        this.msr_use = msr_use;
    }

    public String getMsp_location() {
        return msp_location;
    }

    public void setMsp_location(String msp_location) {
        this.msp_location = msp_location;
    }

    public Double getMsp_lat() {
        return msp_lat;
    }

    public void setMsp_lat(Double msp_lat) {
        this.msp_lat = msp_lat;
    }

    public Double getMsp_lon() {
        return msp_lon;
    }

    public void setMsp_lon(Double msp_lon) {
        this.msp_lon = msp_lon;
    }

    public int getMsp_type() {
        return msp_type;
    }

    public void setMsp_type(int msp_type) {
        this.msp_type = msp_type;
    }

    public String getMsm_name() {
        return msm_name;
    }

    public void setMsm_name(String msm_name) {
        this.msm_name = msm_name;
    }

    @Override
    public String toString() {
        return "ReserveVO{" +
                "msr_no=" + msr_no +
                ", msp_no=" + msp_no +
                ", msr_num=" + msr_num +
                ", msr_date='" + msr_date + '\'' +
                ", msr_price=" + msr_price +
                ", msm_no=" + msm_no +
                ", msr_status=" + msr_status +
                ", msr_reserv=" + msr_reserv +
                ", msr_sdate='" + msr_sdate + '\'' +
                ", msr_edate='" + msr_edate + '\'' +
                ", msr_use=" + msr_use +
                ", msp_location='" + msp_location + '\'' +
                ", msp_lat=" + msp_lat +
                ", msp_lon=" + msp_lon +
                ", msp_type=" + msp_type +
                ", msm_name='" + msm_name + '\'' +
                ", expandable=" + expandable +
                '}';
    }
}
