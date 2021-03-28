package com.example.myposyandu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myposyandu.DownloadTask;
import com.example.myposyandu.R;
import com.example.myposyandu.SharedPrefManager;

public class DetailArtikelActivity extends AppCompatActivity {
    TextView tvJudul, tvIsi, tvPenulis;
    SharedPrefManager sharedPrefManager;

    // diana
    Button buka;
    String URL = "http://192.168.43.36/inka/dokumen/DokumenPernyataan.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_artikel);

        tvJudul = findViewById(R.id.tvJudul);
        tvIsi = findViewById(R.id.tvIsi);
        tvPenulis = findViewById(R.id.tvPenulis);
        sharedPrefManager = new SharedPrefManager(this);

        getDetailArtikel();
        //button untuk downlaod pdf diana
        //buka = findViewById(R.id.);
        buka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadTask(DetailArtikelActivity.this, URL);
            }
        });

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
