package com.example.myposyandu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myposyandu.R;
import com.example.myposyandu.SharedPrefManager;
import com.example.myposyandu.fragment_ibu.ChartFragment;
import com.example.myposyandu.fragment_ibu.DetailBayiFragment;

public class DetailBayiActivity extends AppCompatActivity {
    ImageView ifotoBayi;
    TextView tvNamaBayi, tvJK, tvTgl;
    Button btnChart, btnUpdate;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bayi);

        sharedPrefManager = new SharedPrefManager(this);

        getIncomingExtra();
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,
                    new DetailBayiFragment()).commit();
        }

    }

    private void getIncomingExtra(){
        String id_bayi = getIntent().getStringExtra("id_bayi");
        sharedPrefManager.saveSPString(sharedPrefManager.SP_ID_BAYI, id_bayi);

    }


}
