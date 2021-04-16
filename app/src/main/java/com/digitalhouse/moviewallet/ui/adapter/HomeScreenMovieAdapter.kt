package com.digitalhouse.moviewallet.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.R
import com.digitalhouse.moviewallet.ui.activity.DetailsScreen
import com.digitalhouse.moviewallet.model.Movie
import com.digitalhouse.moviewallet.repository.SingletonConfiguration
import com.squareup.picasso.Picasso

class HomeScreenMovieAdapter(val listaFilme: MutableList<Movie>) : RecyclerView.Adapter<HomeScreenMovieAdapter.HomeScreenMovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HomeScreenMovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_home_category, parent, false))

    override fun getItemCount() = listaFilme.size

    override fun onBindViewHolder(holder: HomeScreenMovieViewHolder, position: Int) {

        val position = listaFilme[position]
        val configuration = SingletonConfiguration.config
        val imageUrl = "${configuration?.images?.secure_base_url}${configuration?.images?.poster_sizes?.get(4)}${position.posterPath}"
        val poster = holder.imgPoster
        Picasso.get().load(imageUrl).into(poster)
        holder.cvMovie.setOnClickListener {
            val intent = Intent(it.context, DetailsScreen::class.java)
            intent.putExtra("NAME_MOVIE", position.originalTitle)
            it.context.startActivity(intent)
        }
    }

    inner class HomeScreenMovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgPoster = view.findViewById<ImageView>(R.id.iv_movie_itemcategory)
        var cvMovie = view.findViewById<CardView>(R.id.cv_movie_itemcategory)
    }
}




