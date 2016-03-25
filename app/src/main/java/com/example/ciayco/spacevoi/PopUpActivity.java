package com.example.ciayco.spacevoi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Jolene on 25.3.2016.
 */
public class PopUpActivity extends Activity {

    KaydetCalSınıf kayit = new KaydetCalSınıf();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pop_window);
        TextView kayittext = (TextView)findViewById(R.id.kayitText);

        Intent intent = getIntent();
        Bundle intentBundle = intent.getExtras();
        final String kayitkodu = intentBundle.getString("KayıtKodu");
        kayittext.setText(kayitkodu);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int genislik = dm.widthPixels;
        int yukseklik= dm.heightPixels;

        getWindow().setLayout((int)(genislik*.8),(int)(yukseklik*.4));

        final Button gonder = (Button)findViewById(R.id.silBut);
        gonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           kayit.sil(kayitkodu,getApplicationContext());
            }
        });
    }
}
