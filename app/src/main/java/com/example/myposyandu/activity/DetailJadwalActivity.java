package com.example.myposyandu.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myposyandu.R;
import com.example.myposyandu.SharedPrefManager;
import com.example.myposyandu.fragment_ibu.ChartFragment;
import com.example.myposyandu.fragment_kader.ArtikelAdminFragment;
import com.example.myposyandu.fragment_kader.JadwalImunisasiFragment;
import com.example.myposyandu.helper.ApiService;
import com.example.myposyandu.helper.UtilsApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailJadwalActivity extends AppCompatActivity {
    TextView tvJudul, tvTgl, tvJam, tvStatus, tvSetSelesai;
    Button kirimPesan, setSelesai;
    SharedPrefManager sharedPrefManager;

    Context mContext;
    ApiService mApiService;
    ProgressDialog loading;

    List<String> no_telp = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_jadwal);

        mContext = this;
        mApiService = UtilsApi.getAPIService();

        tvJudul = findViewById(R.id.djNamaImunisasi);
        tvTgl = findViewById(R.id.djTglImunisasi);
        tvJam = findViewById(R.id.djJamImunisasi);
        tvStatus = findViewById(R.id.djStatus);
        kirimPesan = findViewById(R.id.djKirimPesan);
        setSelesai = findViewById(R.id.djSetSelesai);
        tvSetSelesai = findViewById(R.id.tvSetSelesai);
        sharedPrefManager = new SharedPrefManager(this);

        if (sharedPrefManager.getSPLevel().equals("1") || sharedPrefManager.getSPLevel().equals("2")){ //level 1 = kader (admin)
            kirimPesan.setVisibility(View.VISIBLE);
        }else if (sharedPrefManager.getSPLevel().equals("3")){   //level 3 = ibu bayi
            kirimPesan.setVisibility(View.INVISIBLE);
            setSelesai.setVisibility(View.INVISIBLE);
        }
        getDetailArtikel();
    }

    private void getDetailArtikel(){
        String judul = getIntent().getStringExtra("nama_imunisasi");
        String tanggal = getIntent().getStringExtra("tanggal");
        String jam = getIntent().getStringExtra("waktu");
        String status = getIntent().getStringExtra("status");
        setData(judul, tanggal, jam, status);

        String id_jadwal = getIntent().getStringExtra("id_jadwal");
        getTelpon();

        setSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                requestSelesai(id_jadwal);
            }
        });

        kirimPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sendSmsByManager();
                int i = 0;
                while (i<no_telp.size()){
                    sendSms(no_telp.get(i));
                    i++;
                }
            }
        });
    }

    private void setData(String judul, String tanggal, String jam, String status) {
        tvJudul.setText(judul);
        tvTgl.setText("Tanggal : "+tanggal);
        tvJam.setText("Pukul   : "+jam);
        if (status.equals("belum")){
            tvStatus.setText("Belum Dilakukan");
            setSelesai.setVisibility(View.VISIBLE);
            tvSetSelesai.setVisibility(View.INVISIBLE);
        }else if (status.equals("sudah")){
            tvStatus.setText("Sudah Dilakukan");
            setSelesai.setVisibility(View.INVISIBLE);
            tvSetSelesai.setVisibility(View.VISIBLE);

            if(sharedPrefManager.getSPLevel().equals("3")){
                setSelesai.setVisibility(View.INVISIBLE);
                tvSetSelesai.setVisibility(View.INVISIBLE);

            }
        }
    }

    private void requestSelesai(String id_jadwal) {
        mApiService.setSelesai(id_jadwal)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Log.i("debug", "onResponse: BERHASIL");
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("error").equals("false")){
                                    showMessage("Artikel Telah Disimpan");
                                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                                            new JadwalImunisasiFragment()).commit();
                                } else {
                                    String error_message = jsonRESULTS.getString("error_msg");
                                    showMessage(error_message);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.i("debug", "onResponse: GA BERHASIL");
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        showMessage("Koneksi Internet Bermasalah");
                    }
                });
    }

    private void getTelpon(){
        mApiService.getTelp().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        JSONArray dataArray = jsonRESULTS.getJSONArray("data");

                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject dataobj = dataArray.getJSONObject(i);
                            no_telp.add(dataobj.getString("no_telp"));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
            }
        });

    }
    public void sendSms(String nomor) {
        try {
            // Get the default instance of the SmsManager
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(nomor,
                    null,
                    "Mohon kehadirannya pada tanggal "+getIntent().getStringExtra("tanggal")
                            +", jam "+getIntent().getStringExtra("waktu")+" untuk melakukan imunisasi "
                            +getIntent().getStringExtra("nama_imunisasi")+ ". Terimakasih.",
                    null,
                    null);
            Toast.makeText(getApplicationContext(), "SMS Berhasil Dikirim!",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),"Pengiriman SMS Gagal...",
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    public void sendSmsByManager() {
        try {
            // Get the default instance of the SmsManager
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("082144662301",
                    null,
                    "Mohon kehadirannya pada tanggal "+getIntent().getStringExtra("tanggal")
                            +", jam "+getIntent().getStringExtra("waktu")+" untuk melakukan imunisasi "
                            +getIntent().getStringExtra("nama_imunisasi")+ ". Terimakasih.",
                    null,
                    null);
            Toast.makeText(getApplicationContext(), "SMS Berhasil Dikirim!",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),"Pengiriman SMS Gagal...",
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    private void showMessage(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

}
