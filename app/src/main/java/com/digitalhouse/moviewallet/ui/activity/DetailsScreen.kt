package com.digitalhouse.moviewallet.ui.activity

import android.os.Bundle
import android.os.TestLooperManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.digitalhouse.moviewallet.R
import com.digitalhouse.moviewallet.model.Movie
import com.digitalhouse.moviewallet.repository.SingletonConfiguration
import com.digitalhouse.moviewallet.ui.viewmodel.DetailsViewModel
import com.squareup.picasso.Picasso

class DetailsScreen : AppCompatActivity() {
    private val toolbar by lazy { findViewById<androidx.appcompat.widget.Toolbar>(R.id.tb_details) }
    private val ivMovie by lazy { findViewById<ImageView>(R.id.iv_movie_details) }
    private val tvMovie by lazy { findViewById<TextView>(R.id.tv_title_details) }
    private val tvSynopsis by lazy { findViewById<TextView>(R.id.tv_synopsis_details) }
    private val tvGenre by lazy { findViewById<TextView>(R.id.tv_genre_details) }

    private lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_screen)
        viewModel = ViewModelProvider.NewInstanceFactory().create(DetailsViewModel::class.java)

        initViews()
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initViews() {
        val extras = intent.extras
        val movieId = extras?.getInt("MOVIE_ID")
        if (movieId != null) {
            viewModel.getMovieDetail(movieId.toString())
            movieDetails()
        }
    }

    private fun movieDetails() {
        viewModel.movieDetail.observe(this) {
            val imageUrl: String = viewModel.getBackdropPath()
            tvMovie.text = it.title
            tvSynopsis.text = it.overview

            Picasso.get().load(imageUrl).into(ivMovie)
        }
    }
}