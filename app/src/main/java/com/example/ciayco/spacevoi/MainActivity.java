package com.example.ciayco.spacevoi;


import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;

import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;




public class MainActivity extends AppCompatActivity {


    //region Tanımlamalar
    Upload us = new Upload();
    SesKayit ka = new SesKayit();
    static String loggedUser;
    static String kayitkodu;
    static String link;


    public GoogleApiClient client;

    HashMap<String, List<String>> Movies_category;
    List<String> Movies_list;
    ExpandableListView Exp_list;
    MoviesAdapter adapter;

    //endregion

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.ana_bar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.baslik);
        mTitleTextView.setText("SpaceVoi");




        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);

        //region listviewçekme
        Exp_list = (ExpandableListView) findViewById(R.id.exp_list);
        Movies_category = DataProvider.getInfo();
        Movies_list = new ArrayList<>(Movies_category.keySet());
        adapter = new MoviesAdapter(this, Movies_category, Movies_list);
        Exp_list.setAdapter(adapter);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        //endregion


        //region Login
        Intent intent = getIntent();
        Bundle intentBundle = intent.getExtras();
        loggedUser = intentBundle.getString("USERNAME");
        loggedUser = loggedUser.toLowerCase();
        loggedUser = loggedUser.substring(0,1).toUpperCase()+ loggedUser.substring(1);

        final TextView loginUsername = (TextView)findViewById(R.id.login_user);

        loginUsername.setText("Hoşgeldin " + loggedUser);

        //endregion


        //region Butonlar

        //Listview click
        Exp_list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub

                link = adapter.getChildData(groupPosition, childPosition);
                if (ka.playerkontrol()) {
                    ka.startPlaying(link);
                } else
                    ka.stopPlaying();
                return false;

            }
        });


        //Kaydet Click
        final Button kayit = (Button)findViewById(R.id.kaydetbut);

        kayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ka.kayitkontrol()) {
                    Date zaman = new Date();
                    String damga = Long.toString(zaman.getTime());
                    kayitkodu = loggedUser + damga;
                    ka.startRecording(kayitkodu);
                    kayit.setText("Kaydediliyor");
                } else {
                    ka.stopRecording();
                    kayit.setText("Kaydet");
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

        //Action Bar Button
        ImageButton imageButton = (ImageButton) mCustomView
                .findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Refresh Clicked!",
                        Toast.LENGTH_LONG).show();
            }
        });


        //endregion

    }





}