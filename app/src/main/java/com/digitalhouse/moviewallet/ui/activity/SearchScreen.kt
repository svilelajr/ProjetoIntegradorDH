package com.digitalhouse.moviewallet.ui.activity

import android.os.Bundle
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.data.ListaCategorias
import com.digitalhouse.moviewallet.data.ListaFilmes
import com.digitalhouse.moviewallet.R
import com.digitalhouse.moviewallet.ui.adapter.SearchScreenCategoryAdapter
import com.digitalhouse.moviewallet.ui.adapter.SearchScreenLastSeeAdapter
import com.digitalhouse.moviewallet.ui.viewmodel.SearchViewModel

class SearchScreen() : AppCompatActivity() {
    private val toolbar by lazy { findViewById<Toolbar>(R.id.tb_search) }
    private val recyclerVistos by lazy { findViewById<RecyclerView>(R.id.rv_lastsee_search) }
    private val recyclerCategorias by lazy { findViewById<RecyclerView>(R.id.rv_category_search) }

    private lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_screen)
        setupRecycler()
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupRecycler() {
        recyclerVistos.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerVistos.adapter = SearchScreenLastSeeAdapter(ListaFilmes.getListaDefilmeAcao())

        recyclerCategorias.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerCategorias.adapter = SearchScreenCategoryAdapter(ListaCategorias.getCategorias())

        recyclerCategorias.isNestedScrollingEnabled
    }
}


