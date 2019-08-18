package com.example.moviapp.ViewModel;

import android.app.Application;

import com.example.moviapp.DataBaseUtils.Movie_DataBase;
import com.example.moviapp.model.Movie;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MoviesIDsViewModel extends AndroidViewModel {
    LiveData<List<Integer>> IDs;

    public LiveData<List<Integer>> getIDs() {
        return IDs;
    }

    public MoviesIDsViewModel(@NonNull Application application) {
        super(application);
        IDs = Movie_DataBase.getInstance(this.getApplication()).movies_quaries().Get_Movies_ID_From_DataBase();
    }
}
