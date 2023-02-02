package com.diana_ukrainsky.starwars.ui.movie_list.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.diana_ukrainsky.starwars.data.model.Movie;
import com.diana_ukrainsky.starwars.databinding.MovieItemBinding;
import com.diana_ukrainsky.starwars.ui.callback.CustomItemClickListener;
import com.diana_ukrainsky.starwars.ui.callback.MovieDiffCallback;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private MovieItemBinding movieItemBinding;
    private List<Movie> movieList;
    private CustomItemClickListener customItemClickListener;

    private Context context;

    public MoviesAdapter(Context context) {
        this.customItemClickListener=(CustomItemClickListener)context;
        this.movieList = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        movieItemBinding=MovieItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolder(movieItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        MyViewHolder myViewHolder =(MyViewHolder) holder;
        setListeners(myViewHolder,movie);
        myViewHolder.movieItemBinding.movieItemTXTTitle.setText(movie.getTitle());
        myViewHolder.movieItemBinding.movieItemTXTReleaseDate.setText(movie.getReleaseDate());
        myViewHolder.movieItemBinding.movieItemTXTSummary.setText(movie.getSummary());
    }

    private void setListeners(MyViewHolder myViewHolder, Movie movie) {
        myViewHolder.movieItemBinding.movieItemCVMovieItemCard.setOnClickListener(v -> {
            customItemClickListener.onClick(movie);
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
    public void updateRecipeListItems(List<Movie> movieList) {
        final MovieDiffCallback diffCallback = new MovieDiffCallback(this.movieList, movieList);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.movieList.clear();
        this.movieList.addAll(movieList);
        this.notifyDataSetChanged();
        diffResult.dispatchUpdatesTo(this);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private MovieItemBinding movieItemBinding;

        public MyViewHolder(MovieItemBinding movieItemBinding) {
            super(movieItemBinding.getRoot());
            this.movieItemBinding=movieItemBinding;
        }
    }
}
