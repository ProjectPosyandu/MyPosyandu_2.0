package com.example.myposyandu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myposyandu.R;
import com.example.myposyandu.SharedPrefManager;
import com.example.myposyandu.helper.ApiService;
import com.example.myposyandu.helper.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cyd.awesome.material.AwesomeText;
import cyd.awesome.material.FontCharacterMaps;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText etEmail;
    EditText etPassword;
    AwesomeText ImgShowHidePassword;
    Button btnLogin;
    ProgressDialog loading;

    Context mContext;
    ApiService mApiService;

    SharedPrefManager sharedPrefManager;

    boolean pwd_status = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package apihelper
        initComponents();
        checkSession();
    }

    public void initComponents(){
        sharedPrefManager = new SharedPrefManager(this);

        etEmail = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        ImgShowHidePassword = (AwesomeText)findViewById(R.id.ImgShowPassword); // widget show hide password

        // lalu kita beri action agar show hide password nya bisa berfungsi
        ImgShowHidePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pwd_status) {
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    pwd_status = false;
                    ImgShowHidePassword.setMaterialDesignIcon(FontCharacterMaps.MaterialDesign.MD_VISIBILITY);
                    etPassword.setSelection(etPassword.length());
                } else {
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    pwd_status = true;
                    ImgShowHidePassword.setMaterialDesignIcon(FontCharacterMaps.MaterialDesign.MD_VISIBILITY_OFF);
                    etPassword.setSelection(etPassword.length());
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                requestLogin();
            }
        });
    }

    public void requestLogin(){
        String semail = etEmail.getText().toString();
        String spass = etPassword.getText().toString();
        mApiService.loginRequest(semail, spass)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("error").equals("false")){
                                    // Jika login berhasil maka data nama yang ada di response API
                                    // akan diparsing ke activity selanjutnya.
                                    Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();

                                    String id = jsonRESULTS.getJSONObject("user").getString("id");
                                    String nama = jsonRESULTS.getJSONObject("user").getString("nama");
                                    String email = jsonRESULTS.getJSONObject("user").getString("email");
                                    String level_user = jsonRESULTS.getJSONObject("user").getString("level");

                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_ID, id);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, nama);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_EMAIL, email);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_LEVEL, level_user);
                                    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);

                                    if (level_user.equals("1")){ //level 1 = kader (admin)
                                        startActivity(new Intent(mContext, Main3Activity.class)
                                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                        finish();
                                    }else if (level_user.equals("2")){  //level 2 = bidan
                                        startActivity(new Intent(mContext, Main4Activity.class)
                                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                        finish();
                                    }else if (level_user.equals("3")){   //level 3 = ibu bayi
                                        startActivity(new Intent(mContext, Main2Activity.class)
                                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                        finish();
                                    }else {
                                        startActivity(new Intent(mContext, SplashActivity.class));
                                        finish();
                                    }

//                                    Intent intent = new Intent(mContext, MainActivity.class);
//                                    intent.putExtra("result_nama", nama);
//                                    startActivity(intent);

                                } else {
                                    // Jika login gagal
                                    String error_message = jsonRESULTS.getString("error_msg");
                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
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

    private void checkSession() {
        //melihat session user
        if (sharedPrefManager.getSPSudahLogin()){
            if (sharedPrefManager.getSPLevel().equals("1")){ //level 1 = kader (admin)
                startActivity(new Intent(mContext, Main3Activity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }else if (sharedPrefManager.getSPLevel().equals("2")){  //level 2 = bidan
                startActivity(new Intent(mContext, Main4Activity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }else if (sharedPrefManager.getSPLevel().equals("3")){   //level 3 = ibu bayi
                startActivity(new Intent(mContext, Main2Activity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }else {
                startActivity(new Intent(mContext, LoginActivity.class));
                finish();
            }
        }
    }
}
