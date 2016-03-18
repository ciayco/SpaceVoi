package com.example.ciayco.spacevoi;


import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class UpDownSınıf {

    //region Tanımlamalar

    String indirilecekDosyaAdresi = "http://www.spacevoice.tk/kayitlar/Gokhan/Gokhan.amr";
    String poolindirmeAdresi = "http://www.spacevoice.tk/kayitlar/pool.txt";
    String poolkayityeri = Environment.getExternalStorageDirectory().getAbsolutePath()+"/SpaceVoi/pool/pool.txt";
    String dosyakayityeri = Environment.getExternalStorageDirectory().getAbsolutePath()+"/SpaceVoi/pool/Gokhan.amr";

    int size;

    // dosya sunucuya gönderilirken (upload) hangi adres kullanılacak
    String uploadAdresi = "http://www.spacevoice.tk/upload.php";
    String begenAdresi = "http://www.spacevoice.tk/begen.php";
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


        client.get(indirilecekDosyaAdresi, new FileAsyncHttpResponseHandler(ctxx) {


            @Override
            public void onSuccess(int i, Header[] headers, File file) {

                try {

                    FileInputStream dosya = new FileInputStream(file);

                    size = (int) file.length();
                    FileOutputStream yenidosya = new FileOutputStream(dosyakayityeri);
                    pump(dosya, yenidosya, size, ctxx);
                } catch (FileNotFoundException e) {
                    mesajGoster(ctxx, e.getMessage());
                }

            }


            @Override
            public void onFailure(int i, Header[] headers, Throwable throwable, File file) {
                mesajGoster(ctxx, "Dosya indirilemedi");
            }
        });

    }
//endregion


    //region Pool çek

    public void Poolcek(final Context ctxpool) {



        client.get(poolindirmeAdresi, new FileAsyncHttpResponseHandler(ctxpool) {


            @Override
            public void onSuccess(int i, Header[] headers, File file) {

                try {

                    InputStream dosya = new FileInputStream(file);

                    size = (int) file.length();
                    OutputStream yenidosya = new FileOutputStream(poolkayityeri);
                    pump(dosya, yenidosya, size, ctxpool);
                    mesajGoster(ctxpool, "Dosya Kaydedildi");
                } catch (FileNotFoundException e) {
                    mesajGoster(ctxpool, "Dosya Bulunamadı deneme");
                }

            }


            @Override
            public void onFailure(int i, Header[] headers, Throwable throwable, File file) {
                mesajGoster(ctxpool, "Dosya indirilemedi");
            }
        });

    }


    //endregion


    //region deneme string cekme....!!!!!!

    public void StringCek(){

            params.put("like", "like");

        client.post(uploadAdresi, params, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, String x) {

            }

            @Override
            public void onFailure(int i, Header[] headers, String x, Throwable throwable) {

            }
        });
    }

//endregion


    //region binary yazma ve txt okuma

    public void pump(InputStream in, OutputStream out, int size,Context ctxx) {
        //indirilen dosyayı yeniden yazma
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

                mesajGoster(ctxx,"Okuma Yazma Hatası");
            }


        }

    }

    //Pool txt dosyasını okuma ve array e çekme

 public  String[] pooloku(Context ctxxx){
     String str;
     List<String> list = new ArrayList<String>();

     try {
         BufferedReader in = new BufferedReader(new FileReader(poolkayityeri));
         while ((str = in.readLine()) != null) {
             list.add(str);
         }
     }
     catch (FileNotFoundException f){
         mesajGoster(ctxxx,f.getMessage());
     }

     catch (IOException e)
     {
         mesajGoster(ctxxx, "Pool okunamadı");
     }
    String[] stringArr = list.toArray(new String[0]);

     return stringArr;
 }

    //endregion


    //region Begen

    public void Begen(String kayitkodu,String durum) {



            params.put("kayitadi", kayitkodu);
            params.put("durum", durum);

        client.post(begenAdresi, params, new AsyncHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }


        });
    }
    //endregion



    public void mesajGoster(Context context,String mesaj) {

        Toast.makeText(context, mesaj, Toast.LENGTH_LONG)
                .show();
    }

}
