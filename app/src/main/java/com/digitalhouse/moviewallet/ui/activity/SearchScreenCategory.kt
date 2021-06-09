package com.digitalhouse.moviewallet.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.R
import com.digitalhouse.moviewallet.model.Genre
import com.digitalhouse.moviewallet.ui.adapter.SearchScreenCategoryAdapter
import com.digitalhouse.moviewallet.ui.viewmodel.SearchViewModel

class SearchScreenCategory : AppCompatActivity() {

    private val toolbar by lazy { findViewById<Toolbar>(R.id.tb_search_category) }
    private val recyclerCategorias by lazy { findViewById<RecyclerView>(R.id.rv_category_search) }
    private val searchMovie by lazy { findViewById<ImageButton>(R.id.sch_search) }
    private var listGenres = mutableListOf<Genre>()

    private lateinit var viewModel: SearchViewModel
    private val adapterCategory = SearchScreenCategoryAdapter(listGenres)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_category_screen)
        viewModel = ViewModelProvider.NewInstanceFactory().create(SearchViewModel::class.java)
        viewModel.getGenre()
        observer()
        setupRecycler()
        setupToolbar()
        searchMovie.setOnClickListener {
            val intent = Intent(this,SearchScreen::class.java)
            startActivity(intent)
        }
    }



    private fun setupRecycler() {

        recyclerCategorias.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerCategorias.adapter = adapterCategory

        recyclerCategorias.isNestedScrollingEnabled
    }

    private fun observer() {
        viewModel.listGenre.observe(this, {
            it?.let { listGenres.addAll(it) }
            adapterCategory.notifyDataSetChanged()
        })

    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}


