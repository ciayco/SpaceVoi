package com.example.ciayco.spacevoi;


import android.net.Uri;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.widget.ExpandableListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity  {


    //emre ekleme 1
    HashMap<String, List<String>> Movies_category;
    List<String> Movies_list;
    ExpandableListView Exp_list;
    MoviesAdapter adapter;
    //emre ekleme 1 son

    //Upload us = new Upload();
   // SesKayit ka = new SesKayit();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public void upload(View v) {
      //  us.DosyaGonder(getApplicationContext());
    }

    public void cal(View v) {
      //  ka.startPlaying();
    }

    public void durdur(View v) {
     //   ka.stopPlaying();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //emre ekleme 2
        Exp_list = (ExpandableListView) findViewById(R.id.exp_list);
        Movies_category = DataProvider.getInfo();
        Movies_list = new ArrayList<String>(Movies_category.keySet());
        adapter = new MoviesAdapter(this, Movies_category, Movies_list);
        Exp_list.setAdapter(adapter);
        //emre ekleme 2 son

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //


}