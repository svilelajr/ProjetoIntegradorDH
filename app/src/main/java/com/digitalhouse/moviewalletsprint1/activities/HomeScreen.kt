package com.digitalhouse.moviewalletsprint1.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.adapters.HomeScreenCategoryAdapter
import com.digitalhouse.moviewallet.data.ListaCategorias
import com.digitalhouse.moviewallet.data.ListaFilmes
import com.digitalhouse.moviewalletsprint1.R
import com.digitalhouse.moviewalletsprint1.adpaters.HomeScreenReleaseAdapter
import com.google.android.material.bottomnavigation.BottomNavigationMenu
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeScreen : AppCompatActivity() {
    private val recyclerViewCategoria by lazy { findViewById<RecyclerView>(R.id.rv_category_home) }
    private val toolbar by lazy { findViewById<Toolbar>(R.id.tb_home) }
    private val bottomNavigate by lazy { findViewById<BottomNavigationView>(R.id.bn_home) }
    private val recyclerRelease by lazy { findViewById<RecyclerView>(R.id.rv_release_home) }
    private val btExplorar by lazy { findViewById<Button>(R.id.bt_explore) }
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.page_home -> {

                return@OnNavigationItemSelectedListener true
            }
            R.id.page_favoritos -> {
                val intent=Intent(this,LoginScreen::class.java)
                startActivity(intent)

                return@OnNavigationItemSelectedListener true
            }
            R.id.page_perfil -> {
                val intent=Intent(this,LoginScreen::class.java)
                startActivity(intent)

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen)
        setupRecyclerView()
        initClick()

        bottomNavigate.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun setupRecyclerView() {
        recyclerViewCategoria.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewCategoria.adapter = HomeScreenCategoryAdapter(ListaCategorias.getCategorias())
        recyclerRelease.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
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