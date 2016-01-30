package com.example.ciayco.spacevoi;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    //mesaj alındı moruk
    HashMap<String, List<String>> Movies_category;
    List<String> Movies_list;
    ExpandableListView Exp_list;
    MoviesAdapter adapter;

    //ignore test emre 20.58
    //yorum satırı hell yeah
    // dosya sunucuya gönderilirken (upload) hangi adres kullanılacak
    String uploadAdresi = "http://www.laforizma.com/upload.php";
    String dosyaKayitYeri = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/uzay.amr";
    // upload download işlemlerinin % olarak göstermek için kullanılacak progress dialog
    ProgressDialog pDialog;
    // get ve post işlemleri yapacağımız AsyncHttpClient nesnesi
    final AsyncHttpClient client = new AsyncHttpClient();
    // dosya gönderirken dosyayı iliştireceğimiz nesne.
    final RequestParams params = new RequestParams();

    public void mesajGoster(String mesaj) {
        Toast.makeText(getApplicationContext(), mesaj, Toast.LENGTH_LONG)
                .show();
    }
    public void onClickDosyaGonder(View v) {
        File file = new File(dosyaKayitYeri);
        try {
            params.put("dosya", file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        pDialog.setMessage("Dosya gönderiliyor. Lütfen bekleyin...");
        pDialog.show();

        client.post(uploadAdresi, params, new AsyncHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                mesajGoster("Dosya sunucuya gönderildi!");
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                mesajGoster("Dosya sunucuya gönderilemedi!");
            }


            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                super.onProgress(bytesWritten, totalSize);
                long progress = (bytesWritten * 100) / totalSize;

                pDialog.setProgress((int)progress);

                if (progress == 100)
                    pDialog.dismiss();
            }
        });
    }

    /////--------------------SES KAYIT BLOGU
    public static final String LOG_TAG = "AudioRecordTest";
    public static String mFileName = null;
    public MediaRecorder mRecorder = null;
    public MediaPlayer mPlayer = null;


    public void startPlaying(View v) {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    public void stopPlaying(View v) {
        mPlayer.release();
        mPlayer = null;
    }

    public void startRecording(View v) {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }
    public void stopRecording(View v) {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }
    public MainActivity() {
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/uzay.amr";
    }

/////--------------------SES KAYIT BLOGU

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        Exp_list = (ExpandableListView) findViewById(R.id.exp_list);
        Movies_category = DataProvider.getInfo();
        Movies_list = new ArrayList<String>(Movies_category.keySet());
        adapter = new MoviesAdapter(this, Movies_category, Movies_list);
        Exp_list.setAdapter(adapter);

////sonradan eklenen
        pDialog = new ProgressDialog(this);
        pDialog.setIndeterminate(false);
        pDialog.setMax(100);
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pDialog.setCancelable(true);
////
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
}


