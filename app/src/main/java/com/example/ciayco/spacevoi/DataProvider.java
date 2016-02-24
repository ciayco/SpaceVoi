package com.example.ciayco.spacevoi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jolene on 30.1.2016.
 */
public class DataProvider {

    public static HashMap<String, List<String>> getInfo()
    {
        HashMap<String, List<String>> MoviesDetails = new HashMap<String, List<String>>();
        List<String> Action_Movies = new ArrayList<String>();
        Action_Movies.add("Robocop");
        Action_Movies.add("Robocop 2");
        Action_Movies.add("Robocop 3");
        Action_Movies.add("Robocop 4");
        Action_Movies.add("Robocop 5");


        List<String> Romantic_Movies = new ArrayList<String>();
        Romantic_Movies.add("Romanik film 1");
        Romantic_Movies.add("Romanik film 2");
        Romantic_Movies.add("Romanik film 3");
        Romantic_Movies.add("Romanik film 4");
        Romantic_Movies.add("Romanik film 5");

        List<String> Horror_Movies = new ArrayList<String>();
        Horror_Movies.add("Horror film 1");
        Horror_Movies.add("Horror film 2");
        Horror_Movies.add("Horror film 3");
        Horror_Movies.add("Horror film 4");
        Horror_Movies.add("Horror film 5");

        List<String> Comedy_Movies = new ArrayList<String>();
        Comedy_Movies.add("komedi film 1");
        Comedy_Movies.add("komedi film 2");
        Comedy_Movies.add("komedi film 3");
        Comedy_Movies.add("komedi film 4");
        Comedy_Movies.add("komedi film 5");

        MoviesDetails.put("Actiono Movies", Action_Movies);
        MoviesDetails.put("Romantico Movies", Romantic_Movies);
        MoviesDetails.put("Horrorico Movies", Horror_Movies);
        MoviesDetails.put("Comedico Movies", Comedy_Movies);

        return MoviesDetails;
    }


}
