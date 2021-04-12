package com.digitalhouse.moviewallet.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.data.Movie
import com.digitalhouse.moviewallet.R
import com.digitalhouse.moviewallet.ui.activity.DetailsScreen

class HomeScreenReleaseAdapter(private val listRelease: MutableList<com.digitalhouse.moviewallet.model.Movie>) : RecyclerView.Adapter<HomeScreenReleaseAdapter.HomeScreenReleaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HomeScreenReleaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_home_release, parent, false))

    override fun getItemCount() = listRelease.size

    override fun onBindViewHolder(holder: HomeScreenReleaseViewHolder, position: Int) {
        val position = listRelease[position]
        val img = holder.imgView
//        img.setImageResource(position.backdropPath)
        holder.cvMovie.setOnClickListener{
            val intent= Intent(it.context,DetailsScreen::class.java)
            intent.putExtra("NAME_MOVIE", position.title)
            intent.putExtra("IMAGE_MOVIE", position.backdropPath)
            it.context.startActivity(intent)
        }

    }

    inner class HomeScreenReleaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgView by lazy { view.findViewById<ImageView>(R.id.iv_movie_itemrelease) }
        val cvMovie by lazy { view.findViewById<CardView>(R.id.cv_movie_itemrelease) }
    }
}