package com.example.myposyandu.fragment_ibu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.myposyandu.R;
import com.example.myposyandu.SharedPrefManager;
import com.example.myposyandu.helper.ApiService;
import com.example.myposyandu.helper.UtilsApi;
import com.example.myposyandu.model.ModelDataBayi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class DetailBayiFragment extends Fragment {
    ImageView ifotoBayi;
    TextView tvNamaBayi, tvJK, tvTgl;
    Button btnChart, btnUpdate;
    SharedPrefManager sharedPrefManager;
    Context context;
    ApiService mApiService;
    ProgressDialog loading;

    private List<ModelDataBayi> ListData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_bayi, container, false);

        context = getActivity();
        mApiService = UtilsApi.getAPIService();

        sharedPrefManager = new SharedPrefManager(getActivity());

        ifotoBayi = view.findViewById(R.id.ifotoBayi);
        tvNamaBayi = view.findViewById(R.id.tvNamaBayi);
        tvJK = view.findViewById(R.id.tvJenisKelamin);
        tvTgl = view.findViewById(R.id.tvTglLahir);
        btnChart = view.findViewById(R.id.btnChart);
        btnUpdate = view.findViewById(R.id.btnUpdateChart);

        String id_bayi = sharedPrefManager.getSpIdBayi();
        loading = ProgressDialog.show(context, null, "Harap Tunggu...", true, false);
        getData(id_bayi);

        btnChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container_fragment,
                        new ChartFragment()).commit();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container_fragment,
                        new TambahDataChartFragment()).commit();
            }
        });

        return view;
    }

    private void getData(String id){
        mApiService.getDataPerBayi(id)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("error").equals("false")){
                                    // Jika login berhasil maka data nama yang ada di response API
                                    // akan diparsing ke activity selanjutnya

                                    String id_bayi = jsonRESULTS.getJSONObject("user").getString("id_bayi");
                                    String nama_bayi = jsonRESULTS.getJSONObject("user").getString("nama_bayi");
                                    String jk = jsonRESULTS.getJSONObject("user").getString("jenis_kelamin");
                                    String tgl_lahir = jsonRESULTS.getJSONObject("user").getString("tgl_lahir");
                                    String foto_bayi = jsonRESULTS.getJSONObject("user").getString("foto_bayi");

                                    RequestOptions requestOptions = new RequestOptions();
                                    requestOptions.skipMemoryCache(true);
                                    requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
                                    requestOptions.placeholder(R.mipmap.ic_launcher_round);
                                    requestOptions.error(R.mipmap.ic_launcher_round);
                                    Glide.with(context)
                                            .load(UtilsApi.BASE_URL_API+foto_bayi)
                                            .apply(requestOptions)
                                            .into(ifotoBayi);

                                    sharedPrefManager.saveSPString(sharedPrefManager.SP_ID_BAYI, id_bayi);
                                    sharedPrefManager.saveSPString(sharedPrefManager.SP_TGL, tgl_lahir);
                                    sharedPrefManager.saveSPString(sharedPrefManager.SP_JK, jk);
                                    setDataActivity(nama_bayi, jk, tgl_lahir);

                                } else {
                                    // Jika login gagal
                                    String error_message = jsonRESULTS.getString("error_msg");
                                    showMessage(error_message);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
                    }
                });
//        String fotoBayi = getIntent().getStringExtra("foto_bayi");


    }

    private void setDataActivity(String snamaBayi, String jenis, String tglLahir){
//        Glide.with(this).asBitmap().load(fotoBayi).into(ifotoBayi);
        tvNamaBayi.setText(snamaBayi);
        tvJK.setText(jenis);
        tvTgl.setText(tglLahir);
    }

    private void showMessage(String message) {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
