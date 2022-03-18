package com.sundayndu.movieappentry.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sundayndu.movieappentry.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    suspend fun selectAllMovie(): List<Movie>

    @Query("SELECT * FROM movie WHERE dbMovieType=:movieType")
    suspend fun selectMoviesByType(movieType: String): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(vararg movies: Movie)

    @Query("DELETE FROM movie WHERE dbMovieType=:movieType")
    suspend fun deletePreviousLatest(movieType: String="LATEST")
}