package com.digitalhouse.moviewalletsprint1.adpaters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.data.Category
import com.digitalhouse.moviewallet.data.Movie

import com.digitalhouse.moviewalletsprint1.R
import com.digitalhouse.moviewalletsprint1.activities.MovieScreen

class SearchScreenCategoryAdapter(val listaDeCategorias: MutableList<Category>) : RecyclerView.Adapter<SearchScreenCategoryAdapter.SearchScreenCategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchScreenCategoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_search_category, parent, false))

    override fun getItemCount() = listaDeCategorias.size

    override fun onBindViewHolder(holder: SearchScreenCategoryViewHolder, position: Int) {
        val nome = holder.nomeCategoria
        nome.text = listaDeCategorias[position].categoria
        val btCategory = holder.btCategory

        btCategory.setOnClickListener {
            val intent= Intent(it.context, MovieScreen::class.java)
            val movieArray:ArrayList<Movie> = ArrayList(listaDeCategorias[position].listaDeFilmes)
            intent.putExtra("NAME",listaDeCategorias[position].categoria)
            intent.putExtra("MOVIES",movieArray)
            it.context.startActivity(intent)
        }


    }

    inner class SearchScreenCategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nomeCategoria by lazy { view.findViewById<TextView>(R.id.tv_categorysearch_itemcategory) }
        val btCategory by lazy { view.findViewById<ImageButton>(R.id.bt_categorysearch_temcategory) }
    }
}

