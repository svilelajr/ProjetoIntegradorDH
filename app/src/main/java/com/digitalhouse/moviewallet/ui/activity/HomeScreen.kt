package com.digitalhouse.moviewallet.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.ui.adapter.HomeScreenCategoryAdapter
import com.digitalhouse.moviewallet.R
import com.digitalhouse.moviewallet.model.Genre
import com.digitalhouse.moviewallet.model.Movie
import com.digitalhouse.moviewallet.repository.SingletonConfiguration
import com.digitalhouse.moviewallet.ui.adapter.HomeScreenPopularityAdapter
import com.digitalhouse.moviewallet.ui.adapter.HomeScreenReleaseAdapter
import com.digitalhouse.moviewallet.ui.viewmodel.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator

import com.google.firebase.auth.FirebaseAuth

class HomeScreen : AppCompatActivity() {
    private val recyclerViewCategoria by lazy { findViewById<RecyclerView>(R.id.rv_category_home) }
    private val toolbar by lazy { findViewById<Toolbar>(R.id.tb_home) }
    private val bottomNavigate by lazy { findViewById<BottomNavigationView>(R.id.bn_home) }
    private val recyclerRelease by lazy { findViewById<RecyclerView>(R.id.rv_release_home) }
    private val indicatorRelease by lazy { findViewById<ScrollingPagerIndicator>(R.id.indicator) }
    private val recyclerPopular by lazy { findViewById<RecyclerView>(R.id.rv_popularity_home) }
    private val progressBar by lazy { findViewById<ProgressBar>(R.id.progressBar) }
    private val btExplorar by lazy { findViewById<Button>(R.id.bt_explore) }
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val tvPopularity by lazy { findViewById<TextView>(R.id.tv_popularity_home) }

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
                    } else if (!SingletonConfiguration.getFavoriteDataValidation()) {
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle("Aviso")
                        builder.setMessage("Não foi adicionado Filmes aos Favoritos ainda\n")
                        builder.setNegativeButton("Ok", null)
                        builder.show()

                    } else{

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
    private var listGenres = mutableListOf<Genre>()
    private var listReleaseMovies = mutableListOf<Movie>()
    private val listPopular = mutableListOf<Movie>()
    private val adapterRelease = HomeScreenReleaseAdapter(listReleaseMovies)
    private val adapterGenre = HomeScreenCategoryAdapter(listGenres)
    private val adapterPopular = HomeScreenPopularityAdapter(listPopular)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        observers()
        setupRecyclerView()
        initClick()
        bottomNavigate.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
    
    private fun observers() {
        viewModel.listReleaseMovie.observe(this, {
            it?.let {
                listReleaseMovies.addAll(it)
                adapterRelease.notifyDataSetChanged()
            }
        })
        viewModel.listGenre.observe(this, {
            it?.let {
                listGenres.addAll(it)
                adapterGenre.notifyDataSetChanged()
            }
        })
        viewModel.listPopularMovie.observe(this, {
            it?.let {
                listPopular.addAll(it)
                adapterPopular.notifyDataSetChanged()
            }
        })
        viewModel.progress.observe(this, {
            if (it) {
                progressBar.visibility = View.VISIBLE
                tvPopularity.visibility = View.GONE
            } else {
                tvPopularity.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        })
    }

    private fun setupRecyclerView() {
        val snapHelper = LinearSnapHelper()

        recyclerViewCategoria.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewCategoria.adapter = adapterGenre
        recyclerRelease.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerRelease.adapter = adapterRelease
        snapHelper.attachToRecyclerView(recyclerRelease)

        indicatorRelease.attachToRecyclerView(recyclerRelease)

        recyclerPopular.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerPopular.adapter = adapterPopular

        recyclerViewCategoria.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewCategoria.adapter = adapterGenre

        recyclerViewCategoria.isNestedScrollingEnabled
    }

    private fun initClick() {

        btExplorar.setOnClickListener {
            val intent = Intent(this, SearchScreenCategory::class.java)
            startActivity(intent)
        }
    }


}