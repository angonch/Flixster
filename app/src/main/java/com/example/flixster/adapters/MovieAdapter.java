package com.example.flixster.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    //  inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        // Inflate the item_movie XML to get a View
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        // Wrap view inside a holder
        return new ViewHolder(movieView);
    }

    // Populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder: " + position);
        // Get the movie at the position
        Movie movie = movies.get(position);
        // Bind the movie data into the holder
        holder.bind(movie);
    }

    // Returns total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);

        }

        // Populate each views with movie properties
        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            // Render poster image with glide and into the poster view
            Glide.with(context).load(movie.getPosterPath()).into(ivPoster);
        }
    }
}
