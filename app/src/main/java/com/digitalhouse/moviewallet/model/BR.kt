package com.digitalhouse.moviewallet.model


import com.google.gson.annotations.SerializedName

data class BR(
    @SerializedName("buy")
    val buy: List<Buy>?,
    @SerializedName("flatrate")
    val flatrate: List<Flatrate>?,
    @SerializedName("link")
    val link: String?,
    @SerializedName("rent")
    val rent: List<Rent>?
)