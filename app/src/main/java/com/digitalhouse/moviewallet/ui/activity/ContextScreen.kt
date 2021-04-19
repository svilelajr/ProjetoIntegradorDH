package com.digitalhouse.moviewallet.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.R
import com.digitalhouse.moviewallet.model.MovieRecycler
import com.digitalhouse.moviewallet.ui.adapter.MovieScreenAdapter
import com.digitalhouse.moviewallet.ui.viewmodel.ContextViewModel

class ContextScreen : AppCompatActivity() {

    private val rvMovie by lazy { findViewById<RecyclerView>(R.id.rv_movie_screen) }
    private val toolbar by lazy { findViewById<Toolbar>(R.id.tb_movies) }
    private val listMovie = mutableListOf<MovieRecycler>()
    private val adapterMovie = MovieScreenAdapter(listMovie)

    private val viewModel by lazy { ViewModelProviders.of(this).get(ContextViewModel::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.context_screen)

        initViews()
    }

    private fun initViews() {
        rvMovie.adapter = adapterMovie
        rvMovie.layoutManager = GridLayoutManager(this, 2)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        val extras = intent.extras
        val genreName = extras?.getString("GENRE_NAME")
        val genreId = extras?.getInt("GENRE_ID")
        supportActionBar?.title = genreName
        if (genreId != null) {
            viewModel.getMovieListByGenre(genreId.toString())
            seeMore()
        }

    }

    private fun seeMore() {
        viewModel.movieByCategory.observe(this) {
           listMovie.addAll(it)
            adapterMovie.notifyDataSetChanged()
        }
    }


}