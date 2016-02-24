package com.example.ciayco.spacevoi;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;




public class MainActivity extends AppCompatActivity  {

    Upload us = new Upload();
    SesKayit ka = new SesKayit();
    static String loggedUser;
    static String kayitkodu;

    //region EMRE EKLEME
    public GoogleApiClient client;

    HashMap<String, List<String>> Movies_category;
    List<String> Movies_list;
    ExpandableListView Exp_list;
    MoviesAdapter adapter;

    //endregion

    //region BUTON OLAYLARI

    public void gonder(View v) {


        us.DosyaGonder(getApplicationContext(),loggedUser,kayitkodu);
    }

    public void kaydet(View v){
        Date zaman = new Date();
        String damga = Long.toString(zaman.getTime()) ;
        this.kayitkodu = loggedUser + damga;
     ka.startRecording(kayitkodu);
    }

    public void kayitdurdur(View v){
     ka.stopRecording();
    }

    public void cal(View v) {
      ka.startPlaying(kayitkodu);
    }

    public void durdur(View v) {
     ka.stopPlaying();
    }

    //endregion

    //region ONCREATE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //emre ekleme 2
        Exp_list = (ExpandableListView) findViewById(R.id.exp_list);
        Movies_category = DataProvider.getInfo();
        Movies_list = new ArrayList<>(Movies_category.keySet());
        adapter = new MoviesAdapter(this, Movies_category, Movies_list);
          Exp_list.setAdapter(adapter);
        //emre ekleme 2 son

       client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


        //Login Ekleme

        Intent intent = getIntent();
        Bundle intentBundle = intent.getExtras();
        this.loggedUser = intentBundle.getString("USERNAME");

        this.loggedUser = capitalizeFirstCharacter(this.loggedUser);


        TextView loginUsername = (TextView)findViewById(R.id.login_user);

        loginUsername.setText(this.loggedUser);

        //Login Ekleme
    }

    private String capitalizeFirstCharacter(String textInput){
        String input = textInput.toLowerCase();
        String output = input.substring(0, 1).toUpperCase() + input.substring(1);
        return output;
    }
    //endregion


}