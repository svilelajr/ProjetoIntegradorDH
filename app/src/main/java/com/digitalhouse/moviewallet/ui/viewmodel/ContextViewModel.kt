package com.digitalhouse.moviewallet.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.digitalhouse.moviewallet.model.DiscoverMovies
import com.digitalhouse.moviewallet.model.Genre
import com.digitalhouse.moviewallet.model.MovieRecycler
import com.digitalhouse.moviewallet.repository.RepositoryMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContextViewModel : ViewModel() {

    private val repository = RepositoryMovie()
    val movieByCategory by lazy { MutableLiveData<MutableList<MovieRecycler>>() }
    val nextPageLoading by lazy { MutableLiveData<Boolean>() }
    private var nextPage = 0
    private lateinit var genreIdPagination: String


    fun getMovieListByGenre(genreId: String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            repository.getMoviesByGenre(genreId).let { discoverMovies ->
                updateNextPage(discoverMovies)
                movieByCategory.postValue(discoverMovies.movies as MutableList<MovieRecycler>?)
                getGenreId(genreId)
            }
        } catch (error: Throwable) {
            Log.e("Error", "Problema de Release $error")
        }
    }

    private fun updateNextPage(moviesPage: DiscoverMovies){
        nextPage = moviesPage.page?.plus(1) ?:1
    }

    fun requestNextPage() = CoroutineScope(Dispatchers.IO).launch {
        try {
            nextPageLoading.postValue(true)
            repository.getMoviesByGenre(genreIdPagination, nextPage).let { discoverMovies ->
                updateNextPage(discoverMovies)
                movieByCategory.postValue(discoverMovies.movies as MutableList<MovieRecycler>)
            }
        } catch (error: Throwable) {
            Log.e("Error", "Problema de Release $error")
        } finally {
            nextPageLoading.postValue(false)
        }
    }

    private fun getGenreId(id: String){
        genreIdPagination = id
    }

}