package com.example.myposyandu.fragment_kader;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myposyandu.R;
import com.example.myposyandu.SharedPrefManager;
import com.example.myposyandu.activity.Main2Activity;
import com.example.myposyandu.fragment_bidan.JadwalBidanFragment;
import com.example.myposyandu.fragment_kader.JadwalImunisasiFragment;
import com.example.myposyandu.helper.ApiService;
import com.example.myposyandu.helper.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import androidx.fragment.app.Fragment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahJadwalFragment extends Fragment {
    Button btnTambah;
    EditText etNama;
    ProgressDialog loading;

    Context mContext;
    ApiService mApiService;
    SharedPrefManager sharedPrefManager;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private Button btDatePicker;

    private String Tanggal;
    private String Waktu;

    private Button btTimePicker;
    private TimePickerDialog timePickerDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tambah_jadwal, container, false);

        inItComponents(view);
        sharedPrefManager = new SharedPrefManager(getContext());

        //pilih tanggal
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        btDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        btTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeDialog();
            }
        });


        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                String nama_jadwal = etNama.getText().toString().trim();

                if (nama_jadwal.isEmpty() || Tanggal.isEmpty() || Waktu.isEmpty()) {
                    showMessage("Field belum terisi. Mohon lengkapi semua field isian diatas");
                } else {
                    tambahJadwal(nama_jadwal,Tanggal,Waktu);
                }

            }
        });

        return view;
    }

    private void tambahJadwal(String nama_jadwal, String tanggal, String waktu) {
        mApiService.tambahJadwal(nama_jadwal,tanggal,waktu)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Log.i("debug", "onResponse: BERHASIL");
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("error").equals("false")){
                                    showMessage("Jadwal Imunisasi Telah Disimpan");
                                    getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
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

    private void inItComponents(View view) {
        mContext = getContext();
        mApiService = UtilsApi.getAPIService();

        etNama = view.findViewById(R.id.daNamaTJ);
        btDatePicker = view.findViewById(R.id.btnTanggalTJ);
        btTimePicker = view.findViewById(R.id.btnWaktuTJ);
        btnTambah = view.findViewById(R.id.btnSimpanTJ);
    }

    private void showDateDialog() {

        /**Calendar untuk mendapatkan tanggal sekarang*/
        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /** Set Calendar untuk menampung tanggal yang dipilih */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**Update TextView dengan tanggal yang kita pilih */
                Tanggal = dateFormatter.format(newDate.getTime());
                btDatePicker.setText(Tanggal);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**Tampilkan DatePicker dialog*/
        datePickerDialog.show();
    }

    private void showTimeDialog() {

        Calendar calendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                tvTimeResult.setText("Waktu dipilih = "+hourOfDay+":"+minute);
                Waktu = hourOfDay+":"+minute;
                btTimePicker.setText(Waktu);

            }
        },
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(getContext()));
        timePickerDialog.show();
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
                                new JadwalImunisasiFragment()).commit();
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
                                new JadwalBidanFragment()).commit();
                        return true;
                    }
                    return false;
                }
            });

        }
    }
}
