package com.example.myposyandu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myposyandu.R;
import com.example.myposyandu.SharedPrefManager;

public class DetailArtikelActivity extends AppCompatActivity {
    TextView tvJudul, tvIsi, tvPenulis;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_artikel);

        tvJudul = findViewById(R.id.tvJudul);
        tvIsi = findViewById(R.id.tvIsi);
        tvPenulis = findViewById(R.id.tvPenulis);
        sharedPrefManager = new SharedPrefManager(this);

        getDetailArtikel();
    }

    private void getDetailArtikel(){
        String judul = getIntent().getStringExtra("judul_artikel");
        String isi = getIntent().getStringExtra("isi_artikel");
        String penulis = getIntent().getStringExtra("penulis");
        setData(judul, isi, penulis);
    }

    private void setData(String judul, String isi, String penulis){
        tvJudul.setText(judul);
        tvIsi.setText(isi);
        tvPenulis.setText(penulis);
    }
}
