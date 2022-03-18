package com.sundayndu.movieappentry.model

import com.google.gson.annotations.SerializedName

data class MovieListResponse(
    val id: Long,
    val page: Long,
    val results: List<Movie>?,
    @SerializedName("total_pages")
    val totalPages: Long,
    @SerializedName("total_results")
    val totalResults: Long
)
