package com.example.moviapp;

import com.example.moviapp.DataBaseUtils.Movie_DataBase;
import com.example.moviapp.ViewModel.MoviesIDsViewModel;
import com.example.moviapp.model.Review;
import com.example.moviapp.model.Trailer;
import com.example.moviapp.utils.Networking;
import com.example.moviapp.utils.ReviewJsonUtil;
import com.example.moviapp.utils.TrailerJsonUtil;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.moviapp.model.Movie;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import static com.example.moviapp.BuildConfig.*;
public class Detail_activity extends AppCompatActivity  {

    Movie movie;
    ImageView big_poster;
    ImageView small_poster;
    TextView overview ;
    TextView releasedate ;
    TextView rating ;
    TextView title ;
    CollapsingToolbarLayout coll;
    Toolbar toolbar;
    ImageButton IB;
    ArrayList<Trailer> Trailers;
    ArrayList<Review> Reviews;
    private LoaderManager.LoaderCallbacks<ArrayList<Trailer>> TrailerLoaderListener;
    private LoaderManager.LoaderCallbacks<ArrayList<Review>> ReviewLoaderListener;
    final private String apiKey = BuildConfig.THE_GUARDIAN_API_KEY;
    private ArrayList<Integer> DataBaseIDs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activity);

        DataBaseIDs = new ArrayList<>();
        final Context THIS = this;
        final String BaseURL ="https://api.themoviedb.org/3/movie/";
        final String TrailerURL = "/videos?";
        final String ReviewURL = "/reviews?";

        TrailerLoaderListener = new LoaderManager.LoaderCallbacks<ArrayList<Trailer>>() {
            @NonNull
            @Override
            public Loader<ArrayList<Trailer>> onCreateLoader(int id, @Nullable Bundle args) {
                String url = BaseURL+movie.getID()+TrailerURL+apiKey;
                return new TrailerLoader(THIS , url);
            }

            @Override
            public void onLoadFinished(@NonNull Loader<ArrayList<Trailer>> loader, ArrayList<Trailer> data) {
               findViewById(R.id.TrailerProgressBar).setVisibility(View.GONE);
               if(data.size()==0){
                   findViewById(R.id.NOTRAILERS).setVisibility(View.VISIBLE);
               }
               else {
                   Trailers = data;
                   DesplyTrailers();
               }
            }

            @Override
            public void onLoaderReset(@NonNull Loader<ArrayList<Trailer>> loader) {

            }
        };
        ReviewLoaderListener = new LoaderManager.LoaderCallbacks<ArrayList<Review>>() {
            @NonNull
            @Override
            public Loader<ArrayList<Review>> onCreateLoader(int id, @Nullable Bundle args) {
                String url = BaseURL+movie.getID()+ReviewURL+apiKey;
                return new ReviewLoader(THIS , url);
            }

            @Override
            public void onLoadFinished(@NonNull Loader<ArrayList<Review>> loader, ArrayList<Review> data) {
                findViewById(R.id.ReviewProgressBar).setVisibility(View.GONE);
                if(data.size()==0){
                    findViewById(R.id.NOREVIEWS).setVisibility(View.VISIBLE);
                }
                else {
                    Reviews = data;
                    DesplyReviews();
                }
            }

            @Override
            public void onLoaderReset(@NonNull Loader<ArrayList<Review>> loader) {

            }
        };

        IB = findViewById(R.id.fav);
        IB.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if( IB.getTag().toString().equals("res/drawable/favourites.png")){
                    IB.setImageResource(R.drawable.star);
                    IB.setTag(R.drawable.star);
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            Movie_DataBase.getInstance(getApplication()).movies_quaries().Add_Meal(movie);
                        }
                    });
                }
                else{
                    IB.setImageResource( R.drawable.favourites);
                    IB.setTag("res/drawable/favourites.png");
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            Movie_DataBase.getInstance(getApplication()).movies_quaries().delete_Meal(movie);
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!GlobalBus.getBus().isRegistered(this)) {
            GlobalBus.getBus().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlobalBus.getBus().unregister(this);
    }

    private String GetImageUrl(String img , int mod ){
        if(img == null || img.isEmpty() || img.equalsIgnoreCase("null")){
            return null;
        }
        final String BaseUrl;
        if(mod == 1){
            BaseUrl="https://image.tmdb.org/t/p/w342" +
                    "/";
        }
        else {
            BaseUrl = "https://image.tmdb.org/t/p/w185" +
                    "/";
        }
        return BaseUrl + img.substring(1);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Subscribe(sticky = true)
    public void getMessage(Events.SelectedMovie SM) {
        //Write code to perform action after event is received

        movie = SM.getMovie();
        getSupportLoaderManager().initLoader(2,null,TrailerLoaderListener);
        getSupportLoaderManager().initLoader(3,null,ReviewLoaderListener);
        desplay_screen();
    }
    private void desplay_screen(){
        big_poster = findViewById(R.id.toolbarImage);
        small_poster = findViewById(R.id.small_poster);
        overview = findViewById(R.id.over_view);
        overview.setText(movie.getOver_View());
        releasedate = findViewById(R.id.release_date);
        releasedate.setText(movie.getRelease_Date());
        rating = findViewById(R.id.rate);
        rating.setText(String.valueOf(movie.getVote_Average()));
        title = findViewById(R.id.titl);
        title.setText(movie.getOreginal_Title());

        coll = findViewById(R.id.collapsingToolbar);
        coll.setTitle(movie.getTitle());

        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(movie.getTitle());
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Picasso.get().load(GetImageUrl(movie.getBackdrop_path(),1)).into(big_poster);
        Picasso.get().load(GetImageUrl(movie.getPoster(),0)).into(small_poster);

        getIDS();

    }
    private void getIDS(){
        MoviesIDsViewModel MIVM = ViewModelProviders.of(this).get(MoviesIDsViewModel.class);
        MIVM.getIDs().observe(this, new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> movies) {
                DataBaseIDs.clear();
                DataBaseIDs.addAll(movies);
                CheckFav();
            }
        });
    }
    private void CheckFav(){

        for(int i=0;i<DataBaseIDs.size();i++){
            if(DataBaseIDs.get(i) == movie.getID()){
                IB.setImageResource(R.drawable.star);
                IB.setTag(R.drawable.star);
                return;
            }
        }
        IB.setImageResource( R.drawable.favourites);
        IB.setTag("res/drawable/favourites.png");
    }
    static class TrailerLoader extends AsyncTaskLoader<ArrayList<Trailer>>{

        private final String mUrl;

        TrailerLoader(@NonNull Context context, String url) {
            super(context);
            mUrl = url;
        }
        @Override
        protected void onStartLoading() {
            forceLoad();
        }


        @Nullable
        @Override
        public ArrayList<Trailer> loadInBackground() {
            if (mUrl == null) {
                return null;
            }

            // Perform the network request, parse the response, and extract a list of earthquakes.
            return TrailerJsonUtil.Parse_Trailers(Networking.fetchMovieeData(mUrl));
        }
    }
    private void DesplyTrailers(){
        LinearLayout ParentLayOut = findViewById(R.id.linearLayout);

        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);

        //Loop through collection and add views
        for(int i=0; i<Trailers.size();i++)
        {
            //Create the itemView to use layout xml for each cell
            View itemView = inflater.inflate(R.layout.trailer_lay_out, null);

            //Set values within cell
            TextView title = itemView.findViewById(R.id.TrailerName);
            title.setText(Trailers.get(i).getNAME());

            TextView type = itemView.findViewById(R.id.TrailerType);
            type.setText(Trailers.get(i).getTYPE());

            final String KEY = Trailers.get(i).getKEY();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "http://www.youtube.com/watch?v="+KEY;
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }
            });

            //add the itemView to main view
            ParentLayOut.addView(itemView);
        }
    }
    static class ReviewLoader extends AsyncTaskLoader<ArrayList<Review>>{

        private final String mUrl;

        ReviewLoader(@NonNull Context context, String url) {
            super(context);
            mUrl = url;
        }
        @Override
        protected void onStartLoading() {
            forceLoad();
        }


        @Nullable
        @Override
        public ArrayList<Review> loadInBackground() {
            if (mUrl == null) {
                return null;
            }

            // Perform the network request, parse the response, and extract a list of earthquakes.
            return ReviewJsonUtil.Parse_Reviews(Networking.fetchMovieeData(mUrl));
        }
    }
    private void DesplyReviews(){
        LinearLayout ParentLayOut = findViewById(R.id.ReviewLinearLayout);

        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);

        //Loop through collection and add views
        for(int i=0; i<Reviews.size();i++)
        {
            //Create the itemView to use layout xml for each cell
            View itemView = inflater.inflate(R.layout.review_lay_out, null);

            //Set values within cell
            TextView author = itemView.findViewById(R.id.AuthorName);
            author.setText(Reviews.get(i).getAUTHOR());

            TextView content = itemView.findViewById(R.id.Review);
            content.setText(Reviews.get(i).getCONTENT());

            //add the itemView to main view
            ParentLayOut.addView(itemView);
        }
    }
}
