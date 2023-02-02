package com.diana_ukrainsky.starwars.ui.movie_details;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.diana_ukrainsky.common.Constants;
import com.diana_ukrainsky.starwars.R;
import com.diana_ukrainsky.starwars.data.model.Movie;
import com.diana_ukrainsky.starwars.databinding.ActivityMovieDetailsBinding;
import com.google.gson.Gson;

public class MovieDetailsActivity extends AppCompatActivity {

    private ActivityMovieDetailsBinding activityMovieDetailsBinding;

    private static final int HEIGHT=1000;
    private static final int WEIGHT=1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        activityMovieDetailsBinding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        View view =activityMovieDetailsBinding.getRoot();
        setContentView(view);

        setUI();
    }

    private void setUI() {
        Movie movie = getItemData();
        setMenuItemUI(movie);
    }


    private Movie getItemData() {
        Bundle bundle=getIntent().getExtras();
        String movieData = bundle.getString(Constants.ITEM_DETAILS);
        Movie movie = new Gson().fromJson(movieData,Movie.class);
        return movie;
    }

    private void setMenuItemUI(Movie movie) {
        activityMovieDetailsBinding.activityMovieDetailsTXTTitle.setText(movie.getTitle());
        activityMovieDetailsBinding.activityMovieDetailsTXTYear.setText(movie.getReleaseDate());
        activityMovieDetailsBinding.activityMovieDetailsTXTDirector.setText(movie.getDirector());
        activityMovieDetailsBinding.activityMovieDetailsTXTProducer.setText(movie.getProducer());
//        activityMovieDetailsBinding.activityMovieDetailsTXTCharacters.setText(movie.getCharacters().toString());
    }
}