package com.example.ciayco.spacevoi;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    //region Tanımlamalar
    UpDownSınıf pool = new UpDownSınıf();
    String loggedUser ;

    HashMap<String, List<String>> Movies_category;
    List<String> Movies_list;
    ExpandableListView Exp_list;
    ListAdapterSınıf adapter;

    //endregion


    //region Ayarlar ve top bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id== R.id.action_settings)
        {
            Intent intent = new Intent(MainActivity.this, AyarlarActivity.class);
            startActivity(intent);

        }
        else if (id==R.id.profil_sayfa)
        {
            Intent intent = new Intent(MainActivity.this, ProfilActivity.class);
            intent.putExtra("USERNAME", loggedUser);
            startActivity(intent);
        }
        else if(id==R.id.upload)
        {
            Intent intent = new Intent(MainActivity.this, UpActivity.class);
            intent.putExtra("USERNAME", loggedUser);
            startActivity(intent);
            finish();
        }
        else if (id==R.id.cikis)
        {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("Kontrol",0);
            editor.apply();
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
        //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pool.Poolcek(getApplicationContext());
        final Animation animRotate = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);

        //region listviewçekme
        Exp_list = (ExpandableListView) findViewById(R.id.poolListView);
        Movies_category = KayıtlarSınıf.getInfoPoolKayıtları(getApplicationContext());
        Movies_list = new ArrayList<>(Movies_category.keySet());
        adapter = new ListAdapterSınıf(this, Movies_category, Movies_list);
        Exp_list.setAdapter(adapter);
        Exp_list.expandGroup(0);
        //endregion

        //region Login
        Intent intent = getIntent();
        Bundle intentBundle = intent.getExtras();
        loggedUser = intentBundle.getString("USERNAME");
        loggedUser = loggedUser.toLowerCase();
        loggedUser = loggedUser.substring(0,1).toUpperCase()+ loggedUser.substring(1);



        //endregion


        //region Butonlar
        ImageButton btnyenile = (ImageButton)findViewById(R.id.yenile);
        btnyenile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                arg0.startAnimation(animRotate);
                Movies_category = KayıtlarSınıf.getInfoPoolKayıtları(getApplicationContext());
                Movies_list = new ArrayList<>(Movies_category.keySet());
                adapter = new ListAdapterSınıf(getApplicationContext(), Movies_category, Movies_list);
                Exp_list.setAdapter(adapter);
                Exp_list.expandGroup(0);
            }
        });

        //endregion

    }





}