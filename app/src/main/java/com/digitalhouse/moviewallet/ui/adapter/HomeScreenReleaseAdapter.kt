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
import com.digitalhouse.moviewallet.repository.SingletonConfiguration
import com.digitalhouse.moviewallet.ui.activity.DetailsScreen
import com.squareup.picasso.Picasso

class HomeScreenReleaseAdapter(private val listRelease: MutableList<com.digitalhouse.moviewallet.model.Movie>) : RecyclerView.Adapter<HomeScreenReleaseAdapter.HomeScreenReleaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HomeScreenReleaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_home_release, parent, false))

    override fun getItemCount() = listRelease.size

    override fun onBindViewHolder(holder: HomeScreenReleaseViewHolder, position: Int) {
        val movie = listRelease[position]
        val configuration = SingletonConfiguration.config
        var imageUrl = "${configuration?.images?.secure_base_url}${configuration?.images?.backdrop_sizes?.get(2)}${movie.backdropPath}"
        holder.tvMovie?.text = movie.title
        if (movie.backdropPath != null) {
            Picasso.get().load(imageUrl).into(holder.imgView)
        } else
            imageUrl = "${configuration?.images?.secure_base_url}${configuration?.images?.poster_sizes?.get(5)}${movie.posterPath}"
            Picasso.get().load(imageUrl).into(holder.imgView)

        holder.cvMovie?.setOnClickListener{
            val intent= Intent(it.context,DetailsScreen::class.java)
            intent.putExtra("MOVIE_ID", movie.id)
            it.context.startActivity(intent)
        }

    }

    inner class HomeScreenReleaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgView: ImageView? by lazy { view.findViewById<ImageView>(R.id.iv_movie_itemrelease) }
        val cvMovie: CardView? by lazy { view.findViewById<CardView>(R.id.cv_movie_itemrelease) }
        val tvMovie: TextView? by lazy { view.findViewById<TextView>(R.id.tv_title_itemrelease) }
    }
}