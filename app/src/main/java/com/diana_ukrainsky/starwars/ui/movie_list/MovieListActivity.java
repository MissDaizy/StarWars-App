package com.diana_ukrainsky.starwars.ui.movie_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.diana_ukrainsky.common.Constants;
import com.diana_ukrainsky.starwars.R;
import com.diana_ukrainsky.starwars.data.model.Movie;
import com.diana_ukrainsky.starwars.databinding.ActivityMovieListBinding;
import com.diana_ukrainsky.starwars.ui.callback.CustomItemClickListener;
import com.diana_ukrainsky.starwars.ui.movie_details.MovieDetailsActivity;
import com.diana_ukrainsky.starwars.ui.movie_details.MovieDetailsEvent;
import com.diana_ukrainsky.starwars.ui.movie_details.MovieDetailsViewModel;
import com.diana_ukrainsky.starwars.ui.movie_list.adapter.MoviesAdapter;
import com.google.gson.Gson;

import java.util.List;

public class MovieListActivity extends AppCompatActivity implements CustomItemClickListener, LifecycleOwner {
    private MovieListViewModel movieListViewModel;
    private MovieDetailsViewModel movieDetailsViewModel;
    private RecyclerView recyclerView;
    private MoviesAdapter moviesAdapter;
    private ProgressBar progressBar;

    private ActivityMovieListBinding activityContactListBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        activityContactListBinding = ActivityMovieListBinding.inflate(getLayoutInflater());
        View view = activityContactListBinding.getRoot();
        setContentView(view);

        setViewModels();
        setViews();
        setObservers();

        setListeners();
        setRecyclerView();
        setAdapter();
        setMenuItemListUI();
    }

    private void setViewModels() {
        movieListViewModel=new ViewModelProvider(this).get(MovieListViewModel.class);
        movieDetailsViewModel=new ViewModelProvider(this).get(MovieDetailsViewModel.class);
    }

    private void setViews() {
        progressBar=activityContactListBinding.activityMovieListPBProgressBar;
        activityContactListBinding.radioAscending.setChecked(true);
        activityContactListBinding.radioAll.setChecked(true);
    }

    private void setObservers() {
        movieListViewModel.getMovieListLiveData().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                moviesAdapter.updateRecipeListItems(movies);
            }
        });
        movieListViewModel.getFilteredMovieListLiveData().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                moviesAdapter.updateRecipeListItems(movies);
            }
        });
        movieListViewModel.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if(isLoading)
                    progressBar.setVisibility(View.VISIBLE);
                else
                    progressBar.setVisibility(View.GONE);
            }
        });
        movieDetailsViewModel.getMovieDetailsLiveData().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                if(movie!=null) {
                    startMovieDetailsActivity(movie);
                }
            }
        });
//        movieListViewModel.getCurrentSearchTextLiveData().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                movieListViewModel.onEventMovieList(MovieListEvent.SEARCH,s);
//            }
//        });
    }

    private void startMovieDetailsActivity(Movie movie) {
        String movieDetailsJson=new Gson().toJson(movie);
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle=new Bundle();
        bundle.putString(Constants.ITEM_DETAILS,movieDetailsJson);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void setListeners() {


    }

    private void setRecyclerView() {
        recyclerView = activityContactListBinding.activityMovieListRVRecyclerView;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void setAdapter() {
        moviesAdapter = new MoviesAdapter(this);
        recyclerView.setAdapter(moviesAdapter);
    }

    private void setMenuItemListUI() {
        movieListViewModel.getMovies();
    }

    @Override
    public void onClick(Object object) {
        movieDetailsViewModel
                .onEventMovieDetails(
                        MovieDetailsEvent.GET_MOVIE_DETAILS,
                        object
                );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        movieListViewModel.disposeComposite();
    }
}