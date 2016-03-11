package com.example.ciayco.spacevoi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ProfilActivity extends AppCompatActivity {

    //region Tanımlamalar
    Upload us = new Upload();
    SesKayit ka = new SesKayit();
    static String loggedUser;
    static String kayitkodu;
    static String link;

    HashMap<String, List<String>> Movies_category;
    List<String> Movies_list;
    ExpandableListView Exp_list;
    MoviesAdapter adapter;

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
        Movies_category = DataProvider.getInfo();
        Movies_list = new ArrayList<>(Movies_category.keySet());
        adapter = new MoviesAdapter(this, Movies_category, Movies_list);
        Exp_list.setAdapter(adapter);
        //endregion

        //region Listview click
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
        //endregion

    }
}
