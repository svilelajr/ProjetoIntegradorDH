package com.digitalhouse.moviewalletsprint1.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.data.ListaFilmes
import com.digitalhouse.moviewallet.data.Movie
import com.digitalhouse.moviewalletsprint1.R
import com.digitalhouse.moviewalletsprint1.adpaters.MovieScreenAdapter
import java.io.Serializable
import kotlin.time.measureTimedValue

class MovieScreen : AppCompatActivity() {

    private val tvContext by lazy { findViewById<TextView>(R.id.tv_context_movie) }
    private val rvMovie by lazy { findViewById<RecyclerView>(R.id.rv_movie_screen) }
    private val toolbar by lazy { findViewById<Toolbar>(R.id.tb_movies) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_movies)
        initViews()

    }

    private fun initViews() {
        rvMovie.layoutManager = GridLayoutManager(this, 3)

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        val extras = intent.extras
        val name = extras?.getString("NAME")
        val movie = extras?.getSerializable("MOVIES")
        val movies = movie?.let { getMovies(it) }
        toolbar.title = name
        tvContext.text = "$name :"
        rvMovie.adapter = movies?.let { MovieScreenAdapter(it) }
    }

    private fun getMovies(movie: Serializable) = movie as MutableList<Movie>
}