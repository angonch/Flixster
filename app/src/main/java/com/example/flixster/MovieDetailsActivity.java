package com.example.flixster;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.databinding.ActivityMovieDetailsBinding;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;

public class MovieDetailsActivity extends AppCompatActivity {

    Movie movie; // movie to display

    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    ImageView ivBackdrop;

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
        ivBackdrop = binding.ivBackdrop;

        // //unwrap movie passed in from intent
        movie = Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        // set title, overview, and rating (divide 0-10 vote average by 2 to get 0-5)
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating((voteAverage > 0) ? (voteAverage / 2.0f) : voteAverage);
        Glide.with(getApplicationContext()).load(movie.getBackdropPath()).transform(new CenterInside(), new RoundedCornersTransformation(30, 10)).into(ivBackdrop);

        String VIDEO_URL = "https://api.themoviedb.org/3/movie/" + movie.getId().toString() + "/videos?api_key=c622cdaa84b981fece025799de236e2b";
        // movie video api call
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(VIDEO_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d("MovieDetailsActivity", "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i("MovieDetailsActivity", "Results: " + results.toString());
                    //parse results array
                    String videoID = results.getJSONObject(0).getString("key");
                    //make intent, .putextra("videoID", videoID)
                    final Intent intent = new Intent(getApplicationContext(), MovieTrailerActivity.class);
                    intent.putExtra("videoID", videoID);
                    ivBackdrop.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //play movie trailer activity
                            startActivity(intent);
                        }
                    });
                    Log.i("MovieDetailsActivity", "Video ID: " + videoID);
                } catch (JSONException e) {
                    Log.e("MovieDetailsActivity", "Hit json exception", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d("MovieDetailsActivity", "onFailure");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}