package com.example.ciayco.spacevoi;


import android.content.Context;
import android.os.Environment;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cz.msebera.android.httpclient.Header;


public class UpDownSınıf {

    //region Tanımlamalar

    String indirilecekDosyaAdresi = "http://www.spacevoice.tk/kayitlar/Gokhan/Gokhan1456281058671.amr";
    static String dosyakayityeri = Environment.getExternalStorageDirectory().getAbsolutePath();
    static String yol;
    static int size;

    // dosya sunucuya gönderilirken (upload) hangi adres kullanılacak
    String uploadAdresi = "http://www.spacevoice.tk/upload.php";

    // get ve post işlemleri yapacağımız AsyncHttpClient nesnesi
    final AsyncHttpClient client = new AsyncHttpClient();
    // dosya gönderirken dosyayı iliştireceğimiz nesne.
    final RequestParams params = new RequestParams();

      //endregion


    //region Dosya Gönder

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
    //endregion


    //region Dosya İndir

    public void DosyaIndir(final Context ctxx) {

        dosyakayityeri += "/SpaceVoi/pool/Gokhan1456281058671.amr";

        client.get(indirilecekDosyaAdresi, new FileAsyncHttpResponseHandler(ctxx) {


            @Override
            public void onSuccess(int i, Header[] headers,File file) {

                try {

                    InputStream dosya = new FileInputStream(file);

                    size = (int)file.length();
                    OutputStream yenidosya = new FileOutputStream(dosyakayityeri);
                    pump(dosya,yenidosya,size,ctxx);
                }
                catch (FileNotFoundException e){
                    mesajGoster(ctxx,"Dosya Bulunamadı");
                }

            }


            @Override
            public void onFailure(int i, Header[] headers, Throwable throwable, File file) {
                mesajGoster(ctxx, "Dosya indirilemedi");
            }
        });

    }
//endregion


    public void StringCek(){

            params.put("pool", "pool");

        client.post(uploadAdresi, params, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, String x) {

            }

            @Override
            public void onFailure(int i, Header[] headers, String x, Throwable throwable) {

            }
        });
    }

    public void pump(InputStream in, OutputStream out, int size,Context ctxx) {
        byte[] buffer = new byte[4096];
        int done = 0;
        while (done < size) {
            try {
                int read = in.read(buffer);
                out.write(buffer, 0, read);
                done += read;
            }
            catch (IOException e)
            {
                mesajGoster(ctxx,"burda hata var");
            }


        }

    }



    public void mesajGoster(Context context,String mesaj) {

        Toast.makeText(context, mesaj, Toast.LENGTH_LONG)
                .show();
    }

}
