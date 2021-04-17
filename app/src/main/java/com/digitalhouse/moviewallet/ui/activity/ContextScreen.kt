package com.digitalhouse.moviewallet.ui.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.R
import com.digitalhouse.moviewallet.ui.adapter.MovieScreenAdapter
import com.digitalhouse.moviewallet.data.Movie
import com.digitalhouse.moviewallet.ui.viewmodel.ContextViewModel
import java.io.Serializable

class ContextScreen : AppCompatActivity() {

    private val tvContext by lazy { findViewById<TextView>(R.id.tv_context_movie) }
    private val rvMovie by lazy { findViewById<RecyclerView>(R.id.rv_movie_screen) }
    private val toolbar by lazy { findViewById<Toolbar>(R.id.tb_movies) }

    private lateinit var viewModel: ContextViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.context_screen)
        viewModel = ViewModelProvider.NewInstanceFactory().create(ContextViewModel::class.java)
        initViews()

    }

    private fun initViews() {
        rvMovie.layoutManager = GridLayoutManager(this, 3)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        val extras = intent.extras
        val genreName = extras?.getString("GENRE_NAME")
        val genreId = extras?.getInt("GENRE_ID")
        toolbar.title = genreName.toString()
        tvContext.text = "$genreName :"
        if (genreId != null){
            viewModel.getMovieListByGenre(genreId.toString())
            seeMore()
        }

    }

    private fun seeMore(){
        viewModel.movieByCategory.observe(this) {
            rvMovie.adapter = MovieScreenAdapter(it)
        }
    }
}