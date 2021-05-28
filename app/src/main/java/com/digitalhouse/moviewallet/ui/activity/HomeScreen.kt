package com.digitalhouse.moviewallet.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.adapters.HomeScreenCategoryAdapter
import com.digitalhouse.moviewallet.R
import com.digitalhouse.moviewallet.model.Genre
import com.digitalhouse.moviewallet.model.Movie
import com.digitalhouse.moviewallet.ui.adapter.HomeScreenReleaseAdapter
import com.digitalhouse.moviewallet.ui.viewmodel.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class HomeScreen : AppCompatActivity() {
    private val recyclerViewCategoria by lazy { findViewById<RecyclerView>(R.id.rv_category_home) }
    private val toolbar by lazy { findViewById<Toolbar>(R.id.tb_home) }
    private val bottomNavigate by lazy { findViewById<BottomNavigationView>(R.id.bn_home) }
    private val recyclerRelease by lazy { findViewById<RecyclerView>(R.id.rv_release_home) }
    private val btExplorar by lazy { findViewById<Button>(R.id.bt_explore) }
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_home -> {
                    return@OnNavigationItemSelectedListener true
                }
                R.id.page_favoritos -> {

                    if (firebaseAuth.currentUser == null){
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle("Usuário Não Logado")
                        builder.setMessage("Favoritos disponível somente para usuários Logados \n\nDeseja Logar?")
                        builder.setPositiveButton("Sim") { _, _ ->
                            startActivity(Intent(this, LoginScreen::class.java))
                            finishAffinity()
                        }

                        builder.setNegativeButton("Não", null)
                        builder.show()
                    } else {
                        startActivity(Intent(this, FavoritesScreen::class.java))
                    }

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
    var listReleaseMovies = mutableListOf<Movie>()
    val adapterRelease = HomeScreenReleaseAdapter(listReleaseMovies)
    val adapterGenre = HomeScreenCategoryAdapter(listGenres)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        viewModel.getConfiguration()

        observers()
        setupRecyclerView()
        initClick()
        bottomNavigate.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
    
    private fun observers() {
        viewModel.listReleaseMovie.observe(this, Observer {
            it?.let {
                listReleaseMovies.addAll(it)
                adapterRelease.notifyDataSetChanged()
            }
        })
        viewModel.listGenre.observe(this, Observer {
            it?.let {
                listGenres.addAll(it)
                adapterGenre.notifyDataSetChanged()
            }
        })
    }

    private fun setupRecyclerView() {

        recyclerViewCategoria.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewCategoria.adapter = adapterGenre
        recyclerRelease.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerRelease.adapter = adapterRelease
        recyclerViewCategoria.isNestedScrollingEnabled
    }

    private fun initClick() {

        btExplorar.setOnClickListener {
            val intent = Intent(this, SearchScreen::class.java)
            startActivity(intent)
        }
    }


}