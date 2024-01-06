package com.oytuntekesin.authenticationapp.dto;

import java.util.EventListener;

public class Glyco {
    public Glyco() {

    }

    private String ID;
    private String ACIKLAMA;
    private String ACLIK_SURESI;
    private String ACLIK_TOKLUK;
    private String KAN_SEKERI;
    private String TARIH;
    private String USER_ID;

    public String getACIKLAMA() {
        return ACIKLAMA;
    }

    public void setACIKLAMA(String ACIKLAMA) {
        this.ACIKLAMA = ACIKLAMA;
    }

    public String getACLIK_SURESI() {
        return ACLIK_SURESI;
    }

    public void setACLIK_SURESI(String ACLIK_SURESI) {
        this.ACLIK_SURESI = ACLIK_SURESI;
    }

    public String getACLIK_TOKLUK() {
        return ACLIK_TOKLUK;
    }

    public void setACLIK_TOKLUK(String ACLIK_TOKLUK) {
        this.ACLIK_TOKLUK = ACLIK_TOKLUK;
    }

    public String getKAN_SEKERI() {
        return KAN_SEKERI;
    }

    public void setKAN_SEKERI(String KAN_SEKERI) {
        this.KAN_SEKERI = KAN_SEKERI;
    }

    public String getTARIH() {
        return TARIH;
    }

    public void setTARIH(String TARIH) {
        this.TARIH = TARIH;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
