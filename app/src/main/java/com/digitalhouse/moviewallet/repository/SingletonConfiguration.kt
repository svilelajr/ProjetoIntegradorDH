package com.digitalhouse.moviewallet.repository

import com.digitalhouse.moviewallet.model.MovieConfiguration

object SingletonConfiguration {
    var config: MovieConfiguration? = null
    fun setConfiguration(configuration: MovieConfiguration){
        config = configuration
    }

//    val imageUrl = "${configuration?.images?.base_url}${configuration?.images?.backdrop_sizes?.last()}${position.backdropPath}"
    fun getImageUrl():String{
        val imageUrl = ""
        return imageUrl
    }
}