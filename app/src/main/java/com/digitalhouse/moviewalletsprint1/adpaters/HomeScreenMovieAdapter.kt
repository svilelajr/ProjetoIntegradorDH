package com.digitalhouse.moviewallet.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.data.Movie
import com.digitalhouse.moviewalletsprint1.R
import com.digitalhouse.moviewalletsprint1.activities.DetailsScreen

class HomeScreenMovieAdapter(private val listaFilme: MutableList<Movie>) : RecyclerView.Adapter<HomeScreenMovieAdapter.HomeScreenMovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HomeScreenMovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_home_category, parent, false))

    override fun getItemCount() = listaFilme.size

    override fun onBindViewHolder(holder: HomeScreenMovieViewHolder, position: Int) {
        val poster = holder.imgPoster
        poster.setImageResource(listaFilme[position].poster)
        val nameMovie = holder.nameMovie
        nameMovie.text = listaFilme[position].nomeDoFilme
        holder.cvMovie.setOnClickListener {
            val intent = Intent(it.context, DetailsScreen::class.java)
            intent.putExtra("NAME_MOVIE", listaFilme[position].nomeDoFilme)
            intent.putExtra("IMAGE_MOVIE", listaFilme[position].poster)
            it.context.startActivity(intent)
        }
    }

    inner class HomeScreenMovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgPoster = view.findViewById<ImageView>(R.id.iv_movie_itemcategory)
        val nameMovie = view.findViewById<TextView>(R.id.tv_titlemovie_itemcategory)
        var cvMovie = view.findViewById<CardView>(R.id.cv_movie_itemcategory)
    }
}




