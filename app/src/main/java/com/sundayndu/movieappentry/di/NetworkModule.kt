package com.sundayndu.movieappentry.di

import androidx.room.RoomDatabase
import com.sundayndu.movieappentry.BuildConfig
import com.sundayndu.movieappentry.data.cache.MovieDatabase
import com.sundayndu.movieappentry.data.network.NetworkService
import com.sundayndu.movieappentry.data.network.TokenInterceptor
import com.sundayndu.movieappentry.data.repository.MovieRepoImpl
import com.sundayndu.movieappentry.data.repository.MovieRepository
import com.sundayndu.movieappentry.di.qualifier.DefaultDispatcher
import com.sundayndu.movieappentry.di.qualifier.IoDispatcher
import com.sundayndu.movieappentry.utils.Constants.NETWORK_REQUEST_TIMEOUT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient())
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkService(retrofit: Retrofit): NetworkService {
        return retrofit.create(NetworkService::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(networkService: NetworkService, databaseService: MovieDatabase, @IoDispatcher appDispatcher: CoroutineDispatcher): MovieRepository {
        return MovieRepoImpl(networkService, databaseService, appDispatcher)
    }


    private fun httpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        return OkHttpClient.Builder()
            .readTimeout(NETWORK_REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(NETWORK_REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(TokenInterceptor(BuildConfig.APP_TOKEN))
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }
}