package com.example.ciayco.spacevoi;


import android.content.Context;
import android.os.Environment;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.Header;


public class Upload {

    // dosya sunucuya gönderilirken (upload) hangi adres kullanılacak
    String uploadAdresi = "http://www.spacevoice.tk/upload.php";

    // get ve post işlemleri yapacağımız AsyncHttpClient nesnesi
    final AsyncHttpClient client = new AsyncHttpClient();
    // dosya gönderirken dosyayı iliştireceğimiz nesne.
    final RequestParams params = new RequestParams();


    public void mesajGoster(Context context,String mesaj) {

        Toast.makeText(context, mesaj, Toast.LENGTH_LONG)
                .show();
    }

    public void DosyaGonder(final Context ctx,String kullanici,String kayitkodu) {
        String dosyaKayitYeri = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/SpaceVoi/" +kayitkodu+ ".amr";
        File file = new File(dosyaKayitYeri);
        try {
            params.put("dosya", file);
            params.put("kullanici", kullanici);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        client.post(uploadAdresi, params, new AsyncHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                mesajGoster(ctx, "Dosya sunucuya gönderildi!");
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                mesajGoster(ctx, "Dosya sunucuya gönderilemedi!");
            }


        });
    }

}
