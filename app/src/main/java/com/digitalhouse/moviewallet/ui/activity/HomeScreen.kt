package com.digitalhouse.moviewallet.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.adapters.HomeScreenCategoryAdapter
import com.digitalhouse.moviewallet.data.ListaFilmes
import com.digitalhouse.moviewallet.R
import com.digitalhouse.moviewallet.model.Genre
import com.digitalhouse.moviewallet.ui.adapter.HomeScreenReleaseAdapter
import com.digitalhouse.moviewallet.ui.viewmodel.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeScreen : AppCompatActivity() {
    private val recyclerViewCategoria by lazy { findViewById<RecyclerView>(R.id.rv_category_home) }
    private val toolbar by lazy { findViewById<Toolbar>(R.id.tb_home) }
    private val bottomNavigate by lazy { findViewById<BottomNavigationView>(R.id.bn_home) }
    private val recyclerRelease by lazy { findViewById<RecyclerView>(R.id.rv_release_home) }
    private val btExplorar by lazy { findViewById<Button>(R.id.bt_explore) }
    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_home -> {

                    return@OnNavigationItemSelectedListener true
                }
                R.id.page_favoritos -> {
                    val intent = Intent(this, LoginScreen::class.java)
                    startActivity(intent)

                    return@OnNavigationItemSelectedListener true
                }
                R.id.page_perfil -> {
                    val intent = Intent(this, LoginScreen::class.java)
                    startActivity(intent)

                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    private lateinit var viewModel: HomeViewModel
    var listGenres = mutableListOf<Genre>()
    val adapterGenre = HomeScreenCategoryAdapter(listGenres)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        viewModel.listGenre.observe(this, Observer {
            it?.let { listGenres.addAll(it)
            adapterGenre.notifyDataSetChanged()}
        })
        setupRecyclerView()
        initClick()

        bottomNavigate.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun setupRecyclerView() {

        recyclerViewCategoria.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewCategoria.adapter = adapterGenre
        recyclerRelease.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerRelease.adapter = HomeScreenReleaseAdapter(ListaFilmes.getListaDefilmeAcao())
        recyclerViewCategoria.isNestedScrollingEnabled
    }

    private fun initClick() {
        btExplorar.setOnClickListener {
            val intent = Intent(this, SearchScreen::class.java)
            startActivity(intent)
        }
    }

}