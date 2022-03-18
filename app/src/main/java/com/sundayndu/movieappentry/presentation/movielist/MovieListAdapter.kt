package com.sundayndu.movieappentry.presentation.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sundayndu.movieappentry.BuildConfig
import com.sundayndu.movieappentry.R
import com.sundayndu.movieappentry.databinding.MovieItemBinding
import com.sundayndu.movieappentry.model.Movie
import com.sundayndu.movieappentry.utils.Constants

class MovieListAdapter(val onMovieSelected: (Movie)-> Unit) : ListAdapter<Movie, MovieListAdapter.MovieViewHolder>(DIFF_UTIL) {
    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

    class MovieViewHolder(private val movieItemBinding: MovieItemBinding) :
        RecyclerView.ViewHolder(movieItemBinding.root) {
            fun bind(movie: Movie) {
                movieItemBinding.movieTitle.text = movie.title
                movieItemBinding.movieSummary.text = movie.overview
                movieItemBinding.movieLabel.text = movie.dbMovieType

                Glide
                    .with(movieItemBinding.root.context)
                    .load(Constants.makeImageUrl(movie.posterPath))
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(movieItemBinding.movieIcon)
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val selectedMovie = getItem(position)
        holder.bind(selectedMovie)
        holder.itemView.setOnClickListener {
            onMovieSelected(selectedMovie)
        }
    }
}