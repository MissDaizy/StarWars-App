package com.diana_ukrainsky.starwars.ui.callback;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.diana_ukrainsky.starwars.data.model.Movie;

import java.util.List;

public class MovieDiffCallback extends DiffUtil.Callback {
    private final List<Movie> mOldMovieList;
    private final List<Movie> mNewMovieList;

    public MovieDiffCallback(List<Movie> mOldMovieList, List<Movie> mNewMovieList) {
        this.mOldMovieList = mOldMovieList;
        this.mNewMovieList = mNewMovieList;
    }

    @Override
    public int getOldListSize() {
        return mOldMovieList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewMovieList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldMovieList.get(oldItemPosition).equals(mNewMovieList.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Movie oldMovie = mOldMovieList.get(oldItemPosition);
        final Movie newMovie = mNewMovieList.get(newItemPosition);

        return oldMovie.equals(newMovie);
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
