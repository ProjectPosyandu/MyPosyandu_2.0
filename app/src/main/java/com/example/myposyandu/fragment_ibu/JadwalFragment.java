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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.myposyandu.adapter.JadwalAdapter;
import com.example.myposyandu.R;
import com.example.myposyandu.SharedPrefManager;
import com.example.myposyandu.helper.ApiService;
import com.example.myposyandu.helper.UtilsApi;
import com.example.myposyandu.model.ModelDataJadwal;
import com.example.myposyandu.model.ResponseModelJadwal;

import java.util.ArrayList;
import java.util.List;

public class JadwalFragment extends Fragment {
    private RecyclerView rvData;
    private RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    private List<ModelDataJadwal> listJadwal = new ArrayList<>();
    SharedPrefManager sharedPrefManager;
    TextView status;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_jadwal, container, false);
        rvData = root.findViewById(R.id.rvJadwalImunisasi1);
        status = root.findViewById(R.id.statusJadwal1);
        lmData = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmData);
        rvData.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        getJadwal("sudah");
        return root;
    }

    public void getJadwal(String key){
        ApiService ardData = UtilsApi.getAPIService();
        Call<ResponseModelJadwal> tampilData = ardData.getJadwal(key);
        tampilData.enqueue(new Callback<ResponseModelJadwal>() {
            @Override
            public void onResponse(Call<ResponseModelJadwal> call, Response<ResponseModelJadwal> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                if(pesan.equals("Data tersedia")){
                    status.setVisibility(View.INVISIBLE);
                    rvData.setVisibility(View.VISIBLE);

                    listJadwal = response.body().getData();
                    adData = new JadwalAdapter(getContext(), listJadwal);
                    rvData.setAdapter(adData);
                    adData.notifyDataSetChanged();

                }else {
                    status.setVisibility(View.VISIBLE);
                    rvData.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onFailure(Call<ResponseModelJadwal> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal menghubungi server", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
