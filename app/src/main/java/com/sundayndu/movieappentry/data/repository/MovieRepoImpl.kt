package com.sundayndu.movieappentry.data.repository

import com.sundayndu.movieappentry.data.cache.MovieDatabase
import com.sundayndu.movieappentry.data.network.NetworkService
import com.sundayndu.movieappentry.di.qualifier.IoDispatcher
import com.sundayndu.movieappentry.model.Movie
import com.sundayndu.movieappentry.utils.ResultState
import com.sundayndu.movieappentry.utils.extensions.onStartAndErrorResultState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepoImpl @Inject constructor(
    private val networkService: NetworkService,
    private val dbService: MovieDatabase,
    @IoDispatcher val dispatcher: CoroutineDispatcher
) : MovieRepository {

    override suspend fun emitCachedLatestAndUpdate(): Flow<ResultState<List<Movie>>> =
        flow<ResultState<List<Movie>>> {
            val latestMovie = networkService.latestMovie().copy(dbMovieType = "LATEST")
            // Only one instance of latest movie is allowed in DB. So clear existing latest before update.
            dbService.movieDao().deletePreviousLatest()
            dbService.movieDao().insertMovies(latestMovie)
            val cachedLatest = dbService.movieDao().selectMoviesByType("LATEST")
            emit(ResultState.Success(cachedLatest))
        }
            .onStartAndErrorResultState(dispatcher) {
                dbService.movieDao().selectMoviesByType("LATEST")
            }

    override suspend fun emitCachedPopularAndUpdate(): Flow<ResultState<List<Movie>>> =
        flow<ResultState<List<Movie>>> {
            networkService.popularMovies().results
                ?.map { movie -> movie.copy(dbMovieType = "POPULAR") }
                ?.let { popularMovies ->
                    dbService.movieDao().insertMovies(movies = popularMovies.toTypedArray())
                }
            val cachedLatest = dbService.movieDao().selectMoviesByType("POPULAR")
            emit(ResultState.Success(cachedLatest))
        }
            .onStartAndErrorResultState(dispatcher) {
                dbService.movieDao().selectMoviesByType("POPULAR")
            }

    override suspend fun emitCachedUpcomingAndUpdate(): Flow<ResultState<List<Movie>>> =
        flow<ResultState<List<Movie>>> {
            networkService.upcomingMovies().results
                ?.map { it.copy(dbMovieType = "UPCOMING") }
                ?.let { movies ->
                    dbService.movieDao().insertMovies(movies = movies.toTypedArray())
                }
            val cachedLatest = dbService.movieDao().selectMoviesByType("UPCOMING")
            emit(ResultState.Success(cachedLatest))
        }
            .onStart {
                val cachedLatest = dbService.movieDao().selectMoviesByType("UPCOMING")
                emit(ResultState.Loading(cachedLatest))
            }
            .catch { err ->
                emit(ResultState.Error(err))
            }
            .flowOn(dispatcher)

    override suspend fun emitAllCachedMovie(): ResultState<List<Movie>> {
        return withContext(dispatcher) {
            try {
                val result = dbService.movieDao().selectAllMovie()
                ResultState.Success(result)
            } catch (e: Exception) {
                ResultState.Error(e)
            }
        }
    }
}
