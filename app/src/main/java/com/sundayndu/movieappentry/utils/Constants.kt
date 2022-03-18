package com.sundayndu.movieappentry.utils

object Constants {
    const val MOVIE_DATA: String = "movie_data"
    const val NETWORK_REQUEST_TIMEOUT = 3L
    const val DB_NAME = "umba_movie_db"

    fun makeImageUrl(path: String?, size: String?="w500"): String? {
        return path?.let {
            "https://image.tmdb.org/t/p/$size$path"
        }
    }

}