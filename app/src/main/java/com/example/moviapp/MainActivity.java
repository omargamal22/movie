package com.example.moviapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.moviapp.ViewModel.MovieMainViewModel;
import com.example.moviapp.model.Movie;
import com.example.moviapp.utils.JsonUtil;
import com.example.moviapp.utils.Networking;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import static com.example.moviapp.BuildConfig.*;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    private static final String popular = "https://api.themoviedb.org/3/movie/popular?";
    private static final String top_rated = "https://api.themoviedb.org/3/movie/top_rated?";
    private MovieAdpter movieadapter;
    final private String apiKey = THE_GUARDIAN_API_KEY ;
    public static List<Movie> DataBaseMovies;
    String minMagnitude;
    GridLayoutManager gridLayoutManager ;
    private final String LIST_STATE_KEY = "ListState";
    private Parcelable gridLayoutManagerState;
    View loadingIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loadingIndicator = findViewById(R.id.grid_progressbar);
        GlobalBus.getBus().register(this);
        DataBaseMovies = new ArrayList<>();
        RetriveDataBase();
        RecyclerView posters = findViewById(R.id.Posters);
        gridLayoutManager = new GridLayoutManager(this,3);
        posters.setLayoutManager(gridLayoutManager);
        final Activity THIS = this;
        movieadapter = new MovieAdpter(this, new ArrayList<Movie>(), new CustomItemClickListener() {
            @Override
            public void onItemClick(Movie movie) {
                GlobalBus.getBus().postSticky(new Events.SelectedMovie(movie));
                Intent i = new Intent(THIS , Detail_activity.class);
                startActivity(i);
            }
        });
        posters.setAdapter(movieadapter);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(this);
        minMagnitude = sh.getString("type", "popularity.desc");
        NotifyIfFav(1);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Movies");

        Button setting = findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(THIS , SettingActivity.class));
            }
        });

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlobalBus.getBus().unregister(this);
    }
    @Subscribe(sticky = true)
    public void GetNewValue(Events.PreferenceChanged SM) {
        //Write code to perform action after event is received
        GlobalBus.getBus().removeStickyEvent(SM);
        minMagnitude =  SM.getNew_value();
        NotifyIfFav(2);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(gridLayoutManagerState!=null){
            gridLayoutManager.onRestoreInstanceState(gridLayoutManagerState);
        }
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState!=null){
            gridLayoutManagerState = savedInstanceState.getParcelable(LIST_STATE_KEY);
        }
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(LIST_STATE_KEY,gridLayoutManager.onSaveInstanceState());
    }
    @NonNull
    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int i, @Nullable Bundle bundle) {
        View loadingIndicator = findViewById(R.id.grid_progressbar);
        loadingIndicator.setVisibility(View.VISIBLE);
        movieadapter.clearALL();
        String requestUrl = top_rated+apiKey ;
        if(minMagnitude.equals("popularity.desc")){
            requestUrl = popular+apiKey;
        }
        return new MovieLoader(this,requestUrl);
    }
    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movie>> loader, ArrayList<Movie> movies) {
        loadingIndicator.setVisibility(View.INVISIBLE);
        if(movies.size()==0){
            TextView PlaceHolder = findViewById(R.id.place_holder);
            PlaceHolder.setVisibility(View.VISIBLE);
            PlaceHolder.setText(getResources().getText(R.string.NoDATA));
        }
        movieadapter.addAll(movies);
        movieadapter.notifyDataSetChanged();
    }
    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Movie>> loader) {


    }
    static class MovieLoader extends AsyncTaskLoader<ArrayList<Movie>>{

        private final String mUrl;

        MovieLoader(@NonNull Context context, String url) {
            super(context);
            mUrl = url;
        }
        @Override
        protected void onStartLoading() {
            forceLoad();
        }


        @Nullable
        @Override
        public ArrayList<Movie> loadInBackground() {
            if (mUrl == null) {
                return null;
            }

            // Perform the network request, parse the response, and extract a list of earthquakes.
            return JsonUtil.Parse_Movise(Networking.fetchMovieeData(mUrl));
        }
    }
    private void  RetriveDataBase(){
        MovieMainViewModel MMVM = ViewModelProviders.of(this).get(MovieMainViewModel.class);
        MMVM.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                DataBaseMovies.clear();
                DataBaseMovies.addAll(movies);
                NotifyIfFav(0);
            }
        });
    }
    private void NotifyIfFav(int mod){
        //if there is no internet he could get the fav
        if(minMagnitude.equals("fav")){
            movieadapter.addAll(new ArrayList<>(DataBaseMovies));
            movieadapter.notifyDataSetChanged();
            if(getSupportLoaderManager().getLoader(1) != null) {
                getSupportLoaderManager().destroyLoader(1);
            }
        }
        else if(mod == 1 ||mod == 2 ) {
            Updateloader(mod);
        }
    }
    private void Updateloader(int mod){
        ConnectivityManager cm =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            if(mod == 1) {
                getSupportLoaderManager().initLoader(1, null, this);
            }
            else{
                if(getSupportLoaderManager().getLoader(1)==null){
                    getSupportLoaderManager().initLoader(1, null, this);
                }
                else {
                    getSupportLoaderManager().restartLoader(1, null, this);
                }
            }
        }
        else{
            loadingIndicator.setVisibility(View.INVISIBLE);
            findViewById(R.id.place_holder).setVisibility(View.VISIBLE);
        }
    }
}

