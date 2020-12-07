package com.example.myposyandu.fragment_bidan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myposyandu.adapter.DataIbuAdapter;
import com.example.myposyandu.R;
import com.example.myposyandu.helper.ApiService;
import com.example.myposyandu.helper.UtilsApi;
import com.example.myposyandu.model.ModelDataIbu;
import com.example.myposyandu.model.ResponseModelIbu;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataIbuBayiFragment extends Fragment {
    private RecyclerView rvData;
    private RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    private List<ModelDataIbu> listData = new ArrayList<>();
    TextView status;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_data_ibu_bayi, container, false);
        rvData = root.findViewById(R.id.rvDataIbu);
        status = root.findViewById(R.id.status);
        lmData = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmData);

        tampilDataIbu();

        return root;
    }

    public void tampilDataIbu(){
        ApiService ardData = UtilsApi.getAPIService();
        Call<ResponseModelIbu> tampil = ardData.getDataIbu();
        tampil.enqueue(new Callback<ResponseModelIbu>() {
            @Override
            public void onResponse(Call<ResponseModelIbu> call, Response<ResponseModelIbu> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                if(pesan.equals("Data tersedia")){
                    status.setVisibility(View.INVISIBLE);
                    rvData.setVisibility(View.VISIBLE);

                    listData = response.body().getData();
                    adData = new DataIbuAdapter(getContext(), listData);
                    rvData.setAdapter(adData);
                    adData.notifyDataSetChanged();

                }else {
                    status.setVisibility(View.VISIBLE);
                    rvData.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onFailure(Call<ResponseModelIbu> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal menghubungi server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
