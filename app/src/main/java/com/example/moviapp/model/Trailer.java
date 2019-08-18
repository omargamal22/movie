package com.example.moviapp.model;

public class Trailer {
    public static String key = "key";
    public static String name = "name";
    public static String type = "type";

    private String KEY;
    private String NAME;
    private String TYPE;

    public Trailer() {}

    public Trailer(String KEY, String NAME, String TYPE) {
        this.KEY = KEY;
        this.NAME = NAME;
        this.TYPE = TYPE;
    }

    public String getKEY() {
        return KEY;
    }

    public void setKEY(String KEY) {
        this.KEY = KEY;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }
}
