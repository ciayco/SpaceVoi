package com.example.ciayco.spacevoi;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

/**
 * Created by Jolene on 25.3.2016.
 */
public class PopUpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pop_window);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int genislik = dm.widthPixels;
        int yukseklik= dm.heightPixels;

        getWindow().setLayout((int)(genislik*.8),(int)(yukseklik*.8));
    }
}
