package com.example.myposyandu.fragment_kader;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myposyandu.R;
import com.example.myposyandu.SharedPrefManager;
import com.example.myposyandu.fragment_bidan.ArtikelBidanFragment;
import com.example.myposyandu.fragment_bidan.JadwalBidanFragment;
import com.example.myposyandu.fragment_ibu.ArtikelFragment;
import com.example.myposyandu.helper.ApiService;
import com.example.myposyandu.helper.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.fragment.app.Fragment;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

public class TambahArtikelFragment extends Fragment {
    Button btnTambah;
    EditText judul, isi;
    ProgressDialog loading;

    Context mContext;
    ApiService mApiService;
    SharedPrefManager sharedPrefManager;

    //inisialisasi diana
    TextView uploadPdf;
    private String url = "";
    private String path = "";
    private static final int BUFFER_SIZE = 1024 * 2;
    private static final String IMAGE_DIRECTORY = "/myposyandu";

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
//                    tambahArtikel(judul_artikel,isi_artikel,penulis);
                    uploadArtikel(penulis,judul_artikel,path);
                }

            }
        });

        //klik textview dbg button pilih pdf diana
        pilihArtikel();
        return view;
    }

    private void inItComponents(View view) {
        mContext = getContext();
        mApiService = UtilsApi.getAPIService();
        judul = view.findViewById(R.id.etJudulArtikel);
        isi = view.findViewById(R.id.etIsiArtikel);
        btnTambah  =view.findViewById(R.id.btnSimpanArtikel);
        // diana
        //uploadPdf = view.findViewById(R.id.);

    }

    //fungsi tambah artikel dari textbox
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

    //fungsi untuk upload artikel dalam pdf diana
    private void uploadArtikel(String id, String judul_artikel, String path) {
        String pdfname = String.valueOf(Calendar.getInstance().getTimeInMillis());

        //Create a file object using file path
        File file = new File(path);
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("filename", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), pdfname);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        ApiService getResponse = retrofit.create(ApiService.class);
        Call<ResponseBody> call = getResponse.uploadArtikel(
                fileToUpload, filename, id, judul_artikel);
        Log.d("assss","asss");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Log.i("debug", "onResponse: BERHASIL");
                    loading.dismiss();
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        if (jsonRESULTS.getString("error").equals("false")){
                            showMessage("BERHASIL UPLOAD ARTIKEL");
                            String id = jsonRESULTS.getJSONObject("user").getString("id");
                            if (id != null){
                                // intent ke artikel fragment, perlu filter level user diana
                                if (sharedPrefManager.getSPLevel().equals("1")){
                                    getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                                            new ArtikelAdminFragment()).commit();
                                }else if(sharedPrefManager.getSPLevel().equals("2")){
                                    getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                                            new ArtikelBidanFragment()).commit();
                                }else if(sharedPrefManager.getSPLevel().equals("3")){
                                    getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                                            new ArtikelFragment()).commit();
                                }
                                getActivity().finish();
                            }
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

    //fungsi2 (6 ke bawah) yang tak tambahkan buat pilih pdf yg mau diupload diana
    public void pilihArtikel(){
        uploadPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent,1);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Get the Uri of the selected file
            Uri uri = data.getData();
            String uriString = uri.toString();
            File myFile = new File(uriString);

            String fileName = getFileName(uri);
            uploadPdf.setText(fileName);

            path = getFilePathFromURI(getActivity(),uri);
            Log.d("ioooo",path);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static String getFilePathFromURI(Context context, Uri contentUri) {
        //copy file and send new file path
        String fileName = getFileName(contentUri);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }
        if (!TextUtils.isEmpty(fileName)) {
            File copyFile = new File(wallpaperDirectory + File.separator + fileName);
            // create folder if not exists
            copy(context, contentUri, copyFile);
            return copyFile.getAbsolutePath();
        }
        return null;
    }

    public static String getFileName(Uri uri) {
        if (uri == null) return null;
        String fileName = null;
        String path = uri.getPath();
        int cut = path.lastIndexOf('/');
        if (cut != -1) {
            fileName = path.substring(cut + 1);
        }
        return fileName;
    }

    public static void copy(Context context, Uri srcUri, File dstFile) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStream == null) return;
            OutputStream outputStream = new FileOutputStream(dstFile);
            copystream(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int copystream(InputStream input, OutputStream output) throws Exception, IOException {
        byte[] buffer = new byte[BUFFER_SIZE];

        BufferedInputStream in = new BufferedInputStream(input, BUFFER_SIZE);
        BufferedOutputStream out = new BufferedOutputStream(output, BUFFER_SIZE);
        int count = 0, n = 0;
        try {
            while ((n = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
                out.write(buffer, 0, n);
                count += n;
            }
            out.flush();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
            try {
                in.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
        }
        return count;
    }
}
