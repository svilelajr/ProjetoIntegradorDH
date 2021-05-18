package com.digitalhouse.moviewallet.model


import com.google.gson.annotations.SerializedName

data class PopularMovie(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val movie: List<Movie>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)