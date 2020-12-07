package com.example.myposyandu.model;

public class ModelDataJadwal {
//    private int id_bayi;
    private String id_jadwal,nama_imunisasi,tgl_imunisasi, waktu, status;

    public ModelDataJadwal(String id_jadwal, String nama_imunisasi, String tgl_imunisasi, String waktu, String status) {
        this.id_jadwal = id_jadwal;
        this.nama_imunisasi = nama_imunisasi;
        this.tgl_imunisasi = tgl_imunisasi;
        this.waktu = waktu;
        this.status = status;
    }

    public String getId_jadwal() {
        return id_jadwal;
    }

    public String getNama_imunisasi() {
        return nama_imunisasi;
    }

    public String getTgl_imunisasi() {
        return tgl_imunisasi;
    }

    public String getWaktu() {
        return waktu;
    }

    public String getStatus() {
        return status;
    }

    public void setId_jadwal(String id_jadwal) {
        this.id_jadwal = id_jadwal;
    }

    public void setNama_imunisasi(String nama_imunisasi) {
        this.nama_imunisasi = nama_imunisasi;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public void setTgl_imunisasi(String tgl_imunisasi) {
        this.tgl_imunisasi = tgl_imunisasi;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
