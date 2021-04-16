package com.digitalhouse.moviewallet.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MovieRecycler(
    @SerializedName("genre_ids")
    val genreIds: List<Int>?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("poster_path")
    val posterPath: String?
) : Serializable
