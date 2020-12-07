package com.example.myposyandu.fragment_kader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class JadwalImunisasiFragment extends Fragment {
    Button tambah;
    private RecyclerView rvData;
    private RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    private List<ModelDataJadwal> listJadwal = new ArrayList<>();
    SharedPrefManager sharedPrefManager;
    TextView status;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_jadwal_imunisasi, container, false);
        tambah  = root.findViewById(R.id.btnTambahJadwal);
        rvData = root.findViewById(R.id.rvJadwalImunisasi);
        status = root.findViewById(R.id.statusJadwal);
        lmData = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmData);
        rvData.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        getJadwal();

        tambah.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                                new TambahJadwalFragment()).commit();

                    }
                }
        );
        return root;
    }

    public void getJadwal(){
        ApiService ardData = UtilsApi.getAPIService();
        Call<ResponseModelJadwal> tampilData = ardData.getJadwalAll();
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
