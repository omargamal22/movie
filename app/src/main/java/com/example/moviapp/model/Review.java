package com.example.moviapp.model;

public class Review {
    public final static String author = "author";
    public final static String content = "content";

    private String AUTHOR;
    private String CONTENT;

    public Review() {}

    public String getAUTHOR() {
        return AUTHOR;
    }

    public void setAUTHOR(String AUTHOR) {
        this.AUTHOR = AUTHOR;
    }

    public String getCONTENT() {
        return CONTENT;
    }

    public void setCONTENT(String CONTENT) {
        this.CONTENT = CONTENT;
    }

    public Review(String AUTHOR, String CONTENT) {
        this.AUTHOR = AUTHOR;
        this.CONTENT = CONTENT;
    }
}
