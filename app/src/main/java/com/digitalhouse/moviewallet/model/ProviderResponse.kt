package com.digitalhouse.moviewallet.model


import com.google.gson.annotations.SerializedName

data class ProviderResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("results")
    val providers: Providers?
)