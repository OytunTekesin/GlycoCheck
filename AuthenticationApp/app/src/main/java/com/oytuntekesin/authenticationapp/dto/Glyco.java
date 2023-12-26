package com.oytuntekesin.authenticationapp.dto;

public class Glyco {
    public Glyco() {

    }

    private String glikozDegeri;
    private String olcumTuru;
    private String tarih;
    private String aciklama;

    public String getGlikozDegeri() {
        return glikozDegeri;
    }

    public void setGlikozDegeri(String glikozDegeri) {
        this.glikozDegeri = glikozDegeri;
    }

    public String getOlcumTuru() {
        return olcumTuru;
    }

    public void setOlcumTuru(String olcumTuru) {
        this.olcumTuru = olcumTuru;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }
}
