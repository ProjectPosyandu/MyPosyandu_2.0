package com.example.myposyandu.model;

public class ModelDataArtikel {
    private String id_artikel;
    private String judul_artikel, isi_artikel, penulis;

    public ModelDataArtikel(String id_artikel, String judul_artikel, String isi_artikel, String penulis) {
        this.id_artikel = id_artikel;
        this.judul_artikel = judul_artikel;
        this.isi_artikel = isi_artikel;
        this.penulis = penulis;
    }

    public String getId_artikel() {
        return id_artikel;
    }

    public void setId_artikel(String id_artikel) {
        this.id_artikel = id_artikel;
    }

    public String getJudul_artikel() {
        return judul_artikel;
    }

    public void setJudul_artikel(String judul_artikel) {
        this.judul_artikel = judul_artikel;
    }

    public String getIsi_artikel() {
        return isi_artikel;
    }

    public void setIsi_artikel(String isi_artikel) {
        this.isi_artikel = isi_artikel;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }
}
