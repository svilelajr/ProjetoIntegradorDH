package com.digitalhouse.moviewallet.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.data.Movie
import com.digitalhouse.moviewallet.R
import com.digitalhouse.moviewallet.data.ListaCategorias
import com.digitalhouse.moviewallet.data.ListaFilmes
import com.digitalhouse.moviewallet.model.Genre
import com.digitalhouse.moviewallet.ui.activity.ContextScreen

class HomeScreenCategoryAdapter(private val listaDeCategorias: MutableList<Genre>) : RecyclerView.Adapter<HomeScreenCategoryAdapter.HomeScreenCategoryViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HomeScreenCategoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.reciclerview_lista_de_filmes, parent, false))

    override fun getItemCount() = listaDeCategorias.size

    override fun onBindViewHolder(holder: HomeScreenCategoryViewHolder, position: Int) {
        val listaCategorias = listaDeCategorias[position]
        val tipoDeCategorias = holder.nomeDasCategorias
        val buttonVerMais = holder.buttonVerMais
        val recyclerViewFilmes = holder.recyclerViewFilmes

        tipoDeCategorias.text = listaCategorias.name
        recyclerViewFilmes.layoutManager = LinearLayoutManager(holder.recyclerViewFilmes.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewFilmes.adapter = HomeScreenMovieAdapter(ListaFilmes.getListaDefilmeAcao())
        recyclerViewFilmes.setRecycledViewPool(viewPool)

        buttonVerMais.setOnClickListener{
            val intent= Intent(it.context, ContextScreen::class.java)
            val movieArray:ArrayList<Movie> = ArrayList(ListaFilmes.getListaDefilmeAcao())
            intent.putExtra("NAME",listaDeCategorias[position].name)
            intent.putExtra("MOVIES",movieArray)
            it.context.startActivity(intent)
        }
    }

    inner class HomeScreenCategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nomeDasCategorias by lazy { view.findViewById<TextView>(R.id.txt_categorias) }
        val buttonVerMais by lazy { view.findViewById<Button>(R.id.btn_ver_mais) }
        val recyclerViewFilmes by lazy { view.findViewById<RecyclerView>(R.id.recyclerview_filmes) }
    }
}