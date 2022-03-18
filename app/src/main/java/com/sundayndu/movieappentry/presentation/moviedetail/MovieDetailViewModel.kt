package com.sundayndu.movieappentry.presentation.moviedetail

import androidx.lifecycle.ViewModel
import com.sundayndu.movieappentry.data.repository.MovieRepository
import com.sundayndu.movieappentry.di.qualifier.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    val movieRepository: MovieRepository,
    @MainDispatcher val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

}