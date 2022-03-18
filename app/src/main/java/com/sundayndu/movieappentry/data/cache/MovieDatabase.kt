package com.sundayndu.movieappentry.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.sundayndu.movieappentry.model.Movie

@Database(
    entities = [
        Movie::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}