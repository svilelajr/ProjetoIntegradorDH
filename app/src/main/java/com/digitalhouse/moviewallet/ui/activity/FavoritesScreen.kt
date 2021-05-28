package com.digitalhouse.moviewallet.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.R
import com.digitalhouse.moviewallet.model.MovieFireDb
import com.digitalhouse.moviewallet.model.User
import com.digitalhouse.moviewallet.ui.adapter.FavoritesAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.collections.ArrayList

class FavoritesScreen : AppCompatActivity() {

    private val rvFavorite by lazy { findViewById<RecyclerView>(R.id.rv_movie_screen) }
    private val toolbar by lazy { findViewById<Toolbar>(R.id.tb_movies) }
    private val firestoreDb = Firebase.firestore
    private val favorites: MutableList<MovieFireDb> = mutableListOf()
    private lateinit var user: User
    private lateinit var adapterFavorite: FavoritesAdapter
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.context_screen)

        firebaseAuth = FirebaseAuth.getInstance()

        initViews()
    }

    private fun initViews() {
        rvFavorite.layoutManager = GridLayoutManager(this, 2)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener { onBackPressed() }

        supportActionBar?.title = "Favorites"

        getFavoriteData()
//        if (favorites.isNotEmpty()) {
//            adapterFavorite = FavoritesAdapter(favorites)
//            rvFavorite.adapter = adapterFavorite
//        }
    }

    fun getFavoriteData() {
        firebaseAuth.currentUser?.let { user ->
            firestoreDb.collection("users")
                .document(user.uid).get()
                .addOnSuccessListener { it ->
                    val result = it.data?.get("favoriteList")
                    result as ArrayList<HashMap<String, MovieFireDb>>
                    adapterFavorite = FavoritesAdapter(favorites)
                    rvFavorite.adapter = adapterFavorite
                }.addOnFailureListener {
                    it
                }
        }
    }



//    fun readData() {
//        firebaseAuth.currentUser.let { user ->
//            firestoreDb.collection("users")
//                .document(user.uid).get()
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        val list = ArrayList<String>()
//                        for (document in task.result) {
//                            val name = document.data["name"].toString()
//                            list.add(name)
//                        }
//                        //Do what you need to do with your list
//                    }
//                }
//        }
//    }
}