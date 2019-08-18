package com.example.moviapp.DataBaseUtils;

import android.content.Context;

import com.example.moviapp.model.Movie;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Movie.class, version = 1,exportSchema = false)
public abstract class Movie_DataBase extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static final String DatabaseName = "MovieDB";
    private static Movie_DataBase MDB ;
    public static  Movie_DataBase getInstance(Context context){
        if(MDB == null){
            synchronized (LOCK){
                MDB = Room.databaseBuilder(context.getApplicationContext(),Movie_DataBase.class,DatabaseName).build();
            }
        }
        return MDB;
    }
    public abstract Movies_Quaries movies_quaries();
}
