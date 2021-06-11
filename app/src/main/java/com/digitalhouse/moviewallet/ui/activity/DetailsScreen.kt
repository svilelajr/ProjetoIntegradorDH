package com.digitalhouse.moviewallet.ui.activity

import android.content.Intent
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.R
import com.digitalhouse.moviewallet.model.*
import com.digitalhouse.moviewallet.model.Buy
import com.digitalhouse.moviewallet.model.Flatrate
import com.digitalhouse.moviewallet.model.Movie
import com.digitalhouse.moviewallet.model.Rent
import com.digitalhouse.moviewallet.ui.adapter.DetailsScreenSimiliarAdapter
import com.digitalhouse.moviewallet.ui.adapter.ProviderBuyDetailsAdapter
import com.digitalhouse.moviewallet.ui.adapter.ProviderFlatrateDetailsAdapter
import com.digitalhouse.moviewallet.ui.adapter.ProviderRentDetailsAdapter
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
    private val rvProviderFlatrate by lazy { findViewById<RecyclerView>(R.id.rv_providerflatrate_details) }
    private val rvProviderRent by lazy { findViewById<RecyclerView>(R.id.rv_providerrent_details) }
    private val rvProviderBuy by lazy { findViewById<RecyclerView>(R.id.rv_providerbuy_details) }
    private val btFavorite by lazy { findViewById<FloatingActionButton>(R.id.bt_favorite_details) }
    private val tvFlatrate by lazy { findViewById<TextView>(R.id.tv_flatrate_details) }
    private val tvRent by lazy { findViewById<TextView>(R.id.tv_rent_details) }
    private val tvBuy by lazy { findViewById<TextView>(R.id.tv_buy_details) }
    private val tvTitleSemelhante by lazy { findViewById<TextView>(R.id.tv_title_semelhante) }
    private val ratingBar by lazy { findViewById<RatingBar>(R.id.rb_details_screen) }
    private val rvSimilarMovie by lazy { findViewById<RecyclerView>(R.id.rv_similiar_details) }


    private lateinit var viewModel: DetailsViewModel
    private lateinit var firebaseAuth: FirebaseAuth
    private val firestoreDb = Firebase.firestore
    private lateinit var movieDetailResponse: MovieDetailResponse

    private val listProviders = mutableListOf<Flatrate>()
    var urlImageTest = ""

    private val listProvidersFlatrate = mutableListOf<Flatrate>()
    private val listProvidersBuy = mutableListOf<Buy>()
    private val listProvidersRent = mutableListOf<Rent>()
    private val listMovie = mutableListOf<Movie>()
    private val adapterProviderFlatrate = ProviderFlatrateDetailsAdapter(listProvidersFlatrate)
    private val adapterProviderBuy = ProviderBuyDetailsAdapter(listProvidersBuy)
    private val adapterProviderRent = ProviderRentDetailsAdapter(listProvidersRent)
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
            viewModel.getSimiliarMovie(movieId.toString())
            similarMovie()

            movieDetails()
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
            ratingBar.rating = 5.0f
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

    private fun providerMovie() {
        viewModel.providerFlatrate.observe(this) {
            if (it != null) {
                listProvidersFlatrate.addAll(it)
            }
            adapterProviderFlatrate.notifyDataSetChanged()
            validateProvider()
        }
        viewModel.providerBuy.observe(this) {
            if (it != null) {
                listProvidersBuy.addAll(it)
            }
            adapterProviderBuy.notifyDataSetChanged()
            validateProvider()
        }
        viewModel.providerRent.observe(this) {
            if (it != null) {
                listProvidersRent.addAll(it)
            }
            adapterProviderRent.notifyDataSetChanged()
            validateProvider()
        }

    }

    private fun similarMovie() {
        viewModel.similiarMovies.observe(this) {
            listMovie.addAll(it)
            adapterSimilarMovie.notifyDataSetChanged()
            if (listMovie.isNullOrEmpty()){
                tvTitleSemelhante.visibility = GONE
            }
        }
    }

    private fun setupRecycler() {
        rvProviderFlatrate.adapter = adapterProviderFlatrate
        rvProviderFlatrate.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        rvProviderBuy.adapter = adapterProviderBuy
        rvProviderBuy.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        rvProviderRent.adapter = adapterProviderRent
        rvProviderRent.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        rvSimilarMovie.adapter = adapterSimilarMovie
        rvSimilarMovie.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun validateProvider() {
        when {
            listProvidersFlatrate.isNotEmpty() -> {
                rvProviderFlatrate.visibility = VISIBLE
                tvFlatrate.visibility = VISIBLE
                ivJustWatch.visibility = VISIBLE
            }
            listProvidersRent.isNotEmpty() -> {
                rvProviderRent.visibility = VISIBLE
                tvRent.visibility = VISIBLE
                ivJustWatch.visibility = VISIBLE
            }
            listProvidersBuy.isNotEmpty() -> {
                rvProviderBuy.visibility = VISIBLE
                tvBuy.visibility = VISIBLE
                ivJustWatch.visibility = VISIBLE
            }
        }
    }

    private fun addFavoriteToDb() {

        firebaseAuth.currentUser?.let { user ->
            firestoreDb.collection("users")
                .document(user.uid).update("favoriteList", FieldValue.arrayUnion(movieDetailResponse))
                .addOnSuccessListener {
                    Log.d("TAG", "DocumentSnapshot successfully written!")
                }.addOnFailureListener { e ->
                    Log.w("TAG", "Error writing document", e)
                }
        }
    }

    private fun getMovie(movieResponse: MovieDetailResponse) {
        movieDetailResponse = movieResponse
    }
}