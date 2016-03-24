package com.example.ciayco.spacevoi;


import android.content.Intent;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Date;


public class UpActivity extends AppCompatActivity {

    //region Tanımlamalar
    UpDownSınıf us = new UpDownSınıf();
    KaydetCalSınıf ka = new KaydetCalSınıf();
    static String loggedUser;
    static String kayitkodu;



    //endregion


    //region Ayarlar ve bar menu

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
            Intent intent = new Intent(UpActivity.this, AyarlarActivity.class);
            startActivity(intent);

        }
        else if (id==R.id.profil_sayfa)
        {
            Intent intent = new Intent(UpActivity.this, ProfilActivity.class);
            intent.putExtra("USERNAME", loggedUser);
            startActivity(intent);
        }
        else if(id==R.id.mainmenu)
        {
            Intent intent = new Intent(UpActivity.this, MainActivity.class);
            intent.putExtra("USERNAME", loggedUser);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
//endregion


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.up_activity);


        //region Login
        Intent intent = getIntent();
        Bundle intentBundle = intent.getExtras();
        loggedUser = intentBundle.getString("USERNAME");
        loggedUser = loggedUser.toLowerCase();
        loggedUser = loggedUser.substring(0,1).toUpperCase()+ loggedUser.substring(1);

        final TextView loginUsername = (TextView)findViewById(R.id.login_user);
        final TextView recText = (TextView)findViewById(R.id.recText);
        loginUsername.setText("Hoşgeldin " + loggedUser);

        //endregion

        //region UploadPopUp
        Button b = (Button) findViewById(R.id.popbutton);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpActivity.this,PopUpActivity.class));
            }

        });

        //endregion


        //region Butonlar


        //Kaydet Click
        final ImageButton kayit = (ImageButton)findViewById(R.id.kaydetbut);

        kayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ka.kayitkontrol()) {
                    Date zaman = new Date();
                    String damga = Long.toString(zaman.getTime());
                    kayitkodu = loggedUser + damga;
                    ka.startRecording(kayitkodu,getApplicationContext());
                    kayit.setActivated(true);
                    recText.setText("Kaydediliyor");
                } else {
                    ka.stopRecording();
                    kayit.setActivated(false);
                    recText.setText("Kayıt için tıklayın..");
                }
            }
        });


        //Gönder Buton
        final Button gonder = (Button)findViewById(R.id.gonderbut);
        gonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               us.DosyaGonder(getApplicationContext(), loggedUser, kayitkodu);
            }
        });





        //endregion

    }
}
