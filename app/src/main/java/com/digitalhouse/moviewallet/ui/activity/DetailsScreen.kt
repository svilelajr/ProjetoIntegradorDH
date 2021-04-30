package com.digitalhouse.moviewallet.ui.activity

import android.content.DialogInterface
import android.os.Bundle
import android.os.TestLooperManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.R
import com.digitalhouse.moviewallet.model.Actor
import com.digitalhouse.moviewallet.model.Movie
import com.digitalhouse.moviewallet.repository.SingletonConfiguration
import com.digitalhouse.moviewallet.ui.adapter.ActorDetailsAdapter
import com.digitalhouse.moviewallet.ui.viewmodel.DetailsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class DetailsScreen : AppCompatActivity() {
    private val toolbar by lazy { findViewById<androidx.appcompat.widget.Toolbar>(R.id.tb_details) }
    private val ivMovie by lazy { findViewById<ImageView>(R.id.iv_movie_details) }
    private val tvMovie by lazy { findViewById<TextView>(R.id.tv_title_details) }
    private val tvSynopsis by lazy { findViewById<TextView>(R.id.tv_synopsis_details) }
    private val tvGenre by lazy { findViewById<TextView>(R.id.tv_genre_details) }
    private val tvRating by lazy { findViewById<TextView>(R.id.tv_percentage_details) }
    private val rvActors by lazy { findViewById<RecyclerView>(R.id.rv_elenco) }

    private val btFavorite by lazy { findViewById<FloatingActionButton>(R.id.bt_favorite_details) }

    private lateinit var viewModel: DetailsViewModel
    private val listActor = mutableListOf<Actor>()

    private val adapterActor = ActorDetailsAdapter(listActor)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_screen)
        viewModel = ViewModelProvider.NewInstanceFactory().create(DetailsViewModel::class.java)
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
            val date = LocalDate.parse(it.releaseDate)
            val imageUrl: String = viewModel.getBackdropPath()
            tvMovie.text = it.title
            tvSynopsis.text = it.overview
            tvGenre.text = "(${date.year}) ${it.genres?.get(0)?.name} | ${it.runtime}min"
            tvRating.text = "${viewModel.getPopularity()} %"
            Picasso.get().load(imageUrl).into(ivMovie)
        }
    }

    private fun initClick() {
        btFavorite.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Em construção")
            builder.setMessage("Disponível em Breve...")
            builder.show()
        }
    }
    private fun movieCredits(){
        viewModel.actorMovie.observe(this,){
            listActor.addAll(it)
            adapterActor.notifyDataSetChanged()
        }
    }

    private fun setupRecycler(){
     rvActors.adapter = adapterActor
        rvActors.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
    }
}