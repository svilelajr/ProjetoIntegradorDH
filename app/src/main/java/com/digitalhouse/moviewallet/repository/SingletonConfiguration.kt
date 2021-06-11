package com.digitalhouse.moviewallet.repository

import com.digitalhouse.moviewallet.model.MovieConfigurationResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object SingletonConfiguration {
    var config: MovieConfigurationResponse? = null
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestoreDb = Firebase.firestore


    fun setConfiguration(configuration: MovieConfigurationResponse){
        config = configuration
    }

    fun getFavoriteDataValidation(): Boolean {

        var validation = false

        firebaseAuth.currentUser?.let { user ->
            firestoreDb.collection("users")
                .document(user.uid).get()
                .addOnSuccessListener {
                    if (it.data?.get("favoriteList") == null) {
                        validation = true
                    }

                }.addOnFailureListener {
                    it
                }
        }
        return validation
    }
}