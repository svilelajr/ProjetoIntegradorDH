package com.digitalhouse.moviewallet.repository

import com.digitalhouse.moviewallet.model.MovieConfigurationResponse

object SingletonConfiguration {
    var config: MovieConfigurationResponse? = null
    fun setConfiguration(configuration: MovieConfigurationResponse){
        config = configuration
    }
}