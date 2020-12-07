package com.example.myposyandu.fragment_ibu;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myposyandu.R;
import com.example.myposyandu.SharedPrefManager;
import com.example.myposyandu.activity.DetailBayiActivity;
import com.example.myposyandu.activity.Main2Activity;
import com.example.myposyandu.helper.ApiService;
import com.example.myposyandu.helper.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahDataChartFragment extends Fragment {
    EditText etBB, etTB;
    Button btnTambah;
    TextView tgl, cek;

    String tglLahir, tglSekarang, hasil, idBayi;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;


    Context mContext;
    ProgressDialog loading;
    ApiService mApiService;
    SharedPrefManager sharedPrefManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tambah_data_chart, container, false);

        inItComponents(view);
        hitungUsia();

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                sharedPrefManager = new SharedPrefManager(getActivity());
                String id_bayi = sharedPrefManager.getSpIdBayi();
                String usia_bayi = tgl.getText().toString().trim();
                String berat_bayi = etBB.getText().toString().trim();
                String tinggi_bayi = etTB.getText().toString().trim();

                if ( id_bayi.isEmpty() || usia_bayi.isEmpty() || berat_bayi.isEmpty() || tinggi_bayi.isEmpty()){
                    showMessage("Field belum terisi. Mohon lengkapi semua field isian diatas");
                }else {
                    inputData(id_bayi, usia_bayi, berat_bayi, tinggi_bayi);
                }
            }
        });
        return view;
    }

    private void inItComponents(View view){
        mContext  = getContext();
        mApiService = UtilsApi.getAPIService();
        etBB = view.findViewById(R.id.etBerat);
        etTB = view.findViewById(R.id.etTinggi);
        btnTambah = view.findViewById(R.id.btnTambah);
        tgl =view.findViewById(R.id.tgl);
        cek = view.findViewById(R.id.bulan);

        sharedPrefManager = new SharedPrefManager(getActivity());
        idBayi = sharedPrefManager.getSpIdBayi();
    }

    private void hitungUsia() {
        //hitung usia
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar tglIni = Calendar.getInstance();

        sharedPrefManager = new SharedPrefManager(getContext());
        tglLahir = sharedPrefManager.getSpTgl();
        tglSekarang = dateFormatter.format(tglIni.getTime());
        DateFormat dateAwal = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateAkhir = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date tglAwal = dateAwal.parse(tglLahir);
            Date tglAkhir = dateAkhir.parse(tglSekarang);

            Date TGLAwal = tglAwal;
            Date TGLAkhir = tglAkhir;
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(TGLAwal);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(TGLAkhir);

            hasil = String.valueOf(daysBetween(cal1, cal2));

            Log.d("Tanggal Awal",tglLahir);
            Log.d("Tanggal Akhir",tglSekarang);
            Log.d("Selisih",hasil);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        tgl.setText(hasil);

    }

    private void inputData(final String id_bayi, final String usia_bayi, final String berat_bayi, final String tinggi_bayi){
        mApiService.inputDetailBayi(id_bayi, usia_bayi, berat_bayi, tinggi_bayi)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Log.i("debug", "onResponse: BERHASIL");
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("error").equals("false")){
                                    showMessage("Update grafik telah dilakukan");
                                    Intent intent = new Intent(mContext, DetailBayiActivity.class);
                                    getContext().startActivity(intent);
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

    private void showMessage(String message){
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    private static long daysBetween(Calendar tanggalAwal, Calendar tanggalAkhir) {
        long lama = 0;
        Calendar tanggal = (Calendar) tanggalAwal.clone();
        while (tanggal.before(tanggalAkhir)) {
            tanggal.add(Calendar.DAY_OF_MONTH, 1);
            lama++;
        }
        return lama/30;
    }

    @Override
    public void onResume(){
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
//                    startActivity(new Intent(getActivity(), DetailBayiActivity.class));
                    Intent intent = new Intent(getActivity(), DetailBayiActivity.class);
                    intent.putExtra("id_bayi", idBayi);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                    return true;
                }
                return false;
            }
        });
    }
}
