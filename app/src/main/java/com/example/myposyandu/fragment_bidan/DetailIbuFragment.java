package com.example.myposyandu.fragment_bidan;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.myposyandu.R;
import com.example.myposyandu.SharedPrefManager;
import com.example.myposyandu.activity.DetailIbuActivity;
import com.example.myposyandu.adapter.RecyclerViewAdapter;
import com.example.myposyandu.fragment_kader.ArtikelAdminFragment;
import com.example.myposyandu.fragment_kader.DataIbuFragment;
import com.example.myposyandu.helper.ApiService;
import com.example.myposyandu.helper.UtilsApi;
import com.example.myposyandu.model.ModelDataBayi;
import com.example.myposyandu.model.ResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailIbuFragment extends Fragment {
    private RecyclerView rvData;
    private RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    private List<ModelDataBayi> listData = new ArrayList<>();
    TextView status;

    TextView tvNama, tvUsername, tvTelp;
    SharedPrefManager sharedPrefManager;
    Context context;
    ApiService mApiService;
    ProgressDialog loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_ibu, container, false);

        tvNama = view.findViewById(R.id.tvNama);
        tvUsername = view.findViewById(R.id.tvUsername);
        tvTelp = view.findViewById(R.id.tvTelp);
        sharedPrefManager = new SharedPrefManager(getActivity());
        context = getActivity();
        mApiService = UtilsApi.getAPIService();

        rvData = view.findViewById(R.id.rvDataBayi);
        status = view.findViewById(R.id.status);
        lmData = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmData);
        rvData.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        String id = sharedPrefManager.getSpIdIbu();
        loading = ProgressDialog.show(context, null, "Harap Tunggu...", true, false);

        getDetailIbu(id);
        getDataBayi(id);

        return view;
    }

    private void getDetailIbu(String id){
        mApiService.getDataPerIbu(id)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("error").equals("false")){

//                                    int id = jsonRESULTS.getJSONObject("user").getInt("id");
                                    String nama = jsonRESULTS.getJSONObject("user").getString("nama");
                                    String username = jsonRESULTS.getJSONObject("user").getString("email");
                                    String no_telp = jsonRESULTS.getJSONObject("user").getString("no_telp");
//                                    sharedPrefManager.saveSPInt(sharedPrefManager.SP_ID_IBU, id);
                                    setDataActivity(nama, username, no_telp);

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
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
                    }
                });
    }


    private void setDataActivity(String nama, String username, String no_telp) {
        tvNama.setText(nama);
        tvUsername.setText(username);
        tvTelp.setText(no_telp);
    }

    public void getDataBayi(String id){
        ApiService ardData = UtilsApi.getAPIService();
        Call<ResponseModel> tampilData = ardData.getDataBayi(id);
        tampilData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                if(pesan.equals("Data tersedia")){
                    status.setVisibility(View.INVISIBLE);
                    rvData.setVisibility(View.VISIBLE);

                    listData = response.body().getData();
                    adData = new RecyclerViewAdapter(getContext(), listData);
                    rvData.setAdapter(adData);
                    adData.notifyDataSetChanged();

                }else {
                    status.setVisibility(View.VISIBLE);
                    rvData.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(), "Gagal menghubungi server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showMessage(String message) {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sharedPrefManager.getSPLevel().equals("1")) {
            getView().setFocusableInTouchMode(true);
            getView().requestFocus();
            getView().setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                        getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                                new DataIbuFragment()).commit();
                        return true;
                    }
                    return false;
                }
            });
        } else if (sharedPrefManager.getSPLevel().equals("2")) {
            getView().setFocusableInTouchMode(true);
            getView().requestFocus();
            getView().setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                        getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                                new DataIbuBayiFragment()).commit();
                        return true;
                    }
                    return false;
                }
            });

        }
    }
}
