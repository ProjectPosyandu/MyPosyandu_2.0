package com.example.myposyandu.model;

public class ModelDataIbu {
    private int id;
    private String nama, email, no_telp;

    public ModelDataIbu(int id, String nama, String email, String no_telp) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.no_telp = no_telp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }
}
