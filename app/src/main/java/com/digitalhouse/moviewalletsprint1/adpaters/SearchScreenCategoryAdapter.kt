package com.digitalhouse.moviewalletsprint1.adpaters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.data.Category

import com.digitalhouse.moviewalletsprint1.R

class SearchScreenCategoryAdapter(val categorias: MutableList<Category>) : RecyclerView.Adapter<SearchScreenCategoryAdapter.SearchScreenCategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchScreenCategoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_search_category, parent, false))

    override fun getItemCount() = categorias.size

    override fun onBindViewHolder(holder: SearchScreenCategoryViewHolder, position: Int) {
        val nome = holder.nomeCategoria
        nome.text = categorias[position].categoria
    }

    inner class SearchScreenCategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nomeCategoria by lazy { view.findViewById<TextView>(R.id.tv_categorysearch_itemcategory) }
    }
}

