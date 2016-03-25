package com.example.ciayco.spacevoi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.widget.ExpandableListView;

import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ProfilActivity extends AppCompatActivity {

    //region Tanımlamalar
    UpDownSınıf us = new UpDownSınıf();
    KaydetCalSınıf ka = new KaydetCalSınıf();
    static String loggedUser;
    static String kayitkodu;
    static String link;

    HashMap<String, List<String>> Movies_category;
    List<String> Movies_list;
    ExpandableListView Exp_list;
    ListAdapterSınıf adapter;

    //endregion


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil);

        //region Login
        Intent intent = getIntent();
        Bundle intentBundle = intent.getExtras();
        loggedUser = intentBundle.getString("USERNAME");
        loggedUser = loggedUser.toLowerCase();
        loggedUser = loggedUser.substring(0,1).toUpperCase()+ loggedUser.substring(1);

        final TextView loginUsername = (TextView)findViewById(R.id.login_user);

        loginUsername.setText(loggedUser);

        //endregion

        //region listviewçekme
        Exp_list = (ExpandableListView) findViewById(R.id.exp_list);
        Movies_category = KayıtlarSınıf.getInfoKayıtlarım(getApplicationContext());
        Movies_list = new ArrayList<>(Movies_category.keySet());
        adapter = new ListAdapterSınıf(this, Movies_category, Movies_list);
        Exp_list.setAdapter(adapter);
        Exp_list.expandGroup(0);
        //endregion








    }
}
