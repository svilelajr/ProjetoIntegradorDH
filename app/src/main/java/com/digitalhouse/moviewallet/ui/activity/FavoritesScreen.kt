package com.digitalhouse.moviewallet.ui.activity

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.R
import com.digitalhouse.moviewallet.repository.SingletonConfiguration
import com.digitalhouse.moviewallet.ui.adapter.FavoritesAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text
import kotlin.collections.ArrayList

class FavoritesScreen : AppCompatActivity() {

    private val rvFavorite by lazy { findViewById<RecyclerView>(R.id.rv_movie_screen) }
    private val toolbar by lazy { findViewById<Toolbar>(R.id.tb_movies) }
    private val tvNotification by lazy { findViewById<TextView>(R.id.tv_tratativafav) }
    private val firestoreDb = Firebase.firestore
    private var favorites: MutableList<HashMap<String, *>> = mutableListOf()
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

        supportActionBar?.title = "Favoritos"

        getFavoriteData()
    }

    fun getFavoriteData() {
        firebaseAuth.currentUser?.let { user ->
            firestoreDb.collection("users")
                .document(user.uid).get()
                .addOnSuccessListener {
                    if (it.data?.get("favoriteList") != null) {
                        val result = it.data?.get("favoriteList")
                        result as ArrayList<HashMap<String, *>>
                        favorites.addAll(result)
                        adapterFavorite = FavoritesAdapter(favorites)
                        rvFavorite.adapter = adapterFavorite
                    } else {
                        tvNotification.visibility = View.VISIBLE
                        favorites = mutableListOf()
                        adapterFavorite = FavoritesAdapter(favorites)
                        rvFavorite.adapter = adapterFavorite
                    }
                }.addOnFailureListener {
                    it
                }
        }
    }
}