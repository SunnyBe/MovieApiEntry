package com.sundayndu.movieappentry.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sundayndu.movieappentry.data.repository.MovieRepository
import com.sundayndu.movieappentry.di.qualifier.MainDispatcher
import com.sundayndu.movieappentry.model.Movie
import com.sundayndu.movieappentry.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    @MainDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _latestMovieList: MutableSharedFlow<ResultState<List<Movie>>> = MutableSharedFlow()
    val uIMovies: SharedFlow<ResultState<List<Movie>>> get() = _latestMovieList.asSharedFlow()

    fun latestMovie() {
        viewModelScope.launch(dispatcher) {
            movieRepository.emitCachedLatestAndUpdate()
                .collectLatest { result ->
                    _latestMovieList.emit(result)
                }
        }
    }

    fun popularMovies() {
        viewModelScope.launch(dispatcher) {
            movieRepository.emitCachedPopularAndUpdate()
                .collectLatest { result ->
                    _latestMovieList.emit(result)
                }
        }
    }

    fun upcomingMovies() {
        viewModelScope.launch(dispatcher) {
            movieRepository.emitCachedUpcomingAndUpdate()
                .collectLatest { result ->
                    _latestMovieList.emit(result)
                }
        }
    }

    fun allMovies() {
        viewModelScope.launch(dispatcher) {
            val movies = movieRepository.emitAllCachedMovie()
            _latestMovieList.emit(movies)
        }
    }
}