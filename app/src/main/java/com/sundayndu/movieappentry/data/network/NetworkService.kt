package com.sundayndu.movieappentry.data.network

import com.sundayndu.movieappentry.model.Movie
import com.sundayndu.movieappentry.model.MovieListResponse
import retrofit2.http.GET

interface NetworkService {
    @GET("movie/latest")
    suspend fun latestMovie(): Movie

    @GET("movie/popular")
    suspend fun popularMovies(): MovieListResponse

    @GET("movie/upcoming")
    suspend fun upcomingMovies(): MovieListResponse
}