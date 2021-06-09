package com.digitalhouse.moviewallet.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.R
import com.digitalhouse.moviewallet.ui.adapter.MovieScreenAdapter
import com.digitalhouse.moviewallet.ui.adapter.RecyclerScrollListener
import com.digitalhouse.moviewallet.ui.viewmodel.ContextViewModel

class ContextScreen : AppCompatActivity() {

    private val rvMovie by lazy { findViewById<RecyclerView>(R.id.rv_movie_screen) }
    private val toolbar by lazy { findViewById<Toolbar>(R.id.tb_movies) }
    private lateinit var adapterMovie: MovieScreenAdapter
    private val nextPageLoading by lazy { findViewById<ProgressBar>(R.id.next_loading_context_screen) }

    private val viewModelContextScreen by lazy { ViewModelProviders.of(this).get(ContextViewModel::class.java) }

    private val recyclerScroollListener by lazy {
        RecyclerScrollListener {
            viewModelContextScreen.requestNextPage()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.context_screen)

        initViews()
    }

    private fun initViews() {
        adapterMovie = MovieScreenAdapter()
        rvMovie.adapter = adapterMovie
        rvMovie.layoutManager = GridLayoutManager(this, 2)
        setSupportActionBar(toolbar)
        rvMovie.addOnScrollListener(recyclerScroollListener)

        toolbar.setNavigationOnClickListener { onBackPressed() }

        val extras = intent.extras
        val genreName = extras?.getString("GENRE_NAME")
        val genreId = extras?.getInt("GENRE_ID")

        supportActionBar?.title = genreName

        if (genreId != null) {
            viewModelContextScreen.getMovieListByGenre(genreId.toString())
            seeMore()
        }

    }

    private fun seeMore() {
        viewModelContextScreen.movieByCategory.observe(this) {movieList ->
           setRequestingNextPage()
            adapterMovie.addMovies(movieList)
        }

        viewModelContextScreen.nextPageLoading.observe(this) {
            if (it) {
                nextPageLoading.visibility = View.VISIBLE
            } else {
                nextPageLoading.visibility = View.GONE
            }
        }
    }

    private fun setRequestingNextPage() {
        recyclerScroollListener.requesting = false
    }


}