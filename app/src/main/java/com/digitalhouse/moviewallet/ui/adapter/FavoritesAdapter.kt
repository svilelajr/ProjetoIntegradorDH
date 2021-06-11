package com.digitalhouse.moviewallet.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.R
import com.digitalhouse.moviewallet.repository.SingletonConfiguration
import com.digitalhouse.moviewallet.ui.activity.DetailsScreen
import com.squareup.picasso.Picasso

class FavoritesAdapter(private val favoriteList: MutableList<HashMap<String, *>>): RecyclerView.Adapter<FavoritesAdapter.FavoriteScreenViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FavoriteScreenViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_context_screen, parent, false))

    override fun getItemCount() = favoriteList.size

    override fun onBindViewHolder(holder: FavoriteScreenViewHolder, position: Int) {
        val configuration = SingletonConfiguration.config
        val position = favoriteList[position]
        val imageUrl = "${configuration?.images?.secure_base_url}${configuration?.images?.poster_sizes?.get(5)}${position.getValue("posterPath")}"
        val image = holder.imgMovie
        val movieId = position.getValue("id")
        movieId.toString()
        Picasso.get().load(imageUrl).into(image)
        holder.cardViewMovie.setOnClickListener {
            val intent = Intent(it.context, DetailsScreen::class.java)
            intent.putExtra("MOVIE_ID", movieId.toString())
            it.context.startActivity(intent)
        }
    }


    inner class FavoriteScreenViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgMovie by lazy { view.findViewById<ImageView>(R.id.iv_movie_screen) }
        val cardViewMovie by lazy { view.findViewById<CardView>(R.id.cv_movie_screen) }
    }

}