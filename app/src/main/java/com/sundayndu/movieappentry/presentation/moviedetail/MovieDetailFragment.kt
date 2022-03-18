package com.sundayndu.movieappentry.presentation.moviedetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.sundayndu.movieappentry.BuildConfig
import com.sundayndu.movieappentry.R
import com.sundayndu.movieappentry.databinding.FragmentMovieDetailBinding
import com.sundayndu.movieappentry.model.Movie
import com.sundayndu.movieappentry.utils.Constants
import com.sundayndu.movieappentry.utils.extensions.makeInVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    private lateinit var binding: FragmentMovieDetailBinding

    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Movie>(Constants.MOVIE_DATA)?.let { movie ->
            presentMovieDetail(movie)
        }
    }

    private fun presentMovieDetail(movie: Movie) {
        Log.d(javaClass.simpleName, "$movie")
        Glide.with(requireContext())
            .load(Constants.makeImageUrl(movie.posterPath, "original"))
            .placeholder(R.drawable.ic_launcher_background)
            .centerInside()
            .override(600, 600)
            .into(binding.movieDetailImage)

        with(binding.movieDetailInclude) {
            detailTitle.text = movie.title
            detailRatingValue.text = movie.popularity.toString()
            detailLanguage.text = "Language: ${movie.originalLanguage?.uppercase()}"
            detailOverviewLabel.text = movie.overview
            detailReleaseDate.text = movie.releaseDate
            if (movie.adult) {
                detailIsAdult.apply {
                    makeInVisible
                    text = "Adult"
                }
            } else {
                detailIsAdult.makeInVisible
            }
        }
    }
}