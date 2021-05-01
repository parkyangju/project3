package com.example.my38_locationmap;

public class BoardVO {
    private String msnb_no;     // 게시판 고유번호
    private String msm_no;      // 작성자 고유번호
    private String msnb_subject;       // 제목
    private String msnb_content;     // 내용
    private String msnb_date;
    private String msnb_hit;    // 조회수
    private String msm_name; //사용자 이름

    private boolean expandable;


    public BoardVO(String msnb_no, String msm_no, String msnb_subject, String msnb_content, String msnb_date, String msnb_hit, String msm_name) {
        this.msnb_no = msnb_no;
        this.msm_no = msm_no;
        this.msnb_subject = msnb_subject;
        this.msnb_content = msnb_content;
        this.msnb_date = msnb_date;
        this.msnb_hit = msnb_hit;
        this.msm_name = msm_name;
        this.expandable = false;
    }

    public BoardVO(String msnb_subject, String msnb_content, String msnb_date) {
        this.msnb_subject = msnb_subject;
        this.msnb_content = msnb_content;
        this.msnb_date = msnb_date;

        this.expandable = false;

    }

    public BoardVO(BoardVO boardVO) {
        this.expandable = false;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public String getMsnb_no() {
        return msnb_no;
    }

    public void setMsnb_no(String msnb_no) {
        this.msnb_no = msnb_no;
    }

    public String getMsm_no() {
        return msm_no;
    }

    public void setMsm_no(String msm_no) {
        this.msm_no = msm_no;
    }

    public String getMsnb_subject() {
        return msnb_subject;
    }

    public void setMsnb_subject(String msnb_subject) {
        this.msnb_subject = msnb_subject;
    }

    public String getMsnb_content() {
        return msnb_content;
    }

    public void setMsnb_content(String msnb_content) {
        this.msnb_content = msnb_content;
    }

    public String getMsnb_date() {
        return msnb_date;
    }

    public void setMsnb_date(String msnb_date) {
        this.msnb_date = msnb_date;
    }

    public String getMsnb_hit() {
        return msnb_hit;
    }

    public void setMsnb_hit(String msnb_hit) {
        this.msnb_hit = msnb_hit;
    }

    public String getMsm_name() {
        return msm_name;
    }

    public void setMsm_name(String msm_name) {
        this.msm_name = msm_name;
    }

    @Override
    public String toString() {
        return "BoardVO{" +
                "msnb_no='" + msnb_no + '\'' +
                ", msm_no='" + msm_no + '\'' +
                ", msnb_subject='" + msnb_subject + '\'' +
                ", msnb_content='" + msnb_content + '\'' +
                ", msnb_date='" + msnb_date + '\'' +
                ", msnb_hit='" + msnb_hit + '\'' +
                ", msm_name='" + msm_name + '\'' +
                ", expandable=" + expandable +
                '}';
    }
}
