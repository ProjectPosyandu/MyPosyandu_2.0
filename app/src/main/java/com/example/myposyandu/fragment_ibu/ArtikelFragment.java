package com.example.myposyandu.fragment_ibu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myposyandu.adapter.ArtikelAdapter;
import com.example.myposyandu.R;
import com.example.myposyandu.helper.ApiService;
import com.example.myposyandu.helper.UtilsApi;
import com.example.myposyandu.model.ModelDataArtikel;
import com.example.myposyandu.model.ResponseModelArtikel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtikelFragment extends Fragment {
    private RecyclerView rvArtikel;
    private RecyclerView.Adapter adArtikel;
    private RecyclerView.LayoutManager lmArtikel;
    private List<ModelDataArtikel> listData = new ArrayList<>();
    TextView status;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_artikel, container, false);
        rvArtikel = root.findViewById(R.id.rvArtikel);
        status = root.findViewById(R.id.statusArtikel);
        lmArtikel = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvArtikel.setLayoutManager(lmArtikel);
        rvArtikel.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        getDataArtikel();

        return root;
    }

    public void getDataArtikel(){
        ApiService ardArtikel = UtilsApi.getAPIService();
        Call<ResponseModelArtikel> tampilArtikel = ardArtikel.getDataArtikel();
        tampilArtikel.enqueue(new Callback<ResponseModelArtikel>() {
            @Override
            public void onResponse(Call<ResponseModelArtikel> call, Response<ResponseModelArtikel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                if(pesan.equals("Data tersedia")){
                    status.setVisibility(View.INVISIBLE);
                    rvArtikel.setVisibility(View.VISIBLE);

                    listData = response.body().getData();
                    adArtikel = new ArtikelAdapter(getContext(), listData);
                    rvArtikel.setAdapter(adArtikel);
                    adArtikel.notifyDataSetChanged();
                }else {
                    status.setVisibility(View.VISIBLE);
                    rvArtikel.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseModelArtikel> call, Throwable t) {

            }
        });

    }

}
