package com.example.myposyandu.fragment_ibu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myposyandu.R;
import com.example.myposyandu.adapter.RecyclerViewAdapter;
import com.example.myposyandu.SharedPrefManager;
import com.example.myposyandu.helper.ApiService;
import com.example.myposyandu.helper.UtilsApi;
import com.example.myposyandu.model.ModelDataBayi;
import com.example.myposyandu.model.ResponseModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataBayiFragment extends Fragment {
    private RecyclerView rvData;
    private RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    private List<ModelDataBayi> listData = new ArrayList<>();
    TextView status;

    SharedPrefManager sharedPrefManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_data_bayi, container, false);
        rvData = root.findViewById(R.id.rvDataBayi);
        status = root.findViewById(R.id.status);
        lmData = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmData);
        rvData.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        sharedPrefManager = new SharedPrefManager(getActivity());
        String id = sharedPrefManager.getSpId();

        getDataBayi(id);

        return root;
    }

    public void getDataBayi(String id){
        ApiService ardData = UtilsApi.getAPIService();
        Call<ResponseModel> tampilData = ardData.getDataBayi(id);
        tampilData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                if(pesan.equals("Data tersedia")){
                    status.setVisibility(View.INVISIBLE);
                    rvData.setVisibility(View.VISIBLE);

                    listData = response.body().getData();
                    adData = new RecyclerViewAdapter(getContext(), listData);
                    rvData.setAdapter(adData);
                    adData.notifyDataSetChanged();

                }else {
                    status.setVisibility(View.VISIBLE);
                    rvData.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal menghubungi server", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
