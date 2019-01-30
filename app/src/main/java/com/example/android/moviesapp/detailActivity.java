package com.example.android.moviesapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.Date;

public class detailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView tvOriginalTitle = (TextView) findViewById(R.id.textview_original_title);
        ImageView ivPoster = (ImageView) findViewById(R.id.imageview_poster);
        TextView tvOverView = (TextView) findViewById(R.id.textview_overview);
        TextView tvVoteAverage = (TextView) findViewById(R.id.textview_vote_average);
        TextView tvReleaseDate = (TextView) findViewById(R.id.textview_release_date);

        Intent intentThatStartedThisActivity = getIntent();
        movie movie = intentThatStartedThisActivity.getParcelableExtra("movie");
        tvOriginalTitle.setText(movie.getName());

        String posterPath = movie.getPath();
        String fullPath = movieListFetcher.POSTER_BASE_URL_STRING + movieListFetcher.POSTER_SIZE_W185 + posterPath;
        //Log.d(TAG, fullPath);

        Picasso.get()
                .load(fullPath).into(ivPoster);

        String overView = movie.getPlotSynopsis();
        if (overView == null) {
            tvOverView.setTypeface(null, Typeface.ITALIC);
            overView = getResources().getString(R.string.no_summary_found);
        }
        tvOverView.setText(overView);

        tvVoteAverage.setText(Double.toString(movie.getVoteAverage()));


        String releaseDate = movie.getReleaseDate();
        if(releaseDate != null) {
            tvReleaseDate.setText(releaseDate);
        } else {
            tvReleaseDate.setTypeface(null, Typeface.ITALIC);
            releaseDate = getResources().getString(R.string.no_release_date_found);
        }

    }
}

