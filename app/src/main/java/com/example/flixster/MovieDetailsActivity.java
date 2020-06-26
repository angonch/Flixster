package com.example.flixster;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flixster.databinding.ActivityMovieDetailsBinding;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {

    Movie movie; // movie to display

    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;

    ActivityMovieDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.DarkTheme);
        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // view objects
        tvTitle = binding.tvTitle;
        tvOverview = binding.tvOverview;
        rbVoteAverage = binding.rbVoteAverage;

        // //unwrap movie passed in from intent
        movie = Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        // set title, overview, and rating (divide 0-10 vote average by 2 to get 0-5)
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating((voteAverage > 0) ? (voteAverage / 2.0f) : voteAverage);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}