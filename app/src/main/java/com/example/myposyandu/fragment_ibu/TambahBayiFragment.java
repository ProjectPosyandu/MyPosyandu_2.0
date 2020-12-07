package com.example.myposyandu.fragment_ibu;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myposyandu.R;
import com.example.myposyandu.SharedPrefManager;
import com.example.myposyandu.helper.ApiService;
import com.example.myposyandu.helper.UtilsApi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TambahBayiFragment extends Fragment {
    Button btnTambah;
    EditText etNama;
    TextView tvId, tvTgl;

    ProgressDialog loading;

    Context mContext;
    ApiService mApiService;
    SharedPrefManager sharedPrefManager;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private Button btDatePicker;

    RadioGroup rgGender;
    RadioButton cowok, cewek;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tambah_bayi, container, false);

        inItComponents(view);
        sharedPrefManager = new SharedPrefManager(getContext());
        String id = sharedPrefManager.getSpId();
        tvId.setText(id);

        //pilih tanggal
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        btDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });


        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                String nama_bayi = etNama.getText().toString().trim();
                String tgl_lahir = tvTgl.getText().toString().trim();
                String jenis_kelamin = null;
                String id = sharedPrefManager.getSpId();

                if (cowok.isChecked()) {
                    jenis_kelamin = "Laki-Laki";
                } else if (cewek.isChecked()) {
                    jenis_kelamin = "Perempuan";
                } else {
                    Toast.makeText(getContext(), "Tidak Ada Yang Dipilih", Toast.LENGTH_SHORT).show();
                }

                if (nama_bayi.isEmpty() || tgl_lahir.isEmpty() || jenis_kelamin.isEmpty()) {
                    showMessage("Field belum terisi. Mohon lengkapi semua field isian diatas");
                } else {
//                    inputBayi(nama_bayi, tgl_lahir, jenis_kelamin, id);
                }

            }
        });

        return view;
    }

    private void inItComponents(View view) {
        mContext = getContext();
        mApiService = UtilsApi.getAPIService();

        etNama = view.findViewById(R.id.daNama);
        btDatePicker = (Button) view.findViewById(R.id.btnTanggal);
        rgGender = (RadioGroup) view.findViewById(R.id.rbJenis);
        cowok = (RadioButton) view.findViewById(R.id.rbCowok);
        cewek = (RadioButton) view.findViewById(R.id.rbCewek);
        tvId = view.findViewById(R.id.tvId);
        tvTgl = view.findViewById(R.id.tvTgl);

        btnTambah = view.findViewById(R.id.btnTambah);
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
                String tgl = dateFormatter.format(newDate.getTime());
                btDatePicker.setText(tgl);
                tvTgl.setText(tgl);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**Tampilkan DatePicker dialog*/
        datePickerDialog.show();
    }


    private void showMessage(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }
}
