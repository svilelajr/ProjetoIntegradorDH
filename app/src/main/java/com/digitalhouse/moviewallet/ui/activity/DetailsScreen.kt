package com.digitalhouse.moviewallet.ui.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.R
import com.digitalhouse.moviewallet.model.Flatrate
import com.digitalhouse.moviewallet.model.Providers
import com.digitalhouse.moviewallet.ui.adapter.ProviderDetailsAdapter
import com.digitalhouse.moviewallet.ui.viewmodel.DetailsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import java.time.LocalDate

class DetailsScreen : AppCompatActivity() {
    private val toolbar by lazy { findViewById<androidx.appcompat.widget.Toolbar>(R.id.tb_details) }
    private val ivMovie by lazy { findViewById<ImageView>(R.id.iv_movie_details) }
    private val tvMovie by lazy { findViewById<TextView>(R.id.tv_title_details) }
    private val tvSynopsis by lazy { findViewById<TextView>(R.id.tv_synopsis_details) }
    private val tvGenre by lazy { findViewById<TextView>(R.id.tv_genre_details) }
    private val tvRating by lazy { findViewById<TextView>(R.id.tv_percentage_details) }
    private val rvProvider by lazy { findViewById<RecyclerView>(R.id.rv_provider_details) }


    private val btFavorite by lazy { findViewById<FloatingActionButton>(R.id.bt_favorite_details) }

    private lateinit var viewModel: DetailsViewModel
    private val listProviders = mutableListOf<Flatrate>()
    var urlImageTest = ""

    private val adapterProvider = ProviderDetailsAdapter(listProviders)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_screen)
        viewModel = ViewModelProvider.NewInstanceFactory().create(DetailsViewModel::class.java)
        viewModel.imageTest.observe(this, { url ->
            Picasso.get().load(url).into(ivMovie)
        })
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
            viewModel.getProvider(movieId.toString())
            providerMovie()
        }
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun movieDetails() {
        viewModel.movieDetail.observe(this) {
            val date = LocalDate.parse(it.releaseDate)
            val imageUrl: String = urlImageTest
            tvMovie.text = it.title
            tvSynopsis.text = it.overview
            tvGenre.text = "(${date.year}) ${it.genres?.get(0)?.name} | ${it.runtime}min"
            tvRating.text = "${viewModel.getPopularity()} %"
//            Picasso.get().load(imageUrl).into(ivMovie)
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

    private fun providerMovie() {
        viewModel.providerMovie.observe(this) {
            if (it != null) {
                listProviders.addAll(it)
            }
            adapterProvider.notifyDataSetChanged()
        }
    }

    private fun setupRecycler() {
        rvProvider.adapter = adapterProvider
        rvProvider.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

}