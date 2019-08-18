package com.example.moviapp.utils;

import com.example.moviapp.model.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TrailerJsonUtil {
    public static ArrayList<Trailer> Parse_Trailers(String json){
        ArrayList<Trailer> Trailers = new ArrayList<>();
        // Create a JSONObject from the JSON response string
        try {
            JSONObject baseJsonResponse = new JSONObject(json);
            JSONArray TrailersArray = baseJsonResponse.getJSONArray("results");
            int  TrailersCount = TrailersArray.length();
            for(int i=0;i<TrailersCount;i++){
                Trailer TempTrailer = new Trailer();
                JSONObject JsonTrailer = TrailersArray.getJSONObject(i);
                TempTrailer.setKEY(JsonTrailer.getString(Trailer.key));
                TempTrailer.setNAME(JsonTrailer.getString(Trailer.name));
                TempTrailer.setTYPE(JsonTrailer.getString(Trailer.type));
                Trailers.add(TempTrailer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Trailers;
    }
}
