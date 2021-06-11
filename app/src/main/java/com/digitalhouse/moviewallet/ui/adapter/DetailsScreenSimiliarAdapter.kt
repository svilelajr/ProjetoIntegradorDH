package com.digitalhouse.moviewallet.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.R
import com.digitalhouse.moviewallet.model.Movie
import com.digitalhouse.moviewallet.repository.SingletonConfiguration
import com.digitalhouse.moviewallet.ui.activity.DetailsScreen
import com.squareup.picasso.Picasso

class DetailsScreenSimiliarAdapter(val listMovies: MutableList<Movie>) :
    RecyclerView.Adapter<DetailsScreenSimiliarAdapter.DetailsScreenSimilarViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = DetailsScreenSimilarViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_details_similiar, parent, false)
    )

    override fun onBindViewHolder(holder: DetailsScreenSimilarViewHolder, position: Int) {
        val configuration = SingletonConfiguration.config
        val position = listMovies[position]
        val imageUrl =
            "${configuration?.images?.secure_base_url}${configuration?.images?.poster_sizes?.get(5)}${position.posterPath}"
        val image = holder.imgMovie
        Picasso.get().load(imageUrl).into(image)
        holder.cvMovie?.setOnClickListener {
            val intent = Intent(it.context, DetailsScreen::class.java)
            intent.putExtra("MOVIE_ID", position.id)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount() = listMovies.size

    inner class DetailsScreenSimilarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgMovie: ImageView? by lazy { view.findViewById<ImageView>(R.id.iv_details_itemsimilar) }
        val cvMovie: CardView? by lazy { view.findViewById<CardView>(R.id.cv_details_itemsimilar) }
    }
}