package com.example.myposyandu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.myposyandu.adapter.RecyclerViewAdapter;
import com.example.myposyandu.helper.ApiService;
import com.example.myposyandu.model.ModelDataBayi;
import com.example.myposyandu.helper.UtilsApi;
import com.example.myposyandu.model.ResponseModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CobaActivity extends AppCompatActivity {
    private ArrayList<String> fotoBayi = new ArrayList<>();
    private ArrayList<String> namaBayi = new ArrayList<>();
    private ArrayList<String> detailBayi = new ArrayList<>();
    public FragmentManager f_manager;

    private RecyclerView rvData;
    private RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    private List<ModelDataBayi> listData = new ArrayList<>();

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coba);

        rvData = findViewById(R.id.rvDataBayi);
        lmData = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmData);

        sharedPrefManager = new SharedPrefManager(CobaActivity.this);
        String id = sharedPrefManager.getSpId();

        getDataBayi(id);

//        getDataFromInternet();
    }

//    private void prosesRecyclerViewAdapter(){
//        RecyclerView recyclerView = findViewById(R.id.rvDataBayi);
//        RecyclerViewAdapter adapter = new RecyclerViewAdapter(fotoBayi,namaBayi, detailBayi, this, f_manager);
//
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//    }

//    private void getDataFromInternet(){
//        namaBayi.add("Bayi Satu");
//        fotoBayi.add("https://cdn-brilio-net.akamaized.net/news/2018/03/08/139907/748829-bayi-dikelilingi-bunga-gaba-.jpg");
//        detailBayi.add("Ini detail bayi");
//
//        namaBayi.add("Bayi Dua");
//        fotoBayi.add("https://cdn-brilio-net.akamaized.net/news/2018/03/08/139907/748832-bayi-dikelilingi-bunga-gaba-.jpg");
//        detailBayi.add("Ini detail bayi");
//
//        namaBayi.add("Bayi Tiga");
//        fotoBayi.add("https://cdn-brilio-net.akamaized.net/news/2018/03/08/139907/748833-bayi-dikelilingi-bunga-gaba-.jpg");
//        detailBayi.add("Ini detail bayi");
//
//        prosesRecyclerViewAdapter();
//    }

    public void getDataBayi(String id){

        ApiService ardData = UtilsApi.getAPIService();
        Call<ResponseModel> tampilData = ardData.getDataBayi(id);

        tampilData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(CobaActivity.this, "Kode : "+kode+"Pesan : "+pesan, Toast.LENGTH_SHORT).show();

                listData = response.body().getData();

                adData = new RecyclerViewAdapter(CobaActivity.this, listData);
                rvData.setAdapter(adData);
                adData.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(CobaActivity.this, "Gagal menghubungi server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
