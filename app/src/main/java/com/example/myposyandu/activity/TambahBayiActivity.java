package com.example.myposyandu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myposyandu.R;
import com.example.myposyandu.SharedPrefManager;
import com.example.myposyandu.activity.Main2Activity;
import com.example.myposyandu.helper.ApiService;
import com.example.myposyandu.helper.UtilsApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahBayiActivity extends AppCompatActivity {
    Button btnTambah;
    EditText etNama, etBB, etTB;
    TextView tvId, tvTgl;
    ProgressDialog loading;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private Button btDatePicker;

    RadioGroup rgGender;
    RadioButton cowok, cewek;

    private CircleImageView mPicture;
    FloatingActionButton mFab;
    Bitmap bitmap;

    Context mContext;
    ApiService mApiService;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_bayi);

        inItComponents();
        sharedPrefManager = new SharedPrefManager(this);
        String id = sharedPrefManager.getSpId();
        tvId.setText(id);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }

        });

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
                String foto_bayi = getStringImage(bitmap).trim();
                String nama_bayi = etNama.getText().toString().trim();
                String tgl_lahir = tvTgl.getText().toString().trim();
                String jenis_kelamin = null;
                String berat_bayi = etBB.getText().toString().trim();
                String tinggi_bayi = etTB.getText().toString().trim();
                String id = sharedPrefManager.getSpId();

                if(cowok.isChecked()){
                    jenis_kelamin = "Laki-Laki";
                }else if(cewek.isChecked()){
                    jenis_kelamin = "Perempuan";
                }else{
                    Toast.makeText(getApplicationContext(), "Tidak Ada Yang Dipilih", Toast.LENGTH_SHORT).show();
                }

//                Toast.makeText(mContext,"Clicked "+jenis_kelamin+" "+tgl_lahir, Toast.LENGTH_SHORT).show();

                if ( nama_bayi.isEmpty() || tgl_lahir.isEmpty() || jenis_kelamin.isEmpty() || berat_bayi.isEmpty() || tinggi_bayi.isEmpty()){
                    showMessage("Field belum terisi. Mohon lengkapi semua field isian diatas");
                }else {
                    inputBayi(nama_bayi,tgl_lahir,jenis_kelamin,foto_bayi,id,berat_bayi,tinggi_bayi);
                }

            }
        });
    }

    private void inItComponents() {
        mContext = this;
        mApiService = UtilsApi.getAPIService();

        etNama = findViewById(R.id.daNama);
        btDatePicker = (Button) findViewById(R.id.btnTanggal);
        rgGender = (RadioGroup) findViewById(R.id.rbJenis);
        cowok = (RadioButton) findViewById(R.id.rbCowok);
        cewek = (RadioButton) findViewById(R.id.rbCewek);
        etBB = findViewById(R.id.daBerat);
        etTB = findViewById(R.id.daTinggi);
        tvId = findViewById(R.id.tvId);
        tvTgl = findViewById(R.id.tvTgl);
        mPicture = findViewById(R.id.regFoto);
        mFab = findViewById(R.id.fabChoosePic);

        btnTambah = findViewById(R.id.btnTambah);
    }

    private void showDateDialog(){

        /**Calendar untuk mendapatkan tanggal sekarang*/
        Calendar newCalendar = Calendar.getInstance();

        /**Initiate DatePicker dialog*/
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 * Set Calendar untuk menampung tanggal yang dipilih */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**Update TextView dengan tanggal yang kita pilih */
                String tgl = dateFormatter.format(newDate.getTime());
                btDatePicker.setText(tgl);
                tvTgl.setText(tgl);
//                String tgl_lahir = dateFormatter.format(newDate.getTime());
//                Toast.makeText(mContext, "Tanggal"+tgl_lahir, Toast.LENGTH_SHORT).show();
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**Tampilkan DatePicker dialog*/
        datePickerDialog.show();
    }

    private void inputBayi(final String nama_bayi, final String tgl_lahir,
                           final String jenis_kelamin, final String foto_bayi,
                           final String id, final String berat_bayi, final String tinggi_bayi) {

        mApiService.inputBayiRequest(nama_bayi,tgl_lahir,jenis_kelamin, foto_bayi, id, berat_bayi, tinggi_bayi)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Log.i("debug", "onResponse: BERHASIL");
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("error").equals("false")){
                                    showMessage("Data Bayi telah ditambahkan");
                                    startActivity(new Intent(mContext, Main2Activity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
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

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                mPicture.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void chooseFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }



    private void showMessage(String message){
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

}
