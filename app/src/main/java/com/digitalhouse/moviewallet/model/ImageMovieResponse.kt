package com.digitalhouse.moviewallet.model


import com.google.gson.annotations.SerializedName

data class ImageMovieResponse(
    @SerializedName("backdrops")
    val backdrops: List<Backdrop>?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("posters")
    val posters: List<Any>?
)