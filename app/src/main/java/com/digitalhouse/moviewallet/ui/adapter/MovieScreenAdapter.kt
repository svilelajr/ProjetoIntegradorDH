package com.digitalhouse.moviewallet.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.R
import com.digitalhouse.moviewallet.model.MovieRecycler
import com.digitalhouse.moviewallet.repository.SingletonConfiguration
import com.digitalhouse.moviewallet.ui.activity.DetailsScreen
import com.squareup.picasso.Picasso

class MovieScreenAdapter(val listMovies: MutableList<MovieRecycler>) : RecyclerView.Adapter<MovieScreenAdapter.MovieScreenViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieScreenViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_context_screen, parent, false))

    override fun getItemCount() = listMovies.size

    override fun onBindViewHolder(holder: MovieScreenViewHolder, position: Int) {
        val configuration = SingletonConfiguration.config
        val position = listMovies[position]
        val imageUrl = "${configuration?.images?.secure_base_url}${configuration?.images?.poster_sizes?.get(5)}${position.posterPath}"
        val image = holder.imgMovie
        Picasso.get().load(imageUrl).into(image)
        holder.cvMovie.setOnClickListener {
            val intent = Intent(it.context, DetailsScreen::class.java)
            intent.putExtra("MOVIE_ID", position.id)
            it.context.startActivity(intent)
        }
    }


    inner class MovieScreenViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgMovie by lazy { view.findViewById<ImageView>(R.id.iv_movie_screen) }
        val cvMovie by lazy { view.findViewById<CardView>(R.id.cv_movie_screen) }
    }
}