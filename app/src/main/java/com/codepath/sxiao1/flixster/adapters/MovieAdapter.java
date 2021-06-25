package com.codepath.sxiao1.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.sxiao1.flixster.MovieDetailsActivity;
import com.codepath.sxiao1.flixster.R;
import com.codepath.sxiao1.flixster.models.Movie;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    // Involves populating the data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder" + position);
        // Get the movie at the passed in position
        Movie movie = movies.get(position);
        //Bind the movie data into the VH
        holder.bind(movie);
    }

    // Returns the total count of the items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            itemView.setOnClickListener(this);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            tvOverview.setMovementMethod(new ScrollingMovementMethod());
            String imageUrl;
            int placeholderIcon;
            // for rounded corners
            int radius = 30;
            int margin = 10;
            //if phone is in landscape
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageUrl = movie.getBackdropPath();
                placeholderIcon = R.drawable.flicks_backdrop_placeholder;
            } else {//if phone is in portrait
                imageUrl = movie.getPosterPath();
                placeholderIcon = R.drawable.flicks_movie_placeholder;
            }
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(placeholderIcon)
                    .centerCrop() // scale image to fill the entire ImageView
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .into(ivPoster);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            Movie movie = movies.get(position);
            Intent intent = new Intent(context, MovieDetailsActivity.class);
            intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
            context.startActivity(intent);

        }
    }
}
