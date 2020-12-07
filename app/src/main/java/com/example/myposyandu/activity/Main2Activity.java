
package com.example.myposyandu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

import com.example.myposyandu.R;
import com.example.myposyandu.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Main2Activity extends AppCompatActivity {
    // halaman pengguna (ibu)
    private AppBarConfiguration mAppBarConfiguration;
    NavigationView navigationView;
    SharedPrefManager sharedPrefManager;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        final TextView namaUser = headerView.findViewById(R.id.namaUser);
        final TextView email = headerView.findViewById(R.id.email);
        final Button logout = headerView.findViewById(R.id.btnLogout);
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
                startActivity(new Intent(Main2Activity.this, LoginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });

        sharedPrefManager = new SharedPrefManager(this);
        String snama = sharedPrefManager.getSPNama();
        String semail = sharedPrefManager.getSPEmail();
        namaUser.setText(snama);
        email.setText(semail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TambahBayiActivity.class));

            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_bayi, R.id.nav_jadwal, R.id.nav_artikel, R.id.nav_tambahBayi)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.keluar){
//            sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
//            sharedPrefManager.saveSPString(sharedPrefManager.SP_ID_BAYI, "");
//            sharedPrefManager.saveSPString(sharedPrefManager.SP_ID, "");
//            sharedPrefManager.saveSPString(sharedPrefManager.SP_NAMA, "");
//            sharedPrefManager.saveSPString(sharedPrefManager.SP_EMAIL, "");
//            sharedPrefManager.saveSPString(sharedPrefManager.SP_LEVEL, "");
//            sharedPrefManager.saveSPString(sharedPrefManager.SP_JK, "");
//            sharedPrefManager.saveSPString(sharedPrefManager.SP_TGL, "");
//            startActivity(new Intent(Main2Activity.this, LoginActivity.class)
//                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
//            finish();
//        }
//        return true;
//    }
}
