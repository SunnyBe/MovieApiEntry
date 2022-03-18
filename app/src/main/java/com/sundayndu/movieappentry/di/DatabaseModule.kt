package com.sundayndu.movieappentry.di

import android.content.Context
import androidx.room.Room
import com.sundayndu.movieappentry.data.cache.MovieDatabase
import com.sundayndu.movieappentry.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(context, MovieDatabase::class.java, Constants.DB_NAME)
            .build()
    }
}