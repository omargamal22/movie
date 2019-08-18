package com.example.moviapp.utils;

import com.example.moviapp.model.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReviewJsonUtil {
    public static ArrayList<Review> Parse_Reviews(String json){
        ArrayList<Review> Reviews = new ArrayList<>();
        // Create a JSONObject from the JSON response string
        try {
            JSONObject baseJsonResponse = new JSONObject(json);
            JSONArray ReviewsArray = baseJsonResponse.getJSONArray("results");
            int  ReviewsCount = ReviewsArray.length();
            for(int i=0;i<ReviewsCount;i++){
                Review TempReview = new Review();
                JSONObject JsonTrailer = ReviewsArray.getJSONObject(i);
                TempReview.setAUTHOR(JsonTrailer.getString(Review.author));
                TempReview.setCONTENT(JsonTrailer.getString(Review.content));
                Reviews.add(TempReview);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Reviews;
    }
}
