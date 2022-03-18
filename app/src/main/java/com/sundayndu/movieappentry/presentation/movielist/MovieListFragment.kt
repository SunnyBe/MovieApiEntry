package com.sundayndu.movieappentry.presentation.movielist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sundayndu.movieappentry.R
import com.sundayndu.movieappentry.databinding.FragmentMovieListBinding
import com.sundayndu.movieappentry.model.Movie
import com.sundayndu.movieappentry.presentation.MainViewModel
import com.sundayndu.movieappentry.utils.Constants
import com.sundayndu.movieappentry.utils.ResultState
import com.sundayndu.movieappentry.utils.extensions.makeInVisible
import com.sundayndu.movieappentry.utils.extensions.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListFragment : Fragment() {
    private lateinit var binding: FragmentMovieListBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val movieListAdapter = MovieListAdapter(::navigateToSelectedMovie)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.selectedMovieList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = movieListAdapter
        }
        mainViewModel.allMovies()
        binding.movieTypeToggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            when (group.checkedButtonId) {
                R.id.movie_type_latest_selection -> {
                    mainViewModel.latestMovie()
                }
                R.id.movie_type_upcoming_selection -> {
                    mainViewModel.upcomingMovies()
                }
                R.id.movie_type_popular_selection -> {
                    mainViewModel.popularMovies()
                }
                R.id.movie_type_all_selection -> {
                    mainViewModel.allMovies()
                }
                else -> {
                    Log.e(javaClass.simpleName, "Error with checkedId")
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.uIMovies.collectLatest(::presentResult)
            }
        }
    }

    private fun presentResult(resultState: ResultState<List<Movie>>) {
        when (resultState) {
            is ResultState.Success -> {
                binding.progressIndicator.makeInVisible
                if (resultState.data.isEmpty()) {
                    with(binding.emptyListLabel) {
                        makeVisible
                        text = requireContext().getString(R.string.empty_list_label)
                    }
                } else {
                    binding.emptyListLabel.makeInVisible
                    updateList(resultState.data)
                }
            }
            is ResultState.Error -> {
                binding.progressIndicator.makeInVisible
                resultState.error.printStackTrace()
                Snackbar.make(
                    binding.root,
                    resultState.error.message.toString(),
                    Snackbar.LENGTH_LONG
                ).show()
            }
            is ResultState.Loading -> {
                binding.progressIndicator.makeVisible
                resultState.data?.let {
                    if (resultState.data.isEmpty()) {
                        with(binding.emptyListLabel) {
                            makeVisible
                            text = requireContext().getString(R.string.empty_list_label)
                        }
                    } else {
                        binding.emptyListLabel.makeInVisible
                        updateList(resultState.data)
                    }
                }
            }
        }
    }

    private fun updateList(movies: List<Movie>) {
        movieListAdapter.submitList(movies)
        movieListAdapter.notifyDataSetChanged()
    }

    private fun navigateToSelectedMovie(movie: Movie) {
        val bundle = Bundle().apply {
            putParcelable(Constants.MOVIE_DATA, movie)
        }
        findNavController().navigate(R.id.action_movieListFragment_to_movieDetailFragment, bundle)
    }

}
