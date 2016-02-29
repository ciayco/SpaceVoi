package com.example.ciayco.spacevoi;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jolene on 30.1.2016.
 */
public class DataProvider {

    final  static int i=0;
    public static HashMap<String, List<String>> getInfo()
    {

        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        path += "/SpaceVoi";
        File f = new File(path);
        File file[] = f.listFiles();

        HashMap<String, List<String>> MoviesDetails = new HashMap<String, List<String>>();
        List<String> Action_Movies = new ArrayList<String>();

        for(int i=0; i < file.length; i++) {
            Action_Movies.add(file[i].getName());
        }


        MoviesDetails.put("Ses Kayıtları", Action_Movies);



        return MoviesDetails;
    }


}
