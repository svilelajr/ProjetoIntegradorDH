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

class SearchScreenResultAdapter(private val listMovie: MutableList<Movie>) :
    RecyclerView.Adapter<SearchScreenResultAdapter.SearchScreenResultViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = SearchScreenResultViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_context_screen, parent, false)
    )

    override fun onBindViewHolder(holder: SearchScreenResultViewHolder, position: Int) {
        val configuration = SingletonConfiguration.config
        val movie = listMovie[position]

        val imageUrl =
            "${configuration?.images?.secure_base_url}${configuration?.images?.poster_sizes?.get(5)}${movie.posterPath}"
        val image = holder.imageMovie
        Picasso.get().load(imageUrl).into(image)
        holder.cvMovie?.setOnClickListener {
            val intent = Intent(it.context, DetailsScreen::class.java)
            intent.putExtra("MOVIE_ID", movie.id.toString())
            it.context.startActivity(intent)
        }

    }

    override fun getItemCount() = listMovie.size


    inner class SearchScreenResultViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cvMovie: CardView? by lazy { view.findViewById<CardView>(R.id.cv_movie_screen) }
        val imageMovie: ImageView? by lazy { view.findViewById<ImageView>(R.id.iv_movie_screen) }

    }
}
