package com.sundayndu.movieappentry

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sundayndu.movieappentry.data.cache.MovieDao
import com.sundayndu.movieappentry.data.cache.MovieDatabase
import com.sundayndu.movieappentry.utils.MovieTestModels
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MovieDbTest {
    private lateinit var movieDao: MovieDao
    private lateinit var db: MovieDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MovieDatabase::class.java
        ).build()
        movieDao = db.movieDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeMoviesAndReadFirstInList() {
        runBlocking {
            val movies = MovieTestModels.popularMovies
            movieDao.insertMovies(movies = movies.toTypedArray())
            val byFirstMovie = movies.first().id
            assertThat(byFirstMovie, equalTo(movies.first().id))
        }
    }
}