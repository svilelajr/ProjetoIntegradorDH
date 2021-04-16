package com.digitalhouse.moviewallet.model

import java.io.Serializable

data class Genre(
    val id: Int?,
    val name: String?,
    var movies: MutableList<MovieRecycler>?
):Serializable