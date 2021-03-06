package com.example.ciayco.spacevoi;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class KayıtlarSınıf {
    static UpDownSınıf pool = new UpDownSınıf();

    public static HashMap<String, List<String>> getInfoKayıtlarım(Context ctx)
    {

        String path = ctx.getExternalCacheDir().getPath()+ "/Profil";
        File f = new File(path);
        File file[] = f.listFiles();

        HashMap<String, List<String>> SesKayitlari = new HashMap<String, List<String>>();
        List<String> ProfilKayitlari = new ArrayList<String>();

        for(int i=0; i < file.length; i++) {
            ProfilKayitlari.add(file[i].getName());
        }


        SesKayitlari.put("Ses Kayıtları", ProfilKayitlari);



        return SesKayitlari;
    }


    public static HashMap<String,List<String>> getInfoPoolKayıtları(Context ctx)
    {
        String[] str ;

        str = pool.pooloku(ctx);

        HashMap<String, List<String>> PoolSesKayitlari = new HashMap<String, List<String>>();
        List<String> PoolKayitlari = new ArrayList<String>();

        for(int i=0; i < str.length; i++) {
           PoolKayitlari.add(str[i]);
            pool.DosyaIndir(ctx,"pool",str[i]);
        }


        PoolSesKayitlari.put("Pool Kayıtları", PoolKayitlari);


        return PoolSesKayitlari ;
    }



}
