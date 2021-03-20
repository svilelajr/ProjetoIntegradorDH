package com.digitalhouse.moviewalletsprint1.adpaters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.data.Movie
import com.digitalhouse.moviewalletsprint1.R

class MovieScreenAdapter(val listMovies: MutableList<Movie>) : RecyclerView.Adapter<MovieScreenAdapter.MovieScreenViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieScreenViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_screen_movie, parent, false))

    override fun getItemCount() = listMovies.size

    override fun onBindViewHolder(holder: MovieScreenViewHolder, position: Int) {
        val image = holder.imgMovie
        val title = holder.tvMovie
        image.setImageResource(listMovies[position].poster)
        title.text = listMovies[position].nomeDoFilme
    }


    inner class MovieScreenViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgMovie by lazy { view.findViewById<ImageView>(R.id.iv_movie_screen) }
        val tvMovie by lazy { view.findViewById<TextView>(R.id.tv_movie_screen) }
    }
}