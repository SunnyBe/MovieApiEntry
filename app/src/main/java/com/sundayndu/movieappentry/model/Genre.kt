package com.sundayndu.movieappentry.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genre(
    val id: Long,
    val name: String?
): Parcelable
