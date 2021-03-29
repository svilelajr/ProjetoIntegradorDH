package com.digitalhouse.moviewallet.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.data.Movie
import com.digitalhouse.moviewallet.R

class SearchScreenLastSeeAdapter(val ultimosVistosList: MutableList<Movie>) : RecyclerView.Adapter<SearchScreenLastSeeAdapter.SearchScreenLastSeeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchScreenLastSeeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_search_last_see, parent, false))

    override fun getItemCount() = ultimosVistosList.size

    override fun onBindViewHolder(holder: SearchScreenLastSeeViewHolder, position: Int) {
        val position = ultimosVistosList[position]
        val nome = holder.nomeFilme
        nome.text = position.nomeDoFilme
    }

    inner class SearchScreenLastSeeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView by lazy { view.findViewById<ImageView>(R.id.iv_search_itemlastsee) }
        val nomeFilme by lazy { view.findViewById<TextView>(R.id.tv_search_itemlastsee) }
    }
}