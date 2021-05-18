package com.digitalhouse.moviewallet.model


import com.google.gson.annotations.SerializedName

data class Providers(
    @SerializedName("BR")
    val bR: BR?,
    @SerializedName("US")
    val uS: US?,

)