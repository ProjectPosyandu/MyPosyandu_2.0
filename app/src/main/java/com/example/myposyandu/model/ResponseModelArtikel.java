package com.example.myposyandu.model;

import java.util.List;

public class ResponseModelArtikel {
    private int kode;
    private String pesan;
    private List<ModelDataArtikel> data;

    public int getKode() {
        return kode;
    }

    public void setKode(int kode) {
        this.kode = kode;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public List<ModelDataArtikel> getData() {
        return data;
    }

    public void setData(List<ModelDataArtikel> data) {
        this.data = data;
    }
}
