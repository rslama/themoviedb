package com.slama.remote.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class MovieOverview(
    val id: Long,
    val originalTitle: String,
    val title: String,
    val overview: String,
    val backgroundImagePath: String,
    val posterImagePath: String,
    val releaseDate: String,
) : Parcelable