package com.example.myposyandu.model;

import java.util.List;

public class ResponseModelJadwal {
    private int kode;
    private String pesan;
    private List<ModelDataJadwal> data;

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

    public List<ModelDataJadwal> getData() {
        return data;
    }

    public void setData(List<ModelDataJadwal> data) {
        this.data = data;
    }

}
