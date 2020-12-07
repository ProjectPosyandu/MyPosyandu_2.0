package com.example.myposyandu.model;

import java.util.List;

public class ResponseModelIbu {
    private int kode;
    private String pesan;
    private List<ModelDataIbu> data;

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

    public List<ModelDataIbu> getData() {
        return data;
    }

    public void setData(List<ModelDataIbu> data) {
        this.data = data;
    }
}
