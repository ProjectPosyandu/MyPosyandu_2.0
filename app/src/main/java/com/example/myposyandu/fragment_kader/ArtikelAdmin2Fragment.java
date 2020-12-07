package com.example.myposyandu.fragment_kader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myposyandu.adapter.ArtikelAdapter;
import com.example.myposyandu.R;
import com.example.myposyandu.SharedPrefManager;
import com.example.myposyandu.helper.ApiService;
import com.example.myposyandu.helper.UtilsApi;
import com.example.myposyandu.model.ModelDataArtikel;
import com.example.myposyandu.model.ResponseModelArtikel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtikelAdmin2Fragment extends Fragment {
    Button tambah;
    private RecyclerView rvData;
    private RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    private List<ModelDataArtikel> listJadwal = new ArrayList<>();
    SharedPrefManager sharedPrefManager;
    TextView status;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_artikel_admin2, container, false);
        tambah  = root.findViewById(R.id.btnTambahArtikelAdmin2);
        rvData = root.findViewById(R.id.rvArtikelAdmin2);
        status = root.findViewById(R.id.statusArtikelAdmin2);
        lmData = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmData);

        getJadwal();

        tambah.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                                new TambahArtikelFragment()).commit();

                    }
                }
        );
        return root;
    }

    public void getJadwal(){
        ApiService ardData = UtilsApi.getAPIService();
        Call<ResponseModelArtikel> tampilData = ardData.getDataArtikel();
        tampilData.enqueue(new Callback<ResponseModelArtikel>() {
            @Override
            public void onResponse(Call<ResponseModelArtikel> call, Response<ResponseModelArtikel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                if(pesan.equals("Data tersedia")){
                    status.setVisibility(View.INVISIBLE);
                    rvData.setVisibility(View.VISIBLE);

                    listJadwal = response.body().getData();
                    adData = new ArtikelAdapter(getContext(), listJadwal);
                    rvData.setAdapter(adData);
                    adData.notifyDataSetChanged();

                }else {
                    status.setVisibility(View.VISIBLE);
                    rvData.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onFailure(Call<ResponseModelArtikel> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal menghubungi server", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    public void sendSmsByManager() {
//        try {
//            // Get the default instance of the SmsManager
//            SmsManager smsManager = SmsManager.getDefault();
//            smsManager.sendTextMessage(nomorTelp.getText().toString(),
//                    null,
//                    isiPesan.getText().toString(),
//                    null,
//                    null);
//            Toast.makeText(getActivity(), "SMS Berhasil Dikirim!",
//                    Toast.LENGTH_LONG).show();
//        } catch (Exception ex) {
//            Toast.makeText(getActivity(),"Pengiriman SMS Gagal...",
//                    Toast.LENGTH_LONG).show();
//            ex.printStackTrace();
//        }
//    }

}
