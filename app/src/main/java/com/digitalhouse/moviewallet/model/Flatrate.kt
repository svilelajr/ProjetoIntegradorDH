package com.digitalhouse.moviewallet.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Flatrate(
    @SerializedName("display_priority")
    val displayPriority: Int?,
    @SerializedName("logo_path")
    val logoPath: String?,
    @SerializedName("provider_id")
    val providerId: Int?,
    @SerializedName("provider_name")
    val providerName: String?
):Serializable