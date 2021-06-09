package com.digitalhouse.moviewallet.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.R
import com.digitalhouse.moviewallet.model.Movie
import com.digitalhouse.moviewallet.ui.adapter.SearchScreenResultAdapter
import com.digitalhouse.moviewallet.ui.viewmodel.SearchViewModel

class SearchScreen : AppCompatActivity() {

    private val toolbar by lazy { findViewById<Toolbar>(R.id.tb_search) }
    private val search by lazy { findViewById<SearchView>(R.id.search_new) }
    private val resultSearch by lazy { findViewById<RecyclerView>(R.id.rv_result_search) }

    private lateinit var viewModel: SearchViewModel
    var listMovies = mutableListOf<Movie>()
    private val adapterResult = SearchScreenResultAdapter(listMovies)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_screen)
        viewModel = ViewModelProvider.NewInstanceFactory().create(SearchViewModel::class.java)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search.clearFocus()
                viewModel.searchMovie(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                listMovies.clear()
                viewModel.searchMovie(newText)
                return false
            }

        })
        observer()
        setupRecycler()

    }

    private fun observer() {
        viewModel.resultSearchMovie.observe(this) {
            listMovies.addAll(it)
            adapterResult.notifyDataSetChanged()
        }
    }

    private fun setupRecycler() {
        resultSearch.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )
        resultSearch.adapter = adapterResult
    }

    fun hideKeyboard(){

    }
}
