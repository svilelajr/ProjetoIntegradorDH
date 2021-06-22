package com.digitalhouse.moviewallet.ui.activity

import android.content.Intent
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.R
import com.digitalhouse.moviewallet.model.*
import com.digitalhouse.moviewallet.model.Movie
import com.digitalhouse.moviewallet.ui.adapter.DetailsScreenSimiliarAdapter
import com.digitalhouse.moviewallet.ui.adapter.ProviderDetailsAdapter
import com.digitalhouse.moviewallet.ui.viewmodel.DetailsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import java.time.LocalDate

class DetailsScreen : AppCompatActivity() {
    private val toolbar by lazy { findViewById<androidx.appcompat.widget.Toolbar>(R.id.tb_details) }
    private val ivBackgroundMovie by lazy { findViewById<ImageView>(R.id.iv_moviebackground_details) }
    private val ivMovie by lazy { findViewById<ImageView>(R.id.iv_movie_details) }
    private val ivJustWatch by lazy { findViewById<ImageView>(R.id.iv_just_watch) }
    private val tvSynopsis by lazy { findViewById<TextView>(R.id.tv_synopsis_details) }
    private val tvDetailsMovie by lazy { findViewById<TextView>(R.id.tv_detailsmovie_details) }
    private val tvTitle by lazy { findViewById<TextView>(R.id.tv_title_details) }
    private val rvProvider by lazy { findViewById<RecyclerView>(R.id.rv_provider_details) }
    private val btFavorite by lazy { findViewById<FloatingActionButton>(R.id.bt_favorite_details) }
    private val tvProvider by lazy { findViewById<TextView>(R.id.tv_provider_details) }
    private val tvTitleSemelhante by lazy { findViewById<TextView>(R.id.tv_similar_details) }
    private val ratingBar by lazy { findViewById<RatingBar>(R.id.rb_details_screen) }
    private val rvSimilarMovie by lazy { findViewById<RecyclerView>(R.id.rv_similar_details) }


    private lateinit var viewModel: DetailsViewModel
    private lateinit var firebaseAuth: FirebaseAuth
    private val firestoreDb = Firebase.firestore
    private lateinit var movieDetailResponse: MovieDetailResponse
    private val movieIdsList: MutableList<Long> = mutableListOf()

    private val listProviders = mutableListOf<String>()
    private val listMovie = mutableListOf<Movie>()
    private val adapterProvider = ProviderDetailsAdapter(listProviders)
    private val adapterSimilarMovie = DetailsScreenSimiliarAdapter(listMovie)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_screen)
        viewModel = ViewModelProvider.NewInstanceFactory().create(DetailsViewModel::class.java)
        viewModel.imageBackgroundUrl.observe(this, { url ->
            Picasso.get().load(url).into(ivBackgroundMovie)
        })
        viewModel.imagePosterUrl.observe(this, {
            Picasso.get().load(it).into(ivMovie)
        })
        firebaseAuth = FirebaseAuth.getInstance()
        setSupportActionBar(toolbar)
        initViews()
        setupRecycler()
        initClick()

    }

    private fun initViews() {
        val extras = intent.extras
        val movieId = extras?.getString("MOVIE_ID")
        if (movieId != null) {
            viewModel.getMovieDetail(movieId)
            viewModel.getProvider(movieId)
            providerMovie()
            movieDetails()
            viewModel.getSimiliarMovie(movieId.toString())
            viewModel.getMoviesIdsOnDb(firebaseAuth, firestoreDb)
            getMoviesIds()
            similarMovie()
        }
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun movieDetails() {
        viewModel.movieDetail.observe(this) {
            movieDetailResponse = it

            val date = LocalDate.parse(it.releaseDate)
            tvTitle.text = ("${it.title}")

            if (it.overview.isNullOrBlank()) {
                tvSynopsis.text = "INFELIZMENTE A SINOPSE DO FILME AINDA NÃO FOI FORNECIDA"
            } else {
                tvSynopsis.text = it.overview
            }
            tvDetailsMovie.text = "${date.year} | ${it.runtime}min"
            ratingBar.rating = it.voteAverage?.toFloat()!! / 2f
        }
    }

    private fun initClick() {
        btFavorite.setOnClickListener {
            if (firebaseAuth.currentUser != null) {

                if (checkIfMovieIsOnDb()) {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Filme ja existe nos Favoritos")
                    builder.setMessage("Deseja remover o filme dos Favoritos?")
                    builder.setPositiveButton("Sim") { _, _ ->
                        removeMovieFromDb()
                    }
                    builder.setNegativeButton("Não", null)
                    builder.show()
                } else {
                    addFavoriteToDb()
                }
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

    private fun providerMovie() {
        viewModel.providers.observe(this) {
            if (it != null) {
                listProviders.addAll(it)
            }
            adapterProvider.notifyDataSetChanged()
            validateProvider()
        }
    }

    private fun similarMovie() {
        viewModel.similarMovies.observe(this) {
            if (it != null) {
                listMovie.addAll(it)
            }
            adapterSimilarMovie.notifyDataSetChanged()
            if (listMovie.isNotEmpty()) {
                rvSimilarMovie.visibility = VISIBLE
                tvTitleSemelhante.visibility = VISIBLE
            }
        }
    }

    private fun setupRecycler() {
        rvProvider.adapter = adapterProvider
        rvProvider.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        rvSimilarMovie.adapter = adapterSimilarMovie
        rvSimilarMovie.layoutManager =
            GridLayoutManager(this, 2)
    }

    private fun validateProvider() {
        if (listProviders.isNotEmpty()) {
            rvProvider.visibility = VISIBLE
            tvProvider.visibility = VISIBLE
            ivJustWatch.visibility = VISIBLE
        }
    }

    private fun addFavoriteToDb() {
        firebaseAuth.currentUser?.let { user ->
            firestoreDb.collection("users")
                .document(user.uid)
                .update(
                    "favoriteList", FieldValue.arrayUnion(movieDetailResponse),
                    "moviesIds", FieldValue.arrayUnion(movieDetailResponse.id)
                )
                .addOnSuccessListener {
                    Log.d("TAG", "DocumentSnapshot successfully written!")
                    Toast.makeText(this, "Filme Adicionado aos Favoritos", Toast.LENGTH_LONG).show()
                }.addOnFailureListener { e ->
                    Log.w("TAG", "Error writing document", e)
                    Toast.makeText(
                        this,
                        "Não foi possivel adicionar o Filme aos Favoritos",
                        Toast.LENGTH_LONG
                    ).show()
                }

        }
    }

    private fun getMoviesIds() {
        viewModel.movieIdsList.observe(this, {
            it?.let {
                movieIdsList.addAll(it)
            }
        })
    }

    private fun checkIfMovieIsOnDb(): Boolean {
        var boolean = false

        for (id in movieIdsList) {
            if (id == movieDetailResponse.id?.toLong()) {
                boolean = true
                break
            }
        }
        return boolean
    }

    private fun removeMovieFromDb() {
        firebaseAuth.currentUser?.let { user ->
            firestoreDb.collection("users")
                .document(user.uid)
                .update(
                    "favoriteList", FieldValue.arrayRemove(movieDetailResponse),
                    "moviesIds", FieldValue.arrayRemove(movieDetailResponse.id)
                )
                .addOnSuccessListener {
                    Log.d("TAG", "DocumentSnapshot successfully written!")
                    Toast.makeText(this, "Filme Removido dos Favoritos", Toast.LENGTH_LONG).show()
                }.addOnFailureListener { e ->
                    Log.w("TAG", "Error writing document", e)
                    Toast.makeText(
                        this,
                        "Não foi possivel remover o Filme dos Favoritos",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
    }
}