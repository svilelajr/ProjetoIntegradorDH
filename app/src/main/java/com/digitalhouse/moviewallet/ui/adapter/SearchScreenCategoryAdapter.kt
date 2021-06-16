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
import com.digitalhouse.moviewallet.model.Genre
import com.digitalhouse.moviewallet.repository.SingletonConfiguration
import com.digitalhouse.moviewallet.ui.activity.ContextScreen
import com.squareup.picasso.Picasso

class SearchScreenCategoryAdapter(private val listaDeCategorias: MutableList<Genre>) :
    RecyclerView.Adapter<SearchScreenCategoryAdapter.SearchScreenCategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SearchScreenCategoryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_search_categorynew, parent, false)
        )

    override fun getItemCount() = listaDeCategorias.size

    override fun onBindViewHolder(holder: SearchScreenCategoryViewHolder, position: Int) {
        val position = listaDeCategorias[position]
        val configuration = SingletonConfiguration.config
        var imageUrl =
            "${configuration?.images?.secure_base_url}${configuration?.images?.backdrop_sizes?.get(1)}${
                position.movieUrlImage
            }"
        if (position.movieUrlImage != null) {
            Picasso.get().load(imageUrl).into(holder.ivCategory)
        }
        val nome = holder.nomeCategoria
        val cvCategory = holder.cvCategory
        nome?.text = position.name


        cvCategory?.setOnClickListener {
            val intent = Intent(it.context, ContextScreen::class.java)
            intent.putExtra("GENRE_NAME", position.name)
            intent.putExtra("GENRE_ID", position.id)
            it.context.startActivity(intent)
        }


    }

    inner class SearchScreenCategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nomeCategoria: TextView? by lazy { view.findViewById<TextView>(R.id.tv_categorysearch_newitemcategory) }
        val cvCategory by lazy { view.findViewById<CardView>(R.id.cv_categorysearch_newitemcategory) }
        val ivCategory by lazy { view.findViewById<ImageView>(R.id.iv_categorysearch_newitemcategory) }
    }
}

