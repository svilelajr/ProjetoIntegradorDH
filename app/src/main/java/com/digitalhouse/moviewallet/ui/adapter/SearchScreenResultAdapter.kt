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
import com.digitalhouse.moviewallet.model.Movie
import com.digitalhouse.moviewallet.repository.SingletonConfiguration
import com.digitalhouse.moviewallet.ui.activity.DetailsScreen
import com.squareup.picasso.Picasso
import java.time.LocalDate

class SearchScreenResultAdapter(private val listMovie: MutableList<Movie>) :
    RecyclerView.Adapter<SearchScreenResultAdapter.SearchScreenResultViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = SearchScreenResultViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_screen_resultsearch, parent, false)
    )

    override fun onBindViewHolder(holder: SearchScreenResultViewHolder, position: Int) {
        val configuration = SingletonConfiguration.config
        val movie = listMovie[position]

        val imageUrl =
            "${configuration?.images?.secure_base_url}${configuration?.images?.poster_sizes?.get(5)}${movie.posterPath}"
        val image = holder.imageMovie
        Picasso.get().load(imageUrl).into(image)
        holder.titleMovie?.text = movie.title
        holder.yearRelease?.text = movie.releaseDate
        holder.cvMovie?.setOnClickListener {
            val intent = Intent(it.context, DetailsScreen::class.java)
            intent.putExtra("MOVIE_ID", movie.id)
            it.context.startActivity(intent)
        }

    }

    override fun getItemCount() = listMovie.size


    inner class SearchScreenResultViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cvMovie: CardView? by lazy { view.findViewById<CardView>(R.id.cv_movie_result) }
        val imageMovie: ImageView? by lazy { view.findViewById<ImageView>(R.id.iv_movie_result) }
        val titleMovie: TextView? by lazy { view.findViewById<TextView>(R.id.tv_movie_result) }
        val yearRelease: TextView? by lazy { view.findViewById<TextView>(R.id.tv_year_movie_result) }
    }
}
