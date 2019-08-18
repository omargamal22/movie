package com.example.moviapp.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Movie {

    @Ignore
    public static final String vote_average="vote_average";
    @Ignore
    public static final String title="title";
    @Ignore
    public static final String poster_path="poster_path";
    @Ignore
    public static final String backdrop_path="backdrop_path";
    @Ignore
    public static final String overview = "overview";
    @Ignore
    public static final String release_date ="release_date";
    @Ignore
    public static final String original_title ="original_title";
    @Ignore
    public static final String id ="id";

    @PrimaryKey
    private int ID;
    private String Title;
    private String Oreginal_Title;
    private String Over_View;
    private double Vote_Average;
    private String Release_Date;
    private String Poster;
    private String Backdrop_path;


    public Movie() {
    }

    public Movie(int ID, String title, String oreginal_Title, String over_View, double vote_Average, String release_Date, String poster, String backdrop_path) {
        this.ID = ID;
        Title = title;
        Oreginal_Title = oreginal_Title;
        Over_View = over_View;
        Vote_Average = vote_Average;
        Release_Date = release_Date;
        Poster = poster;
        Backdrop_path = backdrop_path;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }


    public String getBackdrop_path() {
        return Backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        Backdrop_path = backdrop_path;
    }

// --Commented out by Inspection START (26/07/2019 12:51 ص):
//    public Movie(String title, String oreginal_Title, String over_View, double vote_Average, String release_Date, String poster, String backdrop_path) {
//        Title = title;
//        Oreginal_Title = oreginal_Title;
//        Over_View = over_View;
//        Vote_Average = vote_Average;
//        Release_Date = release_Date;
//        Poster = poster;
//        Backdrop_path = backdrop_path;
//    }
// --Commented out by Inspection STOP (26/07/2019 12:51 ص)

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getOreginal_Title() {
        return Oreginal_Title;
    }

    public void setOreginal_Title(String oreginal_Title) {
        Oreginal_Title = oreginal_Title;
    }

    public String getOver_View() {
        return Over_View;
    }

    public void setOver_View(String over_View) {
        Over_View = over_View;
    }

    public double getVote_Average() {
        return Vote_Average;
    }

    public void setVote_Average(double vote_Average) {
        Vote_Average = vote_Average;
    }

    public String getRelease_Date() {
        return Release_Date;
    }

    public void setRelease_Date(String release_Date) {
        Release_Date = release_Date;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }
}
