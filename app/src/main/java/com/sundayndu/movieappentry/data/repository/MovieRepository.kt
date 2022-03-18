package com.sundayndu.movieappentry.data.repository

import com.sundayndu.movieappentry.model.Movie
import com.sundayndu.movieappentry.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun emitCachedLatestAndUpdate(): Flow<ResultState<List<Movie>>>
    suspend fun emitCachedPopularAndUpdate(): Flow<ResultState<List<Movie>>>
    suspend fun emitCachedUpcomingAndUpdate(): Flow<ResultState<List<Movie>>>
    suspend fun emitAllCachedMovie(): ResultState<List<Movie>>
}