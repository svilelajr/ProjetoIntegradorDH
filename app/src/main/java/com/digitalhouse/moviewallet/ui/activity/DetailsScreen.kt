package com.digitalhouse.moviewallet.ui.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.TestLooperManager
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.R
import com.digitalhouse.moviewallet.model.*
import com.digitalhouse.moviewallet.repository.SingletonConfiguration
import com.digitalhouse.moviewallet.ui.adapter.ActorDetailsAdapter
import com.digitalhouse.moviewallet.ui.viewmodel.DetailsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import okhttp3.internal.userAgent
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import javax.security.auth.Subject

class DetailsScreen : AppCompatActivity() {
    private val toolbar by lazy { findViewById<androidx.appcompat.widget.Toolbar>(R.id.tb_details) }
    private val ivMovie by lazy { findViewById<ImageView>(R.id.iv_movie_details) }
    private val tvMovie by lazy { findViewById<TextView>(R.id.tv_title_details) }
    private val tvSynopsis by lazy { findViewById<TextView>(R.id.tv_synopsis_details) }
    private val tvGenre by lazy { findViewById<TextView>(R.id.tv_genre_details) }
    private val tvRating by lazy { findViewById<TextView>(R.id.tv_percentage_details) }
    private val rvActors by lazy { findViewById<RecyclerView>(R.id.rv_elenco) }
    private val btFavorite by lazy { findViewById<FloatingActionButton>(R.id.bt_favorite_details) }
    private val listActor = mutableListOf<Actor>()
    private val adapterActor = ActorDetailsAdapter(listActor)
    private val firestoreDb = Firebase.firestore
    private lateinit var viewModel: DetailsViewModel
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var movieDetail: MovieDetail


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_screen)
        viewModel = ViewModelProvider.NewInstanceFactory().create(DetailsViewModel::class.java)
        firebaseAuth = FirebaseAuth.getInstance()
        setSupportActionBar(toolbar)
        initViews()
        setupRecycler()
        initClick()

    }

    private fun initViews() {
        val extras = intent.extras
        val movieId = extras?.getInt("MOVIE_ID")
        if (movieId != null) {
            viewModel.getMovieDetail(movieId.toString())
            movieDetails()
            viewModel.getCreditMovie(movieId.toString())
            movieCredits()
        }
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun movieDetails() {
        viewModel.movieDetail.observe(this) {
            var movie = it
            val date = LocalDate.parse(it.releaseDate)
            val imageUrl: String = viewModel.getBackdropPath()
            tvMovie.text = it.title
            tvSynopsis.text = it.overview
            "(${date.year}) ${it.genres?.get(0)?.name} | ${it.runtime}min".also {
                tvGenre.text = it
            }
            "${viewModel.getPopularity()} %".also { tvRating.text = it }
            Picasso.get().load(imageUrl).into(ivMovie)
            getMovie(movie)
        }
    }

    private fun initClick() {
        btFavorite.setOnClickListener {
            if (firebaseAuth.currentUser != null) {
                addFavoriteToDb()

            } else {

                val builder = AlertDialog.Builder(this)
                builder.setTitle("Usuário Não Logado")
                builder.setMessage("Favoritos disponível somente para usuários Logados \n\nDeseja Logar?")
                builder.setPositiveButton("Sim") { _, _ ->
                    startActivity(Intent(this, LoginScreen::class.java))
                    finishAffinity()
                }

                builder.setNegativeButton("Não", null)
                builder.show()
            }
        }
    }

    private fun movieCredits() {
        viewModel.actorMovie.observe(this) {
            listActor.addAll(it)
            adapterActor.notifyDataSetChanged()
        }
    }

    private fun setupRecycler() {
        rvActors.adapter = adapterActor
        rvActors.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun addFavoriteToDb() {

        firebaseAuth.currentUser?.let { user ->
            firestoreDb.collection("users")
                .document(user.uid).update("favoriteList", FieldValue.arrayUnion(movieDetail))
                .addOnSuccessListener {
                    Log.d("TAG", "DocumentSnapshot successfully written!")
                }.addOnFailureListener { e ->
                    Log.w("TAG", "Error writing document", e)
                }
        }
    }

    private fun getMovie(movie: MovieDetail) {
        movieDetail = movie
    }
}