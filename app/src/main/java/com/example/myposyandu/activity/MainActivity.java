package com.example.myposyandu.activity;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.internal.Util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myposyandu.R;
import com.example.myposyandu.SharedPrefManager;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {

    private EditText phoneNumber;
    private EditText smsBody;
    private Button smsManagerBtn;
    private Button smsSendToBtn;
    private Button smsViewBtn;

    NavigationView navigationView;
    Button logout;
    SharedPrefManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        whatsapp(this, "082236685263");

        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        smsBody = (EditText) findViewById(R.id.smsBody);
        smsManagerBtn = (Button) findViewById(R.id.smsManager);
        smsSendToBtn = (Button) findViewById(R.id.smsSIntent);
        smsViewBtn = (Button) findViewById(R.id.smsVIntent);

        doLogout();
        smsManagerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSmsByManager();
            }
        });

        smsSendToBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendSmsBySIntent();
            }
        });

        smsViewBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendSmsByVIntent();
            }
        });
    }

    private void doLogout() {
        sharedPrefManager = new SharedPrefManager(this);
        View headerView = navigationView.getHeaderView(0);
        logout = headerView.findViewById(R.id.btnLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                sharedPrefManager.saveSPString(sharedPrefManager.SP_ID_BAYI, "");
                sharedPrefManager.saveSPString(sharedPrefManager.SP_ID, "");
                sharedPrefManager.saveSPString(sharedPrefManager.SP_NAMA, "");
                sharedPrefManager.saveSPString(sharedPrefManager.SP_EMAIL, "");
                sharedPrefManager.saveSPString(sharedPrefManager.SP_LEVEL, "");
                sharedPrefManager.saveSPString(sharedPrefManager.SP_JK, "");
                sharedPrefManager.saveSPString(sharedPrefManager.SP_TGL, "");
                startActivity(new Intent(MainActivity.this, LoginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });
    }

    @SuppressLint("NewApi")
    public static void whatsapp(Activity activity, String phone) {
        String formattedNumber = Util.format(phone);
        try{
            Intent sendIntent =new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT,"");
            sendIntent.putExtra("jid", formattedNumber +"@s.whatsapp.net");
            sendIntent.setPackage("com.whatsapp");
            activity.startActivity(sendIntent);
        }
        catch(Exception e)
        {
            Toast.makeText(activity,"Error/n"+ e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    public void sendSmsByManager() {
        try {
            // Get the default instance of the SmsManager
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber.getText().toString(),
                    null,
                    smsBody.getText().toString(),
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

    public void sendSmsBySIntent() {
        // add the phone number in the data
        Uri uri = Uri.parse("smsto:" + phoneNumber.getText().toString());

        Intent smsSIntent = new Intent(Intent.ACTION_SENDTO, uri);
        // add the message at the sms_body extra field
        smsSIntent.putExtra("sms_body", smsBody.getText().toString());
        try{
            startActivity(smsSIntent);
        } catch (Exception ex) {
            Toast.makeText(MainActivity.this, "Pengiriman SMS Gagal...",
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    public void sendSmsByVIntent() {

        Intent smsVIntent = new Intent(Intent.ACTION_VIEW);
        // hanya akan membuka aplikasi SMS/MMS default di Android
        smsVIntent.setType("vnd.android-dir/mms-sms");

        // menambahkan nomor telepon dan isi SMS otomatis
        smsVIntent.putExtra("address", phoneNumber.getText().toString());
        smsVIntent.putExtra("sms_body", smsBody.getText().toString());
        try{
            startActivity(smsVIntent);
        } catch (Exception ex) {
            Toast.makeText(MainActivity.this, "Pengiriman SMS Gagal...",
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }

    }

}
