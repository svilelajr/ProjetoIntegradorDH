package com.digitalhouse.moviewallet.ui.activity

import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.R
import com.digitalhouse.moviewallet.model.Genre
import com.digitalhouse.moviewallet.ui.adapter.SearchScreenCategoryAdapter
import com.digitalhouse.moviewallet.ui.adapter.SearchScreenLastSeeAdapter
import com.digitalhouse.moviewallet.ui.viewmodel.DetailsViewModel
import com.digitalhouse.moviewallet.ui.viewmodel.SearchViewModel

class SearchScreen() : AppCompatActivity() {
    private val toolbar by lazy { findViewById<Toolbar>(R.id.tb_search) }
    private val recyclerVistos by lazy { findViewById<RecyclerView>(R.id.rv_lastsee_search) }
    private val recyclerCategorias by lazy { findViewById<RecyclerView>(R.id.rv_category_search) }
    private val seachMovie by lazy { findViewById<SearchView>(R.id.sch_search) }
    var listGenres = mutableListOf<Genre>()

    private lateinit var viewModel: SearchViewModel
    val adapter = SearchScreenCategoryAdapter(listGenres)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_screen)
        viewModel = ViewModelProvider.NewInstanceFactory().create(SearchViewModel::class.java)
        viewModel.getGenre()
        viewModel.listGenre.observe(this, Observer {
            it?.let { listGenres.addAll(it) }
            adapter.notifyDataSetChanged()
        })
        setupRecycler()
        setupToolbar()
    }

    private fun setupRecycler() {
        recyclerVistos.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//       recyclerVistos.adapter = SearchScreenLastSeeAdapter(ListaFilmes.getListaDefilmeAcao())

        recyclerCategorias.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerCategorias.adapter = adapter

        recyclerCategorias.isNestedScrollingEnabled
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        supportActionBar?.setDisplayShowTitleEnabled(false)

    }
}


