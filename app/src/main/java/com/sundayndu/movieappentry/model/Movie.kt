package com.sundayndu.movieappentry.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Movie(
    @PrimaryKey
    val id: Int,
    val adult: Boolean,
    val genres: List<Genre>?,
    @SerializedName("imdb_id")
    val imdbId: String?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    val title: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    val status: String?,
    val overview: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    val popularity: Double? = 0.0,
    val dbMovieType: String? = ""
) : Parcelable
