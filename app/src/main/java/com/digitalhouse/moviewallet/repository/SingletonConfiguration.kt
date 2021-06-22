package com.digitalhouse.moviewallet.repository

import com.digitalhouse.moviewallet.model.MovieConfigurationResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object SingletonConfiguration {
    var config: MovieConfigurationResponse? = null

    fun setConfiguration(configuration: MovieConfigurationResponse){
        config = configuration
    }

}