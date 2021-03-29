package com.digitalhouse.moviewallet.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.data.Movie
import com.digitalhouse.moviewallet.R
import com.digitalhouse.moviewallet.ui.activity.DetailsScreen

class MovieScreenAdapter(val listMovies: MutableList<Movie>) : RecyclerView.Adapter<MovieScreenAdapter.MovieScreenViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieScreenViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_context_screen, parent, false))

    override fun getItemCount() = listMovies.size

    override fun onBindViewHolder(holder: MovieScreenViewHolder, position: Int) {
        val position = listMovies[position]
        val image = holder.imgMovie
        val title = holder.tvMovie
        image.setImageResource(position.poster)
        title.text = position.nomeDoFilme
        holder.cvMovie.setOnClickListener {
            val intent = Intent(it.context, DetailsScreen::class.java)
            intent.putExtra("NAME_MOVIE", position.nomeDoFilme)
            intent.putExtra("IMAGE_MOVIE", position.poster)
            it.context.startActivity(intent)
        }
    }


    inner class MovieScreenViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgMovie by lazy { view.findViewById<ImageView>(R.id.iv_movie_screen) }
        val tvMovie by lazy { view.findViewById<TextView>(R.id.tv_movie_screen) }
        val cvMovie by lazy { view.findViewById<CardView>(R.id.cv_movie_screen) }
    }
}