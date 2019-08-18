package com.example.moviapp.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;


import com.example.moviapp.DataBaseUtils.Movie_DataBase;
import com.example.moviapp.model.Movie;

import java.util.List;

public class MovieMainViewModel extends AndroidViewModel {

    LiveData<List<Movie>> Movies;

    public LiveData<List<Movie>> getMovies() {
        return Movies;
    }

    public MovieMainViewModel(@NonNull Application application) {
        super(application);
        Movies = Movie_DataBase.getInstance(this.getApplication()).movies_quaries().Get_Movies_From_DataBase();

    }
}
