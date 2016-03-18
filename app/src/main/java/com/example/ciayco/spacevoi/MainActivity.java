package com.example.ciayco.spacevoi;



import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;





public class MainActivity extends AppCompatActivity {


    //region Tanımlamalar

    UpDownSınıf us = new UpDownSınıf();
    KaydetCalSınıf ka = new KaydetCalSınıf();
    static String loggedUser ;
    static String kayitkodu;
    static String link;


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
        return super.onOptionsItemSelected(item);
    }
        //endregion


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



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



        //endregion

    }





}