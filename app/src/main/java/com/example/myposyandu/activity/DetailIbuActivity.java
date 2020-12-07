package com.example.myposyandu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myposyandu.R;
import com.example.myposyandu.SharedPrefManager;
import com.example.myposyandu.adapter.RecyclerViewAdapter;
import com.example.myposyandu.fragment_bidan.DetailIbuFragment;
import com.example.myposyandu.fragment_ibu.DetailBayiFragment;
import com.example.myposyandu.helper.ApiService;
import com.example.myposyandu.helper.UtilsApi;
import com.example.myposyandu.model.ModelDataBayi;
import com.example.myposyandu.model.ResponseModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailIbuActivity extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ibu);

        sharedPrefManager = new SharedPrefManager(this);

        getIncomingExtra();
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,
                    new DetailIbuFragment()).commit();
        }

    }

    private void getIncomingExtra(){
        String id = getIntent().getStringExtra("id");
        sharedPrefManager.saveSPString(sharedPrefManager.SP_ID_IBU, id);

    }

}
