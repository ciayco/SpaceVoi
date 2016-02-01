package com.example.ciayco.spacevoi;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.Header;


public class Upload {

    // dosya sunucuya gönderilirken (upload) hangi adres kullanılacak
    String uploadAdresi = "http://www.laforizma.com/upload.php";
    String dosyaKayitYeri = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/uzay.amr";
    // upload download işlemlerinin % olarak göstermek için kullanılacak progress dialog
    ProgressDialog pDialog;
    // get ve post işlemleri yapacağımız AsyncHttpClient nesnesi
    final AsyncHttpClient client = new AsyncHttpClient();
    // dosya gönderirken dosyayı iliştireceğimiz nesne.
    final RequestParams params = new RequestParams();


    public void mesajGoster(Context context,String mesaj) {

        Toast.makeText(context, mesaj, Toast.LENGTH_LONG)
                .show();
    }

    public void DosyaGonder(final Context ctx) {
        File file = new File(dosyaKayitYeri);
        try {
            params.put("dosya", file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ////sonradan eklenen
        pDialog = new ProgressDialog(ctx);
        pDialog.setIndeterminate(false);
        pDialog.setMax(100);
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pDialog.setCancelable(true);
////

        pDialog.setMessage("Dosya gönderiliyor. Lütfen bekleyin...");
        pDialog.show();

        client.post(uploadAdresi, params, new AsyncHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                mesajGoster(ctx,"Dosya sunucuya gönderildi!");
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                mesajGoster(ctx,"Dosya sunucuya gönderilemedi!");
            }


            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                super.onProgress(bytesWritten, totalSize);
                long progress = (bytesWritten * 100) / totalSize;

                pDialog.setProgress((int) progress);

                if (progress == 100)
                    pDialog.dismiss();
            }
        });
    }

}
