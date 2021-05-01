package com.example.my38_locationmap;

import java.io.Serializable;

public class ItemVO implements Serializable {

    String aa = "";
    String bb = "";


    public ItemVO(String aa, String bb) {
        this.aa = aa;
        this.bb = bb;
    }


    @Override
    public String toString() {
        return "ItemVO{" +
                "aa='" + aa + '\'' +
                ", bb='" + bb + '\'' +
                '}';
    }

    public String getAa() {
        return aa;
    }

    public void setAa(String aa) {
        this.aa = aa;
    }

    public String getBb() {
        return bb;
    }

    public void setBb(String bb) {
        this.bb = bb;
    }
}
