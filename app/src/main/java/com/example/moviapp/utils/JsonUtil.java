package com.example.moviapp.utils;

import com.example.moviapp.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtil {
    public static ArrayList<Movie> Parse_Movise(String json){
        ArrayList<Movie> Movies = new ArrayList<>();
        // Create a JSONObject from the JSON response string
        try {
            JSONObject baseJsonResponse = new JSONObject(json);
            JSONArray MoviesJson = baseJsonResponse.getJSONArray("results");
            int MoviesCount = MoviesJson.length();
            for(int i=0;i<MoviesCount;i++){
                Movie TempMovie = new Movie();
                JSONObject movie = MoviesJson.getJSONObject(i);
                TempMovie.setVote_Average(movie.getDouble(Movie.vote_average));
                TempMovie.setTitle(movie.getString(Movie.title));
                TempMovie.setOreginal_Title(movie.getString(Movie.original_title));
                TempMovie.setPoster(movie.getString(Movie.poster_path));
                TempMovie.setOver_View(movie.getString(Movie.overview));
                TempMovie.setRelease_Date(movie.getString(Movie.release_date));
                TempMovie.setBackdrop_path(movie.getString(Movie.backdrop_path));
                TempMovie.setID(movie.getInt(Movie.id));
                Movies.add(TempMovie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Movies;
    }
}
