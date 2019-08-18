package com.example.moviapp;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.moviapp.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/*public class MovieAdpter extends BaseAdapter {

    private final Context mContext;
    private ArrayList<Movie> Movies;

    public MovieAdpter(Context mContext, ArrayList<Movie> movies) {
        this.mContext = mContext;
        Movies = movies;
    }

    @Override
    public int getCount() {
        return Movies.size();
    }

    @Override
    public Object getItem(int position) {
        return Movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Movie movie = Movies.get(position);
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.poster_layout, null);
        }
        final ImageView imageView = convertView.findViewById(R.id.poster_image);
        String link = GetImageUrl(movie.getPoster());
        if(link != null) {
            Picasso.get()
                    .load(link)
                    .into(imageView);
        }
        else{
            imageView.setImageResource(R.drawable.ic_launcher_background);
        }
        return convertView;
    }
    private String GetImageUrl(String img){
        if(img == null || img.isEmpty() || img.equalsIgnoreCase("null")){
            return null;
        }
        final String BaseUrl="https://image.tmdb.org/t/p/w92" +
                "/";
        StringBuilder SB = new StringBuilder(BaseUrl);
        SB.append(img.substring(1));
        return SB.toString();
    }
    public void clear(){
        Movies.clear();
    }
    public void addAll( ArrayList<Movie> movies){
        Movies.addAll(movies);
        notifyDataSetChanged();
    }
}*/
public class MovieAdpter extends RecyclerView.Adapter<MovieAdpter.MyViewHolder> {

    private final Context mContext;
    private final ArrayList<Movie> Movies;
    private final CustomItemClickListener c;

    public MovieAdpter(Context mContext, ArrayList<Movie> movies , CustomItemClickListener cl) {
        this.mContext = mContext;
        Movies = movies;
        c=cl;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v =  LayoutInflater.from(mContext)
                .inflate(R.layout.poster_layout, viewGroup, false);
        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) v.getLayoutParams();
        lp.height = 360;
        lp.width= (viewGroup.getWidth()-20)/3;
        v.setLayoutParams(lp);
        return  new MyViewHolder(v) ;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        String link = GetImageUrl(Movies.get(i).getPoster());
        if(link != null) {
            Picasso.get()
                    .load(link)
                    .into(myViewHolder.imageView);
        }
        else{
            myViewHolder.imageView.setImageResource(R.drawable.ic_launcher_background);
        }
        myViewHolder.setclick(c ,Movies.get(i) );
    }

    private String GetImageUrl(String img){
        if(img == null || img.isEmpty() || img.equalsIgnoreCase("null")){
            return null;
        }
        final String BaseUrl="https://image.tmdb.org/t/p/w92" +
                "/";
        StringBuilder SB = new StringBuilder(BaseUrl);
        SB.append(img.substring(1));
        return SB.toString();
    }

    @Override
    public int getItemCount() {
        return Movies.size();
    }

    public void clearALL(){
        Movies.clear();
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<Movie> data){
        Movies.clear();
        Movies.addAll(data);
        notifyDataSetChanged();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        final ImageView imageView;
        final View view;
        MyViewHolder(View v) {
            super(v);
            imageView = v.findViewById(R.id.poster_image);
            view=v;

        }
        void setclick(final CustomItemClickListener click, final Movie pos){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click.onItemClick(pos);
                }
            });
        }
    }
}
