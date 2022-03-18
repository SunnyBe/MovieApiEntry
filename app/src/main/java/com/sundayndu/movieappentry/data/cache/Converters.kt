package com.sundayndu.movieappentry.data.cache

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.sundayndu.movieappentry.model.Genre

class Converters {
    @TypeConverter
    fun genreListToString(genre: List<Genre>?): String? {
        return genre?.let {
            Gson().toJson(genre)
        }
    }

    @TypeConverter
    fun genreListFromString(genre: String?): List<Genre>? {
        return genre?.let {
            Gson().fromJson(genre, Array<Genre>::class.java).asList()
        }
    }
}