package com.example.ciayco.spacevoi;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Date;


public class UpActivity extends AppCompatActivity {

    //region Tanımlamalar
    UpDownSınıf us = new UpDownSınıf();
    KaydetCalSınıf ka = new KaydetCalSınıf();
    static String loggedUser;
    static String kayitkodu;
    //endregion
    private String isim_Text = "";
    private Button test;
    private LayoutInflater layoutInflater;
    private PopupWindow popupWindow;
    private RelativeLayout relativeLayout;



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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.up_activity);

        test = (Button) findViewById(R.id.popbutton);
        relativeLayout = (RelativeLayout) findViewById(R.id.uploadlayout);


                layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.pop_window, null);


                popupWindow = new PopupWindow(container,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT,true);

                container.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });




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



        //region Butonlar


        //Kaydet Click
        final ImageButton kayit = (ImageButton)findViewById(R.id.kaydetbut);

        kayit.setOnClickListener(new OnClickListener() {
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
                    popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);
                    kayit.setActivated(false);
                    recText.setText("Kayıt için tıklayın..");
                }
            }
        });


        //Gönder Buton
        final Button gonder = (Button)container.findViewById(R.id.gonderBut);
        gonder.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               us.DosyaGonder(getApplicationContext(), loggedUser, kayitkodu);
            }
        });
        //Sil Buton
        final Button sil = (Button)container.findViewById(R.id.silBut);
        sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ka.sil(kayitkodu, getApplicationContext());
            }
        });

        //İsim değiştir buton
        final Button isimd = (Button)container.findViewById(R.id.isimdBut);
        isimd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               isimDegistir();
            }
        });


        //endregion

    }

    public void isimDegistir(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Yeni kayıt adı");


        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                isim_Text = input.getText().toString();
                ka.isimdegistir(kayitkodu, isim_Text, getApplicationContext());
                kayitkodu = isim_Text;
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }
}
