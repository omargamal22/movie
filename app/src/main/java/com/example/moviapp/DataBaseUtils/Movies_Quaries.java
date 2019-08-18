package com.example.moviapp.DataBaseUtils;

import com.example.moviapp.model.Movie;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface Movies_Quaries {
    @Query("SELECT * FROM Movie")
    LiveData<List<Movie>> Get_Movies_From_DataBase ();
    @Query("SELECT ID FROM Movie")
    LiveData<List<Integer>> Get_Movies_ID_From_DataBase ();
    @Insert
    void Add_Meal(Movie M);
    @Delete
    void delete_Meal(Movie M);
}
