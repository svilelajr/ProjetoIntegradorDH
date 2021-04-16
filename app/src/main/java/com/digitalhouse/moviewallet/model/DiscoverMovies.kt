package com.digitalhouse.moviewallet.model


import com.google.gson.annotations.SerializedName

data class DiscoverMovies(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val movies: List<MovieRecycler>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)