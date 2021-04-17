package com.digitalhouse.moviewallet.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.R
import com.digitalhouse.moviewallet.model.Genre
import com.digitalhouse.moviewallet.ui.activity.ContextScreen

class SearchScreenCategoryAdapter(private val listaDeCategorias: MutableList<Genre>) : RecyclerView.Adapter<SearchScreenCategoryAdapter.SearchScreenCategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchScreenCategoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_search_category, parent, false))

    override fun getItemCount() = listaDeCategorias.size

    override fun onBindViewHolder(holder: SearchScreenCategoryViewHolder, position: Int) {
        val position = listaDeCategorias[position]
        val nome = holder.nomeCategoria
        val btCategory = holder.btCategory
        nome.text = position.name


        btCategory.setOnClickListener {

        }


    }

    inner class SearchScreenCategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nomeCategoria by lazy { view.findViewById<TextView>(R.id.tv_categorysearch_itemcategory) }
        val btCategory by lazy { view.findViewById<ImageButton>(R.id.bt_categorysearch_temcategory) }
    }
}

