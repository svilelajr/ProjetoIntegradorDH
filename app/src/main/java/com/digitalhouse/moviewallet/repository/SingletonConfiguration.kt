package com.digitalhouse.moviewallet.repository

import com.digitalhouse.moviewallet.model.MovieConfiguration

object SingletonConfiguration {
    var config: MovieConfiguration? = null
    fun setConfiguration(configuration: MovieConfiguration){
        config = configuration
    }
}