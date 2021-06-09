package com.digitalhouse.moviewallet.model


import com.google.gson.annotations.SerializedName

data class CreditMovieResponse(
    @SerializedName("cast")
    val cast: List<Actor>?,
    @SerializedName("crew")
    val crew: List<Crew>?,
    @SerializedName("id")
    val id: Int?
)