package com.sundayndu.movieappentry.utils

import com.sundayndu.movieappentry.model.Genre
import com.sundayndu.movieappentry.model.Movie

object MovieTestModels {
    val latestMovie = listOf(
        Movie(
            id = 413323,
            adult = false,
            genres = listOf(Genre(99, "Documentary")),
            imdbId = "tt5852644",
            originalTitle = "Deadpool: From Comics to Screen... to Screen",
            originalLanguage = "en",
            title = "Deadpool: From Comics to Screen... to Screen",
            overview = "One long boring long test",
            posterPath = "/chV0avy5ogIB2PMTInT4KpHDzwj.jpg",
            status = "Released",
            releaseDate = "2016-05-10",
            popularity = 0.0,
            dbMovieType = "LATEST"
        )
    )

    val popularMovies = listOf(
        Movie(
            id = 413323,
            adult = false,
            genres = listOf(Genre(99, "Documentary")),
            imdbId = "tt5852644",
            originalTitle = "Deadpool: From Comics to Screen... to Screen",
            originalLanguage = "en",
            title = "Deadpool: From Comics to Screen... to Screen",
            overview = "One long boring long test",
            posterPath = "/chV0avy5ogIB2PMTInT4KpHDzwj.jpg",
            status = "Released",
            releaseDate = "2016-05-10",
            popularity = 10.99,
            dbMovieType = "LATEST"
        )
    )
}