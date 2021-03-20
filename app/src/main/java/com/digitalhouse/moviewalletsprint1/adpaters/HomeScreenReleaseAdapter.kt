package com.digitalhouse.moviewalletsprint1.adpaters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.data.Movie
import com.digitalhouse.moviewalletsprint1.R
import com.digitalhouse.moviewalletsprint1.activities.DetailsScreen

class HomeScreenReleaseAdapter(private val listRelease: MutableList<Movie>) : RecyclerView.Adapter<HomeScreenReleaseAdapter.HomeScreenReleaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HomeScreenReleaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_home_release, parent, false))

    override fun getItemCount() = listRelease.size

    override fun onBindViewHolder(holder: HomeScreenReleaseViewHolder, position: Int) {
        val img = holder.imgView
        img.setImageResource(listRelease[position].poster)
        holder.cvMovie.setOnClickListener{
            val intent= Intent(it.context,DetailsScreen::class.java)
            intent.putExtra("NAME_MOVIE", listRelease[position].nomeDoFilme)
            intent.putExtra("IMAGE_MOVIE", listRelease[position].poster)
            it.context.startActivity(intent)
        }

    }

    inner class HomeScreenReleaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgView by lazy { view.findViewById<ImageView>(R.id.iv_movie_itemrelease) }
        val cvMovie by lazy { view.findViewById<CardView>(R.id.cv_movie_itemrelease) }
    }
}