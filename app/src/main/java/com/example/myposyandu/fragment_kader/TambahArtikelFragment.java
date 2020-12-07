package com.example.myposyandu.fragment_kader;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myposyandu.R;
import com.example.myposyandu.SharedPrefManager;
import com.example.myposyandu.fragment_bidan.ArtikelBidanFragment;
import com.example.myposyandu.fragment_bidan.JadwalBidanFragment;
import com.example.myposyandu.helper.ApiService;
import com.example.myposyandu.helper.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.fragment.app.Fragment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahArtikelFragment extends Fragment {
    Button btnTambah;
    EditText judul, isi;
    ProgressDialog loading;

    Context mContext;
    ApiService mApiService;
    SharedPrefManager sharedPrefManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tambah_artikel, container, false);

        inItComponents(view);
        sharedPrefManager = new SharedPrefManager(getContext());

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                String penulis = sharedPrefManager.getSPNama();
                String judul_artikel = judul.getText().toString().trim();
                String isi_artikel = isi.getText().toString().trim();

                if (judul_artikel.isEmpty() || isi_artikel.isEmpty() || penulis.isEmpty()) {
                    showMessage("Field belum terisi. Mohon lengkapi semua field isian diatas");
                } else {
                    tambahArtikel(judul_artikel,isi_artikel,penulis);
                }

            }
        });

        return view;
    }

    private void inItComponents(View view) {
        mContext = getContext();
        mApiService = UtilsApi.getAPIService();
        judul = view.findViewById(R.id.etJudulArtikel);
        isi = view.findViewById(R.id.etIsiArtikel);
        btnTambah  =view.findViewById(R.id.btnSimpanArtikel);

    }

    private void tambahArtikel(String judul_artikel, String isi_artikel, String penulis) {
        mApiService.tambahArtikel(judul_artikel,isi_artikel, penulis)
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
                                    getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                                            new ArtikelAdminFragment()).commit();
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

    private void showMessage(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume(){
        super.onResume();
        if (sharedPrefManager.getSPLevel().equals("1")){
            getView().setFocusableInTouchMode(true);
            getView().requestFocus();
            getView().setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                        getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                                new ArtikelAdminFragment()).commit();
                        return true;
                    }
                    return false;
                }
            });
        } else if (sharedPrefManager.getSPLevel().equals("2")){
            getView().setFocusableInTouchMode(true);
            getView().requestFocus();
            getView().setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                        getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                                new ArtikelBidanFragment()).commit();
                        return true;
                    }
                    return false;
                }
            });

        }
    }
}
